package org.wikipedia.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.imagesearch.ImageRecognitionLabel;
import org.wikipedia.imagesearch.ImageRecognitionLabelTestImpl;
import org.wikipedia.imagesearch.KeywordSelectActivity;

import java.util.ArrayList;

import static org.wikipedia.Constants.ACTIVITY_REQUEST_IMAGE_KEYWORD;


/**
 * This Activities purpose is to allow testing of the KeywordSelectActivity.
 * It passes sample data to the activity and displays the result in a TextView
 */
public class TestKeywordSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ImageRecognitionLabel> keywords = new ArrayList<ImageRecognitionLabel>();
                ImageRecognitionLabel label1 = new ImageRecognitionLabelTestImpl("Cat", 0.9);
                ImageRecognitionLabel label2 = new ImageRecognitionLabelTestImpl("Dog", 0.2);
                keywords.add(label1);
                keywords.add(label2);
                Intent intent = new Intent(view.getContext(), KeywordSelectActivity.class);
                intent.putExtra(KeywordSelectActivity.KEYWORD_LIST, keywords);

                startActivityForResult(intent, ACTIVITY_REQUEST_IMAGE_KEYWORD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_REQUEST_IMAGE_KEYWORD && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.test_result_view);

            textView.setText(data.getExtras().getString(KeywordSelectActivity.RESULT_KEY));
        }
    }
}
