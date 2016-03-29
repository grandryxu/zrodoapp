package com.zrodo.zrdapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zrodo.zrdapp.uihandler.ModeLayoutHandler;
import com.app.common.ZrodoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grandry.xu on 15-10-28.
 */
public class ModeActivity extends ZrodoActivity {

    private ViewPager viewPager;
    private List<View> views=new ArrayList<View>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode);
        getModeView();
    }



    @Override
    protected void initView() {
       viewPager=findView(R.id.viewpager);
    }

    private void getModeView(){
        Map<Integer,Class<?>> map=new HashMap<Integer, Class<?>>();
        map.put(new Integer(R.id.data_statistic),DataStatisticActivity.class);
        map.put(new Integer(R.id.data_warning),DataWarningActivity.class);
        map.put(new Integer(R.id.data_query),InfoQueryActivity.class);
       // map.put(new Integer(R.id.personal_setting),PersonalSettingActivity.class);
       // map.put(new Integer(R.id.location_inf),BaiduGPSLocation.class);
        map.put(new Integer(R.id.location_inf),BDLocationActivity.class);
        ModeLayoutHandler modeLayoutHandler=new ModeLayoutHandler(this,R.layout.mode_item,map);
        views.add(modeLayoutHandler.getView());
        viewPager.setAdapter(pagerAdapter);
    }

    PagerAdapter pagerAdapter=new PagerAdapter() {
        @Override
        public int getCount() {
            return views.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));
        }
    };

    @Override
    public boolean handlerException(Exception e) {
      return false;
    }
}