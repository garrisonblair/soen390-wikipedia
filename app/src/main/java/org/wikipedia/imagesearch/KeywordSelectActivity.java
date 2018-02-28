package org.wikipedia.imagesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;

import java.util.ArrayList;

/**
 * This Activity takes in a VisionAPI Result object and returns a keyword selected by the user as a string.
 */

public class KeywordSelectActivity extends AppCompatActivity {

    public static final String KEYWORD_LIST = "keyword_list";
    public static final String RESULT_KEY = "result";
    public static final int[] PERCENTAGE_COLORS = {
            0xffff0000,
            0xffff3300,
            0xffff6600,
            0xffff9900,
            0xffffCC00,
            0xffffff00,
            0xffccff00,
            0xff77ff00,
            0xff33ff00,
            0xff00ff00
    };

    public static final int PERCENT = 100;

    private ArrayList<ImageRecognitionLabel> keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_select);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        keywords = (ArrayList<ImageRecognitionLabel>) getIntent().getSerializableExtra(KEYWORD_LIST);

        ListView keywordList = findViewById(R.id.keyword_list_view);
        keywordList.setAdapter(new KeywordAdapter(this, keywords));
    }

    private class KeywordAdapter extends ArrayAdapter<ImageRecognitionLabel> {

        KeywordAdapter(Context context, ArrayList<ImageRecognitionLabel> keywords) {
            super(context, -1, keywords);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_image_keyword, parent, false);

            TextView keywordView = rowView.findViewById(R.id.keyword_view);
            TextView scoreView = rowView.findViewById(R.id.score_view);
            ImageRecognitionLabel label = keywords.get(position);
            keywordView.setText(label.getDescription());

            int percentage = (int) (label.getScore() * PERCENT);
            int colorIndex = percentage / 10;
            scoreView.setText(percentage + "");
            int color = PERCENTAGE_COLORS[colorIndex];
            scoreView.setTextColor(PERCENTAGE_COLORS[colorIndex]);


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView keywordView = view.findViewById(R.id.keyword_view);
                    String keyword = keywordView.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra(RESULT_KEY, keyword);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            return rowView;
        }
    }
}
