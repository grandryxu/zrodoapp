package com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.common.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by grandry.xu on 15-11-9.
 */
public class DataListAdapter extends BaseAdapter {
    private Context mContex;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mDatas = new ArrayList<Map<String, String>>();
    private int layoutResourceId;
    private String[] names;
    private int[] ids;

    public DataListAdapter() {
    }

    public DataListAdapter(Context context) {
        this.mContex = context;
    }

    public DataListAdapter(Context context, int layoutResourceId, String[] names, int[] ids, List<Map<String, String>> datas) {
        this.mContex = context;
        mInflater = LayoutInflater.from(mContex);
        this.layoutResourceId = layoutResourceId;
        this.names = names;
        this.ids = ids;
        this.mDatas = datas;

    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(this.layoutResourceId, null);
        }
        bindValuetoView(convertView, position);
        return convertView;
    }

    private void bindValuetoView(View view, int position) {
        Map<String, String> map = mDatas.get(position);
        if (ids == null || names == null) {
            throw new IllegalArgumentException("please check ids,names");
        }
        int count = ids.length;
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.views = new View[count];
            for (int i = 0; i < count; i++) {
                holder.views[i] = view.findViewById(ids[i]);
            }
            view.setTag(holder);
        }
        for (int i = 0; i < count; i++) {
            View v = holder.views[i];
            String data = map.get(names[i]);
            if (v instanceof TextView) {
                ((TextView) v).setText(data != null ? data : "");
            }
        }

    }

    class ViewHolder {
        View[] views;
    }
}
