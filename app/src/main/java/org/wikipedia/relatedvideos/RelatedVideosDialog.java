package org.wikipedia.relatedvideos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;

/**
 * Created by Fred on 2018-03-23.
 *
 * Displays videos related to the current article
 */

public class RelatedVideosDialog extends ExtendedBottomSheetDialogFragment {

    public static final String TITLE_ARGUMENT = "title_argument_related_videos_dialog";

    private View rootView;
    private RecyclerView videoRecyclerView;

    private String title;

    private String[] sample = {"YouTubeVideoAdapter 1", "YouTubeVideoAdapter 2", "YouTubeVideoAdapter 3", "YouTubeVideoAdapter 4"};



    //Instance getter because Fragments cant have non-default constructors
    public static RelatedVideosDialog newInstance(String title) {
        RelatedVideosDialog dialog = new RelatedVideosDialog();
        Bundle args = new Bundle();

        args.putString(TITLE_ARGUMENT, title);

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dialog_related_videos, container);

        title = this.getArguments().getString(TITLE_ARGUMENT);

        videoRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_list);
        videoRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        videoRecyclerView.setLayoutManager(layoutManager);

        videoRecyclerView.setAdapter(new VideoRecyclerAdapter(sample));

        return rootView;
    }


    class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            private View view;

            ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
            }

            public View getRootView() {
                return view;
            }
        }

        private String[] videos;

        VideoRecyclerAdapter(String[] videos) {
            this.videos = videos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_related_video, parent, false);

            ViewHolder vh = new ViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String video = videos[position];

            TextView videoNameView = (TextView) holder.getRootView().findViewById(R.id.video_name_view);
            videoNameView.setText(video);
        }

        @Override
        public int getItemCount() {
            return videos.length;
        }



    }
}
