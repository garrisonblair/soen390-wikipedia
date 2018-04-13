package org.wikipedia.journey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.wikipedia.R;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.page.PageActivity;
import org.wikipedia.util.ShareUtil;

import java.util.List;

public class JourneyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.nothing_to_display_layout);
        TreeNode root = setUpTree();
        if (root.isLeaf()) {
            setContentView(R.layout.nothing_to_display_layout);
        } else {
            setContentView(R.layout.activity_journey);

            TextView tv = (TextView)findViewById(R.id.journey_title);
            tv.setText("Journey");

            LinearLayout linearLayout = findViewById(R.id.activity_journey);

            // Add tree view as child
            AndroidTreeView treeView = new AndroidTreeView(this, root);
            linearLayout.addView(treeView.getView());

            ImageButton shareJourneyButton = findViewById(R.id.journey_share_button);

            // Add on click listener to share journey
            shareJourneyButton.setOnClickListener(v -> {
                JourneyRecorder journeyRecorder = JourneyRecorder.getInstance(this);
                StringBuilder text = new StringBuilder();
                String title = "Journey";
                text.append(journeyRecorder.getJourneyString());
                ShareUtil.shareText(this, title, text.toString());
            });
        }
    }

    private TreeNode setUpTree() {
        TreeNode root = TreeNode.root();
        Visit journeyRecorderRoot = JourneyRecorder.getInstance(this).getRoot();

        if (journeyRecorderRoot == null) {
            return root;
        }
        TreeNode firstNode = new TreeNode(journeyRecorderRoot).setViewHolder(new JourneyArticleHolder(this, getViewArticleCallback()));
        root.addChild(firstNode);
        traverseAllNodes(journeyRecorderRoot, firstNode);
        return root;
    }

    private void traverseAllNodes(Visit visitNode, TreeNode parent) {
        List<Visit> subVisitsList = visitNode.getSubVisits();
        if (subVisitsList.isEmpty()) {
            return;
        }

        for (Visit visitSubNode : subVisitsList) {
            TreeNode newNode = new TreeNode(visitSubNode).setViewHolder(new JourneyArticleHolder(this, getViewArticleCallback()));
            parent.addChild(newNode);
            traverseAllNodes(visitSubNode, newNode);
        }
    }

    private void showPageActivity(Visit visit) {
        Intent intent = PageActivity.newIntent(getApplicationContext(), visit.getPageInfo().getDisplayTitle());
        startActivity(intent);
        //finish();
    }

    private JourneyArticleHolder.ViewArticleCallback getViewArticleCallback() {
        JourneyArticleHolder.ViewArticleCallback callback = new JourneyArticleHolder.ViewArticleCallback() {
            @Override
            public void afterViewArticleClicked(Visit visit) {
                showPageActivity(visit);
            }
        };
        return callback;
    }
}
