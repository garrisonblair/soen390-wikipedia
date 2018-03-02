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
import java.util.Collections;
import java.util.Comparator;

/**
 * This Activity takes in a VisionAPI Result object and returns a keyword selected by the user as a string.
 */

public class KeywordSelectActivity extends AppCompatActivity {

    public static final String KEYWORD_LIST = "keyword_list";
    public static final String RESULT_KEY = "result";

    private ArrayList<ImageRecognitionLabel> keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_select);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        keywords = (ArrayList<ImageRecognitionLabel>) getIntent().getSerializableExtra(KEYWORD_LIST);
        removeDuplicates(keywords);
        sortKeywords(keywords);


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
            ImageRecognitionLabel label = keywords.get(position);
            keywordView.setText(label.getDescription());

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

    private void sortKeywords(ArrayList<ImageRecognitionLabel> keywords) {
        Collections.sort(keywords, new Comparator<ImageRecognitionLabel>() {
            @Override
            public int compare(ImageRecognitionLabel imageRecognitionLabel, ImageRecognitionLabel t1) {
                if (imageRecognitionLabel.getScore() > t1.getScore()) {
                    return 1;
                } else if (imageRecognitionLabel.getScore() < t1.getScore()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private void removeDuplicates(ArrayList<ImageRecognitionLabel> keywords) {
        ArrayList<ImageRecognitionLabel> removes = new ArrayList<>();
        for (ImageRecognitionLabel label : keywords) {
            for (ImageRecognitionLabel label2 : keywords) {
                if (label != label2 && label.getDescription().toLowerCase().equals(label2.getDescription().toLowerCase())) {
                    removes.add(label);
                }
            }
        }

        for (ImageRecognitionLabel label : removes) {
            keywords.remove(label);
        }
    }
}
