package org.wikipedia.page.shareafact;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.ActivityUtil;
import org.wikipedia.analytics.ShareAFactFunnel;
import org.wikipedia.bridge.CommunicationBridge;
import org.wikipedia.dataclient.mwapi.MwQueryResponse;
import org.wikipedia.gallery.ImageLicense;
import org.wikipedia.gallery.ImageLicenseFetchClient;
import org.wikipedia.language.AppLanguageLookUpTable;
import org.wikipedia.onboarding.PrefsOnboardingStateMachine;
import org.wikipedia.page.Namespace;
import org.wikipedia.page.NoDimBottomSheetDialog;
import org.wikipedia.page.Page;
import org.wikipedia.page.PageActivity;
import org.wikipedia.page.PageFragment;
import org.wikipedia.page.PageProperties;
import org.wikipedia.page.PageTitle;
import org.wikipedia.settings.Prefs;
import org.wikipedia.texttospeech.TTSWrapper;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.ShareUtil;
import org.wikipedia.util.StringUtil;
import org.wikipedia.util.UriUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.wiktionary.WiktionaryDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;

import static org.wikipedia.analytics.ShareAFactFunnel.ShareMode;

/**
 * Let user choose between sharing as text or as image.
 */
public class ShareHandler {
    private static final String PAYLOAD_PURPOSE_KEY = "purpose";
    private static final String PAYLOAD_PURPOSE_SHARE = "share";
    private static final String PAYLOAD_PURPOSE_DEFINE = "define";
    private static final String PAYLOAD_PURPOSE_EDIT_HERE = "edit_here";
    private static final String PAYLOAD_TEXT_KEY = "text";
    private WikipediaApp app = WikipediaApp.getInstance();

    @NonNull private final PageFragment fragment;
    @NonNull private final CommunicationBridge bridge;
    @Nullable private ActionMode webViewActionMode;
    @Nullable private ShareAFactFunnel funnel;
    @Nullable private TTSWrapper textToSpeech;
    @Nullable private String pageLanguage;
    @Nullable private String selectedLanguage = "";
    @Nullable private Locale selectedLocale;
    @Nullable private final ReentrantLock lock = new ReentrantLock();


    private void createFunnel() {
        final Page page = fragment.getPage();
        final PageProperties pageProperties = page.getPageProperties();
        funnel = new ShareAFactFunnel(app, page.getTitle(), pageProperties.getPageId(),
                pageProperties.getRevisionId());
    }

    public ShareHandler(@NonNull PageFragment fragment, @NonNull CommunicationBridge bridge) {
        this.fragment = fragment;
        this.bridge = bridge;
        textToSpeech = TTSWrapper.getInstance(fragment.getContext(), null);
        pageLanguage = app.getAppLanguageCanonicalName(app.getAppOrSystemLanguageCode());

        bridge.addListener("onGetTextSelection", new CommunicationBridge.JSEventListener() {
            @Override
            public void onMessage(String messageType, JSONObject messagePayload) {
                String purpose = messagePayload.optString(PAYLOAD_PURPOSE_KEY, "");
                String text = messagePayload.optString(PAYLOAD_TEXT_KEY, "");
                switch (purpose) {
                    case PAYLOAD_PURPOSE_SHARE:
                        onSharePayload(text);
                        break;
                    case PAYLOAD_PURPOSE_DEFINE:
                        onDefinePayload(text);
                        break;
                    case PAYLOAD_PURPOSE_EDIT_HERE:
                        onEditHerePayload(messagePayload.optInt("sectionID", 0), text);
                        break;
                    default:
                        L.d("Unknown purpose=" + purpose);
                }
            }
        });
    }

    private void onHighlightText() {
        if (funnel == null) {
            createFunnel();
        }
        funnel.logHighlight();
    }

    public void showWiktionaryDefinition(String text) {
        PageTitle title = fragment.getTitle();
        fragment.showBottomSheet(WiktionaryDialog.newInstance(title, text));
    }

    private void onSharePayload(@NonNull String text) {
        if (funnel == null) {
            createFunnel();
        }
        shareSnippet(text);
        funnel.logShareTap(text);
    }

    private void onDefinePayload(String text) {
        showWiktionaryDefinition(text.toLowerCase(Locale.getDefault()));
    }

