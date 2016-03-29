package com.app.common;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.zrodo.zrdapp.LoginActivity;
import com.zrodo.zrdapp.ModeActivity;
import com.app.annotation.TabNum;
import com.app.util.ActivityUtil;
import com.zrodo.zrdapp.ModeGridActivity;

import java.lang.reflect.Field;

/**
 * Created by grandry.xu on 15-10-28.
 */
public abstract class ZrodoActivity extends Activity implements ExceptionHandler {

    public int header_tabnum;
    public static final boolean TAB_VIEW_IS_SHOWED=true;
    private ZrodoApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setActivityTabNum();
        initView();
        application= (ZrodoApplication) this.getApplication();
        application.addActivity(this);

    }


    /**
     * 获取控件
     */
    protected <T extends View> T findView(int resId){
        return (T) this.findViewById(resId);
    }


    /**
     * 初始化控件
     */
    protected abstract void initView();


    /**
     * 异常处理
     */
    protected void handError(){
        //todo
    }


    /**
     * 消息提示框
     */
    protected void alert(ActivityUtil.MsgDuration msgDuration,CharSequence msg){
        switch (msgDuration){
            case SHORT:
                ActivityUtil.shortToast(this,msg);
                break;
            case LONG:
                ActivityUtil.longToast(this,msg);
                break;
            default:
                break;
        }
    }

    public void setActivityTabNum(){
        Class clzz=this.getClass();
        try {
            Field tabnum=clzz.getField("header_tabnum");
            if(tabnum==null)
                return;
            if(clzz.isAnnotationPresent(TabNum.class)){
                TabNum tab= (TabNum) clzz.getAnnotation(TabNum.class);
                tabnum.set(this,tab.tabNum());
            }
        } catch (Exception e) {
        }

    }

    /**
     * 手机返回键按钮触发事件
     */
    @Override
    public void onBackPressed() {
        if(this instanceof LoginActivity || this instanceof ModeGridActivity){
            new AlertDialog.Builder(this).setTitle("退出系统")
                    .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           application.removeALL();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }else{
            super.onBackPressed();
        }

    }
}
