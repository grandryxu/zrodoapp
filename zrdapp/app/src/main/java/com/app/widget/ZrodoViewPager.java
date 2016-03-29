package com.app.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.renderscript.Element;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zrodo.zrdapp.R;
import com.zrodo.zrdapp.uihandler.BtnArray;
import com.app.asyntask.ZrodAsyncTask;
import com.app.common.DataQueryType;
import com.app.common.ZrodoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grandry.xu on 15-11-6.
 */
public class ZrodoViewPager extends ViewPager {
    private ZrodoViewPagerPostExecuteListener listener;
    private ZrodoAfterPagerSelectedListener afterSelectedListener;

    public ZrodoViewPager(Context context) {
        this(context, null);
    }

    public ZrodoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPostExecuterListener(ZrodoViewPagerPostExecuteListener listener) {
        this.listener = listener;
    }

    public void setAfterSelectedListener(ZrodoAfterPagerSelectedListener afterSelectedListener) {
        this.afterSelectedListener = afterSelectedListener;
    }

    public void setViews(final List<View> views, final BtnArray btnArray) {
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }
        });

        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                Button[] buttons = btnArray.getBtnArray();
                for (Button btn : buttons) {
                    btn.setBackgroundResource(R.drawable.tab_btn_backstyle1);
                }
                Button btn = btnArray.getItemAtIndex(i);
                btn.setBackgroundResource(R.drawable.tab_btn_backstyle2);

            }


            @Override
            public void onPageSelected(final int i) {
                final ViewGroup v = (ViewGroup) views.get(i);
                if (v.getTag() != null&&(Boolean) v.getTag())//tag is view is showed
                    return;
                afterSelectedListener.afterPagerSelected(v,i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public interface ZrodoViewPagerPostExecuteListener {
        void doAfterBackExecute(ViewGroup view, int id, DataQueryType type);
    }


    public interface  ZrodoAfterPagerSelectedListener{
        void afterPagerSelected(ViewGroup view, int index);
    }


}
