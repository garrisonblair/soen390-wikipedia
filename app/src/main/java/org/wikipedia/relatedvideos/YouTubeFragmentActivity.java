package org.wikipedia.relatedvideos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.util.ResourceUtil;
import org.wikipedia.util.ShareUtil;

/**
 * Created by steve on 28/03/18.
 */

public class YouTubeFragmentActivity extends BaseActivity {

    private WikipediaApp app;

    private String videoId;
    private String videoTitle;
    private String videoDescription;
    private String pageTitle;
    private String pageId;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            Bundle bundleRead = getIntent().getExtras();
            videoId = bundleRead.getString("videoId");
            videoTitle = bundleRead.getString("videoTitle");
            videoDescription = bundleRead.getString("videoDescription");
            pageTitle = bundleRead.getString("pageTitle");
            pageId = bundleRead.getString("pageDescription");
        }

        app = (WikipediaApp) getApplicationContext();
        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.page_status_bar_color));
        setContentView(R.layout.youtube_player_fragment);

        videoId = getIntent().getExtras().getString("videoId");
        videoTitle = getIntent().getExtras().getString("videoTitle");
        videoDescription = getIntent().getExtras().getString("videoDescription");

        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        TextView titleView = findViewById(R.id.youtube_video_title);
        titleView.setText(videoTitle);

        TextView descriptionView = findViewById(R.id.youtube_video_description);
        descriptionView.setText(videoDescription);

        //Listener for the video share button
        ImageButton shareButton = findViewById(R.id.youtube_player_share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder videoShare = new StringBuilder();
                videoShare.append(videoTitle);
                videoShare.append("\nRelated to article: " + pageTitle);
                videoShare.append("\nYouTube URL: " + "https://www.youtube.com/watch?v=" + videoId);
                ShareUtil.shareText(v.getContext(), videoTitle, videoShare.toString());
            }
        });

        //Listener for the video back button
        ImageButton backButton = findViewById(R.id.youtube_player_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEV_DEBUG", "Back button pressed");
                onBackPressed();
            }
        });

        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);

        youTubePlayerFragment.initialize(YouTubeAPIKey.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    Log.d("DEV_DEBUG", "Player is working");
                    youTubePlayer.cueVideo(videoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DEV_DEBUG", "Player not working");
            }
        });
    }

    @Override
    public void onBackPressed() {
        app.getSessionFunnel().backPressed();
        super.onBackPressed();
        finish();
    }


}
