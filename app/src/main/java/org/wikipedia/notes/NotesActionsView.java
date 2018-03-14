package org.wikipedia.notes;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.wikipedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActionsView extends LinearLayout {
    public interface Callback {
        void onShare();
        void onDelete();
    }

    @BindView(R.id.notes_remove_text) TextView removeTextView;

    @Nullable private Callback callback;

    public NotesActionsView(Context context) {
        super(context);
        init();
    }

    public NotesActionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NotesActionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NotesActionsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setState() {
        removeTextView.setText(getResources().getString(R.string.note_remove_from_list));
    }

    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    @OnClick(R.id.notes_share) void onShareClick(View view) {
        if (callback != null) {
            callback.onShare();
        }
    }

    @OnClick(R.id.notes_remove) void onRemoveClick(View view) {
        if (callback != null) {
            callback.onDelete();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.view_notes_page_actions, this);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);
    }
}
