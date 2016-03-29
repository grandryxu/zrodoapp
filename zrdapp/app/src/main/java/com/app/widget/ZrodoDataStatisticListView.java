package com.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by grandry.xu on 15-11-9.
 */
public class ZrodoDataStatisticListView extends ListView {
    public ZrodoDataStatisticListView(Context context) {
        super(context);
    }

    public ZrodoDataStatisticListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZrodoDataStatisticListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
