package com.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zrodo.zrdapp.R;


/**
 * Created by grandry.xu on 15-10-28.
 * 页面的header bar，可以设置返回，确认等操作按钮
 */
public class ZrodoHeaderView extends RelativeLayout {
    private Button leftButton;//左侧按钮
    private Button rightButton;//右侧按钮
    private TextView middleText;//中部文本控件

    private Context context;

    public ZrodoHeaderView(Context context) {
        this(context,null);
    }

    public ZrodoHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZrodoHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        if(attrs!=null){
            initFromAttributes(context,attrs);
        }

    }

    private void initFromAttributes(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZrodoHeaderView);
        int n=a.getIndexCount();
        CharSequence leftBtnStr=null,middleTextStr=null,rightBtnStr=null;
        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.ZrodoHeaderView_left_btn_text:
                    leftBtnStr=a.getText(attr);
                    break;
                case R.styleable.ZrodoHeaderView_middle_text:
                    middleTextStr=a.getText(attr);
                    break;
                case R.styleable.ZrodoHeaderView_right_btn_text:
                    rightBtnStr=a.getText(attr);
            }
        }
        a.recycle();
        if(leftBtnStr!=null&&leftBtnStr.length()>0){
            setupLeftBtn(leftBtnStr);
        }
        if(middleTextStr!=null&&middleTextStr.length()>0){
            setupMiddleText(middleTextStr);
        }
        if(rightBtnStr!=null&&rightBtnStr.length()>0){
            setupRightButton(rightBtnStr);
        }
    }


    private void setupLeftBtn(CharSequence leftStr){
        if(leftButton!=null){
            return;
        }
        LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        leftButton=new Button(this.context);
        leftButton.setId(R.id.head_left_btn);
        leftButton.setText(leftStr);
        leftButton.setTextColor(getResources().getColor(R.color.white));
        leftButton.setTextSize(14);
        leftButton.setBackgroundResource(R.drawable.head_btn_back);
        leftButton.setLayoutParams(lp);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)ZrodoHeaderView.this.getContext()).onBackPressed();
            }
        });
        addView(leftButton);
    }

    private void setupMiddleText(CharSequence middle){
        if(middleText!=null){
            return;
        }
        LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        middleText=new TextView(this.context);
        middleText.setId(R.id.head_middle_text);
        middleText.setText(middle);
        middleText.setTextColor(getResources().getColor(R.color.white));
        middleText.setTextSize(20);
        middleText.setGravity(Gravity.CENTER);
        middleText.setLayoutParams(lp);
        addView(middleText);
    }

    private void setupRightButton(CharSequence rightstr){
        if(rightButton!=null){
            return;
        }
        LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        rightButton=new Button(this.context);
        rightButton.setId(R.id.head_right_btn);
        rightButton.setText(rightstr);
        rightButton.setTextColor(getResources().getColor(R.color.white));
        rightButton.setTextSize(14);
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setBackgroundResource(R.drawable.head_btn_right);
        rightButton.setLayoutParams(lp);
        addView(rightButton);
    }

    public Button getRightButton() {
        return rightButton;
    }
}
