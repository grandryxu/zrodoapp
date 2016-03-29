package com.zrodo.zrdapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;

import com.app.asyntask.DownloaderTask;
import com.app.asyntask.NetworkFacade;
import com.app.asyntask.ZrodAsyncTask;
import com.app.cache.OpenUrlCache;
import com.app.common.ZrodoActivity;
import com.app.entity.DataResult;
import com.app.entity.Update;
import com.app.sqlite.DatabaseHelper;
import com.app.util.JSONUtil;
import com.app.util.NetworkUtil;

import org.json.JSONObject;

import java.io.File;

public class MainActivity extends ZrodoActivity {

    private Update mUpdate;

    private static String apkFileName="zrodphone.apk";

    //webview访问地址
    private String accessUrl;
    private boolean isRemoteUrl=false;//判断是否需要远程获取webview的访问地址
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper=new DatabaseHelper(this);
        initEnvironment();

    }

    @Override
    protected void initView() {

    }

    private void initEnvironment() {
        new ZrodAsyncTask.Builder().setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() {
  /*               try {
                     Thread.sleep(3000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }*/
                boolean isOnLine= NetworkUtil.isAvailable(MainActivity.this);
                if(isOnLine){
                    mUpdate=getUpdate();
                }else{
                    OpenUrlCache cache=new OpenUrlCache();
                    accessUrl=cache.loadFromDb(mHelper);
                    if(accessUrl==null){
                        accessUrl="http://www.zrodo.cn";
                    }
                }
            }

            @Override
            public void onPostExecute() {
                if(mUpdate==null||!mUpdate.isUpdate()){
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("webUrl",accessUrl);
                    startActivity(intent);
                    return ;
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("软件更新")
                        .setCancelable(false)
                        .setMessage("检测到新版本，请更新")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sdkPath= Environment.getExternalStorageDirectory().getPath();
                                startDownloadSchedule(mUpdate.getApkDownloadUrl(),sdkPath,apkFileName);
                            }
                        }).create().show();
            }
        }).create().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private Update getUpdate(){
        Update update=null;
      //  String url="http://115.29.106.147:8080/DetectList/getapkversion.do";
        String url="";
        NetworkFacade facade=new NetworkFacade();
        DataResult<String> result= null;
        try {
            result = facade.getMethod(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result!=null&&result.status==DataResult.Status.OK){
            JSONObject obj= JSONUtil.toJSONObject(result.getData());
            update=new Update();
            update.setVersionCode(obj.optString("versionCode"));
            update.setApkDownloadUrl(obj.optString("apkUrl"));
            update.setUpdate(getIsUpdate(update.getVersionCode()));
            accessUrl=obj.optString("webUrl");//获取webview访问url并更新到sqlite
            try{
                OpenUrlCache cache=new OpenUrlCache();
                cache.insertOrUpdateURL(mHelper,accessUrl);
            }catch (Exception e){
                String s=e.getMessage();
                e.printStackTrace();
            }

        }
        return  update;
    }

    private boolean getIsUpdate(String serverVersionCode){
        try {
            PackageInfo info=MainActivity.this.getPackageManager().getPackageInfo("com.example.zrodphone",0);
            if(info!=null){
                return compareVersionCode(serverVersionCode,info.versionName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean compareVersionCode(String serverVersionCode,String appVersionCode){
        if(serverVersionCode==null||appVersionCode==null){
            return false;
        }
        if(!serverVersionCode.trim().equals(appVersionCode.trim())){
            return true ;
        }
        return  false;
    }

    //下载更新包
    private void startDownloadSchedule(String url,final String dir,final String filename){

        new DownloaderTask(MainActivity.this).setParameter(url, dir, filename)
                .setmTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
                    @Override
                    public void doInBack() {

                    }

                    @Override
                    public void onPostExecute() {
                        installApk(MainActivity.this,dir,filename);

                    }
                }).execute();
    }


    private void installApk(Activity activity,String path,String fileName){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path,fileName)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this).setTitle("退出系统")
                    .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          /*  Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                            intent.putExtra("webUrl",accessUrl);
                            startActivity(intent);*/
                        }
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean handlerException(Exception e) {
         return false;
    }
}
