package org.wikipedia.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wikipedia.R;
import org.wikipedia.activity.FragmentUtil;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;
import org.wikipedia.readinglist.database.ReadingList;
import org.wikipedia.readinglist.database.ReadingListPage;
import org.wikipedia.util.ResourceUtil;

public class NotesActionsDialog extends ExtendedBottomSheetDialogFragment {
    public interface Callback {
        void onShareItem(int noteIndex);
        void onDeleteItem(int noteIndex);
    }

    private int noteIndex;
    private NotesActionsView actionsView;
    private ItemActionsCallback itemActionsCallback = new ItemActionsCallback();

    //TODO: set instance from db
    @NonNull
    public static NotesActionsDialog newInstance(@NonNull ReadingListPage page,
                                                 @NonNull ReadingList list) {
        NotesActionsDialog instance = new NotesActionsDialog();
        Bundle args = new Bundle();
        args.putInt("noteIndex", list.pages().indexOf(page));
        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        actionsView = new NotesActionsView(getContext());
        actionsView.setBackgroundColor(ResourceUtil.getThemedColor(getContext(), R.attr.paper_color));
        actionsView.setCallback(itemActionsCallback);
        noteIndex = getArguments().getInt("noteIndex");
        actionsView.setState();
        return actionsView;
    }

    @Override
    public void onDestroyView() {
        actionsView.setCallback(null);
        actionsView = null;
        super.onDestroyView();
    }

    private class ItemActionsCallback implements NotesActionsView.Callback {
        @Override
        public void onShare() {
            dismiss();
            if (callback() != null) {
                callback().onShareItem(noteIndex);
            }
        }

        @Override
        public void onDelete() {
            dismiss();
            if (callback() != null) {
                callback().onDeleteItem(noteIndex);
            }
        }
    }

    @Nullable
    private Callback callback() {
        return FragmentUtil.getCallback(this, Callback.class);
    }
}
