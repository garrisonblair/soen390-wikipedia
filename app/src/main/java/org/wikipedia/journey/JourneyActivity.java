package org.wikipedia.journey;

import android.os.Bundle;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.wikipedia.R;
import org.wikipedia.activity.BaseActivity;

public class JourneyActivity extends BaseActivity {

    private TreeNode root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_layout);
        TreeNode root = setUpTree();
        if(root.size() == 0) {
            setContentView(R.layout.activity_layout);
        }
        else {
            AndroidTreeView treeView = new AndroidTreeView(this, root);
            setContentView(treeView.getView());
        }

    }

    private TreeNode setUpTree() {
        root = TreeNode.root();


        return root;


    }
}
