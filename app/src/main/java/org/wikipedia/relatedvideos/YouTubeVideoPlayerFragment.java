package org.wikipedia.relatedvideos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.wikipedia.R;
import org.wikipedia.util.ShareUtil;





/**
 * Created by steve on 28/03/18.
 */

public class YouTubeVideoPlayerFragment extends Fragment {

    private String title;
    private String description;
    private int pageId;
    private View view;
    private View rootView;
    private FragmentActivity myContext;
    private YouTubePlayer player;
    private YouTubePlayer yplayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            description = bundleRead.getString("pageDescription");
            pageId = bundleRead.getInt("pageId");
        }

        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();

        youTubePlayerFragment.initialize(YouTubeAPIKey.API_KEY, new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                if (!wasRestored) {
                    yplayer = player;
                    yplayer.setFullscreen(true);
                    yplayer.loadVideo("2zNSgSzhBfM");
                    yplayer.play();
                }

            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }


        });
        android.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    }

    public void initialize(String apiKey, YouTubePlayer.OnInitializedListener onInitializedListener) {

    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.youtube_player_fragment, container, false);
        rootView = view.getRootView();

        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();

        android.app.FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        // Setting Title in the TextView
        TextView titleView = view.findViewById(R.id.youtube_video_title);
        titleView.setText(title);

        //Listener for the video share button
        ImageButton shareButton = view.findViewById(R.id.youtube_player_share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder videoShare = new StringBuilder();
                videoShare.append(title);
                videoShare.append("\nRelated to article: " + pageId);
                videoShare.append("\nYouTube URL: " + "https://www.youtube.com/watch?v=" + description);
                ShareUtil.shareText(v.getContext(), title, videoShare.toString());
            }
        });

        //Listener for the video back button
        ImageButton backButton = view.findViewById(R.id.youtube_player_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEV_DEBUG", "Back button pressed");
                getActivity().onBackPressed();
            }
        });

        return rootView;

    }
}
