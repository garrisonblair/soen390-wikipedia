package org.wikipedia.relatedvideos;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.wikipedia.R;

public class YouTubeFragmentActivityTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_fragment_test);
        Button button = findViewById(R.id.testButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Creating and adding of properties to be passed into the youtubevideoplayer activity and fragment
                Intent intent = new Intent(view.getContext(), YouTubeFragmentActivity.class);
                intent.putExtra("pageId", "Test Page Id");
                intent.putExtra("pageTitle", "Test Page Title");
                intent.putExtra("videoId", "Test Video Id");
                intent.putExtra("videoTitle", "Test Video Title");
                intent.putExtra("videoDescription", "Test Video Description");

                startActivity(intent);

            }
        });
    }

}
