package com.s00.TreeViews;

import libs.AbstractTreeViewAdapter;
import libs.TreeNodeInfo;
import libs.TreeStateManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s00.R;
import com.s00.main;
import com.s00.Models.NavPoint;

/**
 * This is a very simple adapter that provides very basic tree view with a
 * checkboxes and simple item description.
 * 
 */
public class SimpleStandardAdapter extends AbstractTreeViewAdapter<NavPoint> {



    private String tag = "SimpleStandardAdapter";

	public SimpleStandardAdapter(final main epubApp,
            final TreeStateManager<NavPoint> treeStateManager, final int numberOfLevels) {
        super(epubApp, treeStateManager, numberOfLevels);
    }

    private String getDescription(final NavPoint np) {
//        final Integer[] hierarchy = getManager().getHierarchyDescription(id);
    	String labelString = np.getLabel();
        return labelString;
    }

    @Override
    public View getNewChildView(final TreeNodeInfo<NavPoint> treeNodeInfo) {
//        final LinearLayout viewLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(
//                R.layout.demo_list_item, null);
    	final LinearLayout viewLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(
                R.layout.my_list_item, null);
        return updateView(viewLayout, treeNodeInfo);
    }

    @Override
    public LinearLayout updateView(final View view, final TreeNodeInfo<NavPoint> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) view;
        final TextView descriptionView = (TextView) viewLayout.findViewById(R.id.demo_list_item_description);
//        final TextView levelView = (TextView) viewLayout.findViewById(R.id.demo_list_item_level);
        descriptionView.setText(getDescription(treeNodeInfo.getId()));
        descriptionView.setTextColor(Color.BLACK);
//        levelView.setText(Integer.toString(treeNodeInfo.getLevel()));
//        final CheckBox box = (CheckBox) viewLayout.findViewById(R.id.demo_list_checkbox);
//        box.setTag(treeNodeInfo.getId());
//        if (treeNodeInfo.isWithChildren()) {
//            box.setVisibility(View.GONE);
//        } else {
//            box.setVisibility(View.VISIBLE);
//            box.setChecked(selected.contains(treeNodeInfo.getId()));
//        }
//        box.setOnCheckedChangeListener(onCheckedChange);
        return viewLayout;
    }

    @Override
    public void handleItemClick(final View view, final Object id) {
        final NavPoint np = (NavPoint) id;
        final TreeNodeInfo<NavPoint> info = getManager().getNodeInfo(np);
        if (info.isWithChildren()) {
//            super.handleItemClick(view, id);
        } else {
//            final ViewGroup vg = (ViewGroup) view;
//            final CheckBox cb = (CheckBox) vg.findViewById(R.id.demo_list_checkbox);
//            cb.performClick();
        }
        Log.d(tag , "click:" + np.getId());
//        Toast.makeText(EpubApp.getCONTEXT(),"click:" + np.getId(), Toast.LENGTH_LONG).show();
        ((main)main.getCONTEXT()).handleChapterClick(np);
    }
    
    @Override
    public Drawable getBackgroundDrawable(final TreeNodeInfo<NavPoint> treeNodeInfo) {
            return new ColorDrawable(Color.WHITE);
    }

    @Override
    public long getItemId(final int position) {
        return getTreeId(position).getPlayOrder();
    }
}