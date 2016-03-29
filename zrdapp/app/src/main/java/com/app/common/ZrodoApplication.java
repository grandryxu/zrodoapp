package com.app.common;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by grandry.xu on 15-10-29.
 */
public class ZrodoApplication extends Application {

    /**
     * 管理activity，在退出系统前finish所有activity
     */
    private ArrayList<Activity> runningActivityList=new ArrayList<Activity>();

    public void addActivity(Activity activity){
        runningActivityList.add(activity);
    }

    public void removeActivity(Activity activity){
        runningActivityList.remove(activity);
    }

    public void removeALL(){
        for(Activity activity:runningActivityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
