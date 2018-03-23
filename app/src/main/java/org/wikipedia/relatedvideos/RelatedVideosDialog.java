package org.wikipedia.relatedvideos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wikipedia.R;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;

/**
 * Created by Fred on 2018-03-23.
 *
 * Displays videos related to the current article
 */

public class RelatedVideosDialog extends ExtendedBottomSheetDialogFragment {

    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_related_videos, container);

        return rootView;
    }
}