    private void onEditHerePayload(int sectionID, String text) {
        if (sectionID >= 0) {
            fragment.getEditHandler().startEditingSection(sectionID, text);
        }
    }

    private void showCopySnackbar() {
        FeedbackUtil.showMessage(fragment.getActivity(), R.string.text_copied);
    }

    private void shareSnippet(@NonNull CharSequence input) {
        final String selectedText = StringUtil.sanitizeText(input.toString());
        final PageTitle title = fragment.getTitle();
        final String leadImageNameText = fragment.getPage().getPageProperties().getLeadImageName() != null
                ? fragment.getPage().getPageProperties().getLeadImageName() : "";

        new ImageLicenseFetchClient().request(title.getWikiSite(),
                new PageTitle(Namespace.FILE.toLegacyString(), leadImageNameText, title.getWikiSite()),
                new ImageLicenseFetchClient.Callback() {
                    @Override public void success(@NonNull Call<MwQueryResponse> call,
                                                  @NonNull ImageLicense result) {
                        final Bitmap snippetBitmap = SnippetImage.getSnippetImage(fragment.getContext(),
                                fragment.getLeadImageBitmap(),
                                title.getDisplayText(),
                                fragment.getPage().isMainPage() ? "" : StringUtils.capitalize(title.getDescription()),
                                selectedText,
                                result);
                        fragment.showBottomSheet(new PreviewDialog(fragment.getContext(),
                                snippetBitmap, title, selectedText, funnel));
                    }

                    @Override public void failure(@NonNull Call<MwQueryResponse> call,
                                                  @NonNull Throwable caught) {
                        // If we failed to get license info for the lead image, just share the text
                        PreviewDialog.shareAsText(fragment.getContext(), title, selectedText, funnel);
                        L.e("Error fetching image license info for " + title.getDisplayText(), caught);
                    }
                });
    }

    /**
     * @param mode ActionMode under which this context is starting.
     */
    public void onTextSelected(ActionMode mode) {
        webViewActionMode = mode;
        Menu menu = mode.getMenu();
        MenuItem shareItem = menu.findItem(R.id.menu_text_select_share);
        handleSelection(menu, shareItem);
    }

