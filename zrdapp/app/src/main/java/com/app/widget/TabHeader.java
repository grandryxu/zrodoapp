package com.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zrodo.zrdapp.R;

/**
 * Created by grandry.xu on 15-10-30.
 * tab页签,目前最多设计3个
 */
public class TabHeader extends LinearLayout {

    private Button firstTab;
    private Button secondTab;
    private Button thirdTab;
    private Context context;
    private int tabnum;
    private static int  FONTSIZE=20;
    private static int FONTCOLOR= R.color.white;
    public TabHeader(Context context) {
        this(context,null);
    }

    public TabHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        setOrientation(HORIZONTAL);
        initFromAttributes(context,attrs);
    }



    private void initFromAttributes(Context context,AttributeSet attrs){
        tabnum=0;
        CharSequence first_txt=null,second_txt=null,third_txt=null;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabHeader);
        int n=a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.TabHeader_tabNum:
                    tabnum=a.getInt(attr,2);
                    break;
                case R.styleable.TabHeader_first_btn_text:
                    first_txt=a.getText(attr);
                    break;
                case R.styleable.TabHeader_second_btn_text:
                    second_txt=a.getText(attr);
                    break;
                case R.styleable.TabHeader_third_btn_text:
                    third_txt=a.getText(attr);
                    break;
                default:
                    break;
            }
        }
        firstTab=new Button(context);
        secondTab=new Button(context);
        thirdTab=new Button(context);
        switch (tabnum){
            case 2:
                createbtn(firstTab,first_txt);
                createbtn(secondTab,second_txt);
                break;
            case 3:
                createbtn(firstTab,first_txt);
                createbtn(secondTab,second_txt);
                createbtn(thirdTab,third_txt);
                break;
            default:
                break;

        }

    }


    private void createbtn(Button btn,CharSequence btn_txt){
        LayoutParams lp=new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lp.weight=1;
        btn.setText(btn_txt);
        btn.setTextColor(FONTCOLOR);
        btn.setTextSize(FONTSIZE);
        btn.setBackgroundResource(R.drawable.tab_btn_backstyle1);
        btn.setLayoutParams(lp);
        addView(btn);
    }

    public Button getFirstTab() {
        return firstTab;
    }

    public Button getSecondTab() {
        return secondTab;
    }

    public Button getThirdTab() {
        return thirdTab;
    }

    public int getTabnum() {
        return tabnum;
    }
}
