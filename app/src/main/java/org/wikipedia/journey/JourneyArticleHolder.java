package org.wikipedia.journey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.unnamed.b.atv.model.TreeNode;

import org.wikipedia.R;
import org.wikipedia.views.ViewUtil;

/**
 * Created by Andres on 2018-04-10.
 */

public class JourneyArticleHolder extends TreeNode.BaseNodeViewHolder<Visit> {
    private final int LEVEL_LEFT_MARGIN = 50;

    public JourneyArticleHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, Visit visit) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_journey, null, true);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        node.setExpanded(true);
        p.setMargins((node.getLevel() - 1) * LEVEL_LEFT_MARGIN, 0, 0, 0);
        view.setLayoutParams(p);
        TextView tvLevel = (TextView) view.findViewById(R.id.journey_level);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvLevel.setText(node.getLevel() + ".");
        tvValue.setText(visit.getPageInfo().getDisplayTitle());
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.node_icon);
        ViewUtil.loadImageUrlInto(imageView, visit.getPageInfo().getLeadImageUrl());

        return view;
    }
}
