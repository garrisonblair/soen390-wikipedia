package org.wikipedia.journey;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

    public interface ViewArticleCallback {
        void afterViewArticleClicked(Visit visit);
    }
    private ViewArticleCallback callback;
    private static final int LEVEL_LEFT_MARGIN = 50;

    public JourneyArticleHolder(Context context, ViewArticleCallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    public View createNodeView(TreeNode node, Visit visit) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_journey, null, true);
        node.setExpanded(true);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        p.setMargins((node.getLevel() - 1) * LEVEL_LEFT_MARGIN, 0, 0, 0);
        view.setLayoutParams(p);
        TextView tvLevel = (TextView) view.findViewById(R.id.journey_level);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvLevel.setText(node.getLevel() + ".");
        tvValue.setText(visit.getPageInfo().getDisplayTitle());
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.node_icon);
        ViewUtil.loadImageUrlInto(imageView, visit.getPageInfo().getLeadImageUrl());
        ImageView menuImage = (ImageView) view.findViewById(R.id.journey_menu);
        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenuInflater().inflate(R.menu.menu_item_journey, menu.getMenu());
                menu.show();
                MenuItem renameItem = menu.getMenu().findItem(R.id.menu_journey_view_article);
                renameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        callback.afterViewArticleClicked(visit);
                        JourneyRecorder.getInstance(context).setCurrentVisit(visit);
                        return false;
                    }
                });
            }
        });

        return view;
    }
}
