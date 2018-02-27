package org.wikipedia.feed.searchbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.wikipedia.R;
import org.wikipedia.feed.view.DefaultFeedCardView;
import org.wikipedia.imagesearch.KeywordSelectActivity;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.ResourceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.wikipedia.Constants.ACTIVITY_REQUEST_IMAGE_KEYWORD;


public class SearchCardView extends DefaultFeedCardView<SearchCard> {

    public interface Callback {
        void onSearchRequested();
        void onVoiceSearchRequested();
    }

    public SearchCardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_search_bar, this);
        setCardBackgroundColor(ResourceUtil.getThemedColor(context, R.attr.searchItemBackground));
        ButterKnife.bind(this);
        FeedbackUtil.setToolbarButtonLongPressToast(findViewById(R.id.voice_search_button));
    }

    @OnClick(R.id.search_container) void onSearchClick() {
        if (getCallback() != null) {
            getCallback().onSearchRequested();
        }
    }

    @OnClick(R.id.voice_search_button) void onVoiceSearchClick() {
        if (getCallback() != null) {
            getCallback().onVoiceSearchRequested();
        }
    }

    @OnClick(R.id.image_search_button) void onImageSearchClick() {
        // TODO: Add logic to open camera app for image to text

        //Start KeywordSelectActivity with test data for development, move to MainActivity when camera/gallery implemented
        Intent keywordSelectIntent = new Intent(getContext(), KeywordSelectActivity.class);
        String[] keywords = {"Cat", "Animal", "Tabby Cat"};
        keywordSelectIntent.putExtra(KeywordSelectActivity.KEYWORD_LIST, keywords);

        ((Activity) getContext()).startActivityForResult(keywordSelectIntent, ACTIVITY_REQUEST_IMAGE_KEYWORD);
    }


}
