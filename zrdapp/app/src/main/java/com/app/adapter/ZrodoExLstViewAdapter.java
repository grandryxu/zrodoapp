package com.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zrodo.zrdapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by grandry.xu on 15-11-18.
 */
public class ZrodoExLstViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private List<List<String>> childrenList;

    public ZrodoExLstViewAdapter(Context context, List<String> groupList, List<List<String>> childrenList, View.OnClickListener listener) {
        if (groupList == null || childrenList == null) {
            throw new IllegalArgumentException("groupList or childrenList cannot be null");
        }
        this.context = context;
        this.groupList = groupList;
        this.childrenList = childrenList;
    }

    public ZrodoExLstViewAdapter(Context context, String[] groupList, String[][] childrenList) {
        if (groupList == null || childrenList == null) {
            throw new IllegalArgumentException("groupList or childrenList cannot be null");
        }
        this.context = context;
        this.groupList = Arrays.asList(groupList);
        this.childrenList = new ArrayList<List<String>>();
        int count = childrenList.length;
        for (int i = 0; i < count; i++) {
            this.childrenList.add(Arrays.asList(childrenList[i]));
        }
    }

    public ZrodoExLstViewAdapter(Context context, String[] groupList, Object[] childrenList) {
        if (groupList == null || childrenList == null) {
            throw new IllegalArgumentException("groupList or childrenList cannot be null");
        }
        this.context = context;
        this.groupList = Arrays.asList(groupList);
        this.childrenList = new ArrayList<List<String>>();
        int count = childrenList.length;
        for (int i = 0; i < count; i++) {
            if (childrenList[i] instanceof String[]) {
                this.childrenList.add(Arrays.asList((String[]) childrenList[i]));
            }
        }

    }

    @Override
    public int getGroupCount() {
        return this.groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childrenList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childrenList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String txtValue = (String) getGroup(groupPosition);
        return createGroupView(txtValue, isExpanded);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String txtValue = (String) getChild(groupPosition, childPosition);
        return createLstItemView(txtValue);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private View createGroupView(String txtValue, boolean isExpand) {
        LinearLayout layout = new LinearLayout(this.context);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        ImageButton img = new ImageButton(this.context);
        img.setLayoutParams(params);
        if (isExpand){
            img.setBackground(this.context.getResources().getDrawable(R.drawable.arrow_down));
        }else{
        img.setBackground(this.context.getResources().getDrawable(R.drawable.arrow_right));
        }
        img.setFocusable(false);
        img.setClickable(true);
        layout.addView(img);
        LinearLayout.LayoutParams txtparams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3.0f);
        TextView groupTxtView = new TextView(this.context);
        groupTxtView.setText(txtValue);
        groupTxtView.setTextSize(16);
        groupTxtView.setTextColor(this.context.getResources().getColor(R.color.zrodoblue));
        groupTxtView.setLayoutParams(txtparams);
        layout.addView(groupTxtView);
        return layout;
    }

    private View createLstItemView(String txtValue) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,100);
        TextView childTxtView = new TextView(this.context);
        childTxtView.setText(txtValue);
        childTxtView.setTextSize(14);
        childTxtView.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        childTxtView.setLayoutParams(layoutParams);
        return childTxtView;
    }

}
