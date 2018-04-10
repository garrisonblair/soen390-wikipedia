package org.wikipedia.journey;

import android.os.Bundle;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.wikipedia.R;
import org.wikipedia.activity.BaseActivity;

import java.util.List;

public class JourneyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_layout);
        TreeNode root = setUpTree();
        if (root.isLeaf()) {
            setContentView(R.layout.activity_layout);
        } else {
            AndroidTreeView treeView = new AndroidTreeView(this, root);
            setContentView(treeView.getView());
        }
    }

    private TreeNode setUpTree() {
        TreeNode root = TreeNode.root();
        Visit journeyRecorderRoot = JourneyRecorder.getInstance(this).getRoot();

        if (journeyRecorderRoot == null) {
            return root;
        }
        TreeNode firstNode = new TreeNode(journeyRecorderRoot).setViewHolder(new JourneyArticleHolder(this));
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
            TreeNode newNode = new TreeNode(visitSubNode).setViewHolder(new JourneyArticleHolder(this));
            parent.addChild(newNode);
            traverseAllNodes(visitSubNode, newNode);
        }
    }
}
