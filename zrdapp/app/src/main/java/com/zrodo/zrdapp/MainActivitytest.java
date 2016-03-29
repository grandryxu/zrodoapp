package com.zrodo.zrdapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

public class MainActivitytest extends Activity implements TencentLocationListener{
    private TencentLocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   try{
            System.loadLibrary("tencentloc");
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }*/
        setContentView(R.layout.activity_main);
       // setContentView(R.layout.acac);




        mLocationManager = TencentLocationManager.getInstance(this);




    }

    public void click(View view){

       int a= mLocationManager.requestLocationUpdates(TencentLocationRequest.create()
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME)
                .setInterval(1000).setAllowDirection(true), this);
        Toast.makeText(this,"hehhe"+"-----------"+a,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if(i==TencentLocation.ERROR_OK){
            String add=tencentLocation.getAddress();
            Toast.makeText(this,tencentLocation.getAddress()+"---",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
     //   Toast.makeText(this,s1,Toast.LENGTH_LONG).show();


    }
}
