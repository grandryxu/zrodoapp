package com.app.provider;

/**
 * Created by grandry.xu on 15-12-29.
 */
public class ZrodoProviderManager {

    private static Provider mProvider;
    public static String userId;

    public static Provider getmProvider() {
        if(mProvider==null){
            mProvider=new ZrodoProviderImp();
        }
        return mProvider;
    }
}
