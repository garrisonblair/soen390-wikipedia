package org.wikipedia.journey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.unnamed.b.atv.model.TreeNode;

import org.wikipedia.R;
import org.wikipedia.views.ViewUtil;

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
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.node_icon);
        ViewUtil.loadImageUrlInto(imageView, visit.getPageInfo().getLeadImageUrl());

        return view;
    }
}