    private void handleSelection(Menu menu, MenuItem shareItem) {
        if (PrefsOnboardingStateMachine.getInstance().isShareTutorialEnabled()) {
            postShowShareToolTip(shareItem);
            PrefsOnboardingStateMachine.getInstance().setShareTutorial();
        }

        // Provide our own listeners for the copy, define, and share buttons.
        shareItem.setOnMenuItemClickListener(new RequestTextSelectOnMenuItemClickListener(PAYLOAD_PURPOSE_SHARE));
        MenuItem copyItem = menu.findItem(R.id.menu_text_select_copy);
        copyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                fragment.getWebView().copyToClipboard();
                showCopySnackbar();
                leaveActionMode();
                return true;
            }
        });
        MenuItem toSpeechItem = menu.findItem(R.id.menu_text_to_speech);


        toSpeechItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                pageLanguage = getPageLanguage();
                boolean isSetLanguage = setTTSLanguage(getLocaleForTTS(setLanguageName(pageLanguage)));
                Toast.makeText(fragment.getActivity(), "is set language: " + isSetLanguage, Toast.LENGTH_SHORT);
                if (isSetLanguage){
                    selectedTextToSpeech();
                    showStopButton();
                    leaveActionMode();
                    return true;
                }
                return false;
            }
        });
        MenuItem defineItem = menu.findItem(R.id.menu_text_select_define);
        if (shouldEnableWiktionaryDialog()) {
            defineItem.setVisible(true);
            defineItem.setOnMenuItemClickListener(new RequestTextSelectOnMenuItemClickListener(PAYLOAD_PURPOSE_DEFINE));
        }
        MenuItem editItem = menu.findItem(R.id.menu_text_edit_here);
        editItem.setOnMenuItemClickListener(new RequestTextSelectOnMenuItemClickListener(PAYLOAD_PURPOSE_EDIT_HERE));
        if (!fragment.getPage().isArticle()) {
            editItem.setVisible(false);
        }

        onHighlightText();
    }
    private void showStopButton() {
        FragmentActivity currentActivity = fragment.getActivity();
        if (currentActivity instanceof PageActivity) {
            ImageButton stopButton = ((PageActivity) currentActivity).getStopButton();
            stopButton.setVisibility(View.VISIBLE);
        }
    }
    private void selectedTextToSpeech() {

        fragment.getWebView().evaluateJavascript("(function() { return window.getSelection().toString() }) ()",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        textToSpeech.speak(value);
                    }
                });
    }
    private boolean shouldEnableWiktionaryDialog() {
        return Prefs.useRestBase() && isWiktionaryDialogEnabledForArticleLanguage();
    }

    private boolean isWiktionaryDialogEnabledForArticleLanguage() {
        return Arrays.asList(WiktionaryDialog.getEnabledLanguages())
                .contains(fragment.getTitle().getWikiSite().languageCode());
    }

    private void postShowShareToolTip(final MenuItem shareItem) {
        fragment.getView().post(new Runnable() {
            @Override
            public void run() {
                View shareItemView = ActivityUtil.getMenuItemView(fragment.getActivity(), shareItem);
                if (shareItemView != null) {
                    showShareToolTip(shareItemView);
                }
            }
        });
    }

    private void showShareToolTip(@NonNull View shareItemView) {
        FeedbackUtil.showTapTargetView(fragment.getActivity(), shareItemView,
                R.string.menu_text_select_share, R.string.tool_tip_share, null);
    }

    private void leaveActionMode() {
        if (hasWebViewActionMode()) {
            finishWebViewActionMode();
            nullifyWebViewActionMode();
        }
    }

    private boolean hasWebViewActionMode() {
        return webViewActionMode != null;
    }

    private void nullifyWebViewActionMode() {
        webViewActionMode = null;
    }

    private void finishWebViewActionMode() {
        webViewActionMode.finish();
    }

    private class RequestTextSelectOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        @NonNull private final String purpose;

        RequestTextSelectOnMenuItemClickListener(@NonNull String purpose) {
            this.purpose = purpose;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            requestTextSelection(purpose);
            leaveActionMode();
            return true;
        }
    }

    private void requestTextSelection(String purpose) {
        // send an event to the WebView that will make it return the
        // selected text (or first paragraph) back to us...
        try {
            JSONObject payload = new JSONObject();
            payload.put(PAYLOAD_PURPOSE_KEY, purpose);
            bridge.sendMessage("getTextSelection", payload);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A dialog to be displayed before sharing with two action buttons:
     * "Share as image", "Share as text".
     */
    private static class PreviewDialog extends NoDimBottomSheetDialog {
        private boolean completed = false;

        PreviewDialog(final Context context, final Bitmap resultBitmap, final PageTitle title,
                      final String selectedText, final ShareAFactFunnel funnel) {
            super(context);
            View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_share_preview, null);
            setContentView(rootView);
            ImageView previewImage = rootView.findViewById(R.id.preview_img);
            previewImage.setImageBitmap(resultBitmap);
            rootView.findViewById(R.id.close_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    });
            rootView.findViewById(R.id.share_as_image_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String introText = context.getString(R.string.snippet_share_intro,
                                    title.getDisplayText(),
                                    UriUtil.getUrlWithProvenance(context, title, R.string.prov_share_image));
                            ShareUtil.shareImage(context, resultBitmap, title.getDisplayText(),
                                    title.getDisplayText(), introText);
                            funnel.logShareIntent(selectedText, ShareMode.image);
                            completed = true;
                        }
                    });
            rootView.findViewById(R.id.share_as_text_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareAsText(context, title, selectedText, funnel);
                            completed = true;
                        }
                    });
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    resultBitmap.recycle();
                    if (!completed) {
                        funnel.logAbandoned(title.getDisplayText());
                    }
                }
            });
            startExpanded();
        }

        static void shareAsText(@NonNull Context context, @NonNull PageTitle title,
                                @NonNull String selectedText, @Nullable ShareAFactFunnel funnel) {
            String introText = context.getString(R.string.snippet_share_intro,
                    title.getDisplayText(),
                    UriUtil.getUrlWithProvenance(context, title, R.string.prov_share_text));
            ShareUtil.shareText(context, title.getDisplayText(),
                    constructShareText(selectedText, introText));
            if (funnel != null) {
                funnel.logShareIntent(selectedText, ShareMode.text);
            }
        }

        private static String constructShareText(String selectedText, String introText) {
            return selectedText + "\n\n" + introText;
        }
    }

    //retrieve the language name instead of the language code
    private String getPageLanguage() {
        AppLanguageLookUpTable lookup = new AppLanguageLookUpTable(app);
        String lang = lookup.getCanonicalName(fragment.getTitle().getWikiSite().languageCode());
        //Toast.makeText(app, "New Page language: "+ lang, Toast.LENGTH_SHORT).show();
        return lang;
    }

    private String setLanguageName(String language) {
        String lang = language;
        //both Tranditional Chinese and Simplified Chinese can be classified as Chinese
        //because they can be both read in mandarin, which is supported by the TTS engine
        if (language.contains("Chinese")) {
            lang = "Chinese";
        }

        //if the language does not have any other constraint, return the original
        return lang;
    }

    //find the Locale from the given language
    private Locale getLocaleForTTS(String language) {

        Locale locale = Locale.getDefault();
        boolean foundLanguage = false;
        Locale[] locales = Locale.getAvailableLocales();

        //loop until the matched language found
        for (Locale loc : locales) {
            if (loc.getDisplayLanguage().equals(language)) {
                locale = loc;
                foundLanguage = true;
                break;
            }
        }
        if (!foundLanguage) {
            Toast.makeText(fragment.getActivity(), "Locale not found. Default language will be applied. ", Toast.LENGTH_SHORT).show();
            locale = Locale.getDefault();
        }

        return locale;
    }

    //set the TTS language
    private boolean setTTSLanguage(Locale locale) {
        //Toast.makeText(app, "setting TTS language: "+ locale.getDisplayLanguage(), Toast.LENGTH_SHORT).show();
        int result = textToSpeech.isTTSLanguageAvailable(locale);
        //Toast.makeText(fragment.getActivity(), "result setting TTS: " + locale.getDisplayLanguage(), Toast.LENGTH_SHORT).show();
        Toast.makeText(fragment.getActivity(), "result setting TTS: " + result, Toast.LENGTH_SHORT).show();

        boolean isNoLanguage = false;
        if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            textToSpeech.setLanguage(locale);
            isNoLanguage = true;
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED){
            showAlternateLanguageDialog();
            textToSpeech.setLanguage(selectedLocale);
            isNoLanguage =  true;
        }
        Toast.makeText(fragment.getActivity(), "No Language: " + isNoLanguage, Toast.LENGTH_SHORT).show();

        Toast.makeText(fragment.getActivity(), "TTS language has set to : " + textToSpeech.getTTSLanguage(), Toast.LENGTH_SHORT).show();
        return isNoLanguage;
    }

    private void showAlternateLanguageDialog() {
        FragmentActivity currentActivity = fragment.getActivity();
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(currentActivity);
        builderSingle.setTitle("Select alternate language for TTS: ");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(currentActivity, android.R.layout.select_dialog_singlechoice, getTTSOptionList());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedItem = arrayAdapter.getItem(which);
                selectedLanguage = selectedItem;
                AlertDialog.Builder builderInner = new AlertDialog.Builder(currentActivity);
                builderInner.setMessage(selectedItem);

                ArrayList<Locale> locales = getTTSLocales();
                for (Locale loc : locales) {
                    if (selectedLanguage.contains(loc.getDisplayLanguage())) {
                        selectedLocale = loc;
                        pageLanguage = loc.getDisplayLanguage();
                        break;
                    }
                }
                builderInner.setTitle("Select alternate TTS language : ");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private ArrayList getTTSOptionList() {
        ///show TTS language option list
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> ttsLanguages = new ArrayList<>();
        for (Locale locale : locales) {
            int res = textToSpeech.isTTSLanguageAvailable(locale);
            if (res == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                ttsLanguages.add(locale.getDisplayLanguage() + " : " + locale.getDisplayCountry());
            }
        }
        return ttsLanguages;
    }

    private ArrayList getTTSLocales() {
        ///show TTS languages
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<Locale> localeList = new ArrayList<Locale>();
        for (Locale locale : locales) {
            int res = textToSpeech.isTTSLanguageAvailable(locale);
            if (res == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                localeList.add(locale);
            }
        }
        return localeList;
    }
}
