package com.zrodo.zrdapp.uihandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zrodo.zrdapp.PersonalSettingActivity;
import com.zrodo.zrdapp.R;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by grandry.xu on 15-10-29.
 * ��װ����ģ��ҳ�棬����ģ�����Ӧactivity��ϵ����
 */
public class ModeLayoutHandler implements View.OnClickListener {
    private Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
    private LayoutInflater mInflater;
    private ViewGroup parentView;
    private Context mContext;

    public ModeLayoutHandler(Context context, int resId, Map<Integer, Class<?>> map) {
        mInflater = LayoutInflater.from(context);
        parentView = (ViewGroup) mInflater.inflate(resId, null);
        this.map = map;
        this.mContext = context;
    }

    public View getView() {
        int count = parentView.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (parentView.getChildAt(i) instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) parentView.getChildAt(i);
                    int childCount = layout.getChildCount();
                    for (int j = 0; j < childCount; j++) {
                        ImageView imageView = (ImageView) layout.getChildAt(j);
                        imageView.setOnClickListener(this);
                    }
                } else {
                    ImageView imageView = (ImageView) parentView.getChildAt(i);
                    imageView.setOnClickListener(this);
                }
            }
        }
        return parentView;
    }


    @Override
    public void onClick(View v) {
        if (map == null) {
            return;
        }
        Class clz = this.map.get(v.getId());
        if(clz== PersonalSettingActivity.class){
            Toast.makeText(v.getContext(),"������δ����",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(mContext, clz);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}
