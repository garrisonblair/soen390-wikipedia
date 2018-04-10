package org.wikipedia.journey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import org.wikipedia.R;

/**
 * Created by Andres on 2018-04-10.
 */

public class JourneyArticleHolder extends TreeNode.BaseNodeViewHolder<Visit> {

    public JourneyArticleHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, Visit visit) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_node, null, true);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(visit.getPageInfo().getDisplayTitle());

        return view;
    }
}
