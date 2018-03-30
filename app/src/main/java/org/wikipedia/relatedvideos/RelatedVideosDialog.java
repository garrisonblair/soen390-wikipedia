package org.wikipedia.relatedvideos;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;
import org.wikipedia.relatedvideos.components.YouTubeVideoPlayerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 2018-03-23.
 *
 * Displays videos related to the current article
 */

public class RelatedVideosDialog extends ExtendedBottomSheetDialogFragment {

    public static final String TITLE_ARGUMENT = "title_argument_related_videos_dialog";

    private String title;
    private List<VideoInfo> videos;
    private YouTubeVideoService videoService;

    private View rootView;
    private RecyclerView videoRecyclerView;




    //Instance getter because Fragments cant have non-default constructors
    public static RelatedVideosDialog newInstance(String title) {
        RelatedVideosDialog dialog = new RelatedVideosDialog();
        Bundle args = new Bundle();

        dialog.setVideoService(new YouTubeVideoService());

        args.putString(TITLE_ARGUMENT, title);

        dialog.setArguments(args);
        return dialog;
    }

    static RelatedVideosDialog newInstance(String title, YouTubeVideoService videoService) {
        RelatedVideosDialog dialog = new RelatedVideosDialog();
        dialog.setVideoService(videoService);

        Bundle args = new Bundle();

        args.putString(TITLE_ARGUMENT, title);

        dialog.setArguments(args);
        return dialog;
    }

    private void retrieveVideos(String searchTerm) {

        videoService.searchVideos(searchTerm);

        videos = new ArrayList<VideoInfo>();
        videos.add(new VideoInfoTestImpl("DaOJv-fMlmA", "THOR RAGNAROK Grandmaster Moves To Earth EXTENDED - Team Darryl Short Film (2017) Jeff Goldblum HD", ""));
        videos.add(new VideoInfoTestImpl("jI8Im6RoPWo", "10 Playstation Fails Sony Wants You To Forget", ""));
        videos.add(new VideoInfoTestImpl("j-W6ccHY6-Q", "Fallout 4 - 25 Behemoths VS 25 Sentry Bots - Battles #1", ""));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = this.getArguments().getString(TITLE_ARGUMENT);
        retrieveVideos(title);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dialog_related_videos, container);



        videoRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_list);
        videoRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        videoRecyclerView.setLayoutManager(layoutManager);

        videoRecyclerView.setAdapter(new VideoRecyclerAdapter(videos));

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

        private List<VideoInfo> videos;

        VideoRecyclerAdapter(List<VideoInfo> videos) {
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
            VideoInfo video = videos.get(position);

            TextView videoNameView = (TextView) holder.getRootView().findViewById(R.id.video_name_view);
            videoNameView.setText(video.getTitle());

            ImageView thumbnailView = (ImageView) holder.getRootView().findViewById(R.id.video_thumbnail);

            thumbnailView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    LayoutInflater inflater = getLayoutInflater();
                    View customView = inflater.inflate(R.layout.dialog_youtube_player_view, null);
                    PopupWindow mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    if (Build.VERSION.SDK_INT >= 21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);


                    return;
                }
            });

            //set the ontouch listener

            new ThumbnailLoadTask(thumbnailView, video.getID()).execute();

        }

        @Override
        public int getItemCount() {
            return videos.size();
        }



    }

    public void setVideoService(YouTubeVideoService videoService) {
        this.videoService = videoService;
    }
}
