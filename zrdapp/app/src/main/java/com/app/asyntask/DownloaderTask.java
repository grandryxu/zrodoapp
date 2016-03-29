package com.app.asyntask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.zrodo.zrdapp.R;

import java.io.IOException;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class DownloaderTask extends AsyncTask<String,Integer,Void> {

    private AlertDialog mDialog;
    private ProgressBar progressBar;
    private TextView mProgressTxt;
    private Context mContext;
    private ZrodAsyncTask.ZrodTaskListener mTaskListener;
    private String mDownUrl;
    private String mFileName;
    private String mDownDir;

    public DownloaderTask(Context context){
        mContext=context;
    }
    @Override
    protected Void doInBackground(String... params) {
        DownLoader downLoader=new DownLoader();
        downLoader.setDownLoaderListener(new DownLoader.DownLoaderListener() {
            @Override
            public void onGetFileSize(int fileSize) {
               publishProgress(0, fileSize);
            }

            @Override
            public void onDownloadSizeChanged(int downloadSize, int percentValue) {
              publishProgress(1,downloadSize, percentValue);
            }
        });
        try {
            downLoader.downLoad2SD(mDownUrl,mDownDir,mFileName);
        } catch (IOException e) {
            throw new RuntimeException("下载失败");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDialog.dismiss();
        Toast.makeText(mContext,"下载完成",Toast.LENGTH_LONG).show();
        if(mTaskListener!=null){
            mTaskListener.onPostExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(values==null||values.length<1){
            return;
        }
        int flag=values[0];
        switch (flag){
            case 0:
                progressBar.setMax(values[1]);
                break;
            case 1:
               if(values.length>=3){
                   progressBar.setProgress(values[1]);
                   mProgressTxt.setText(values[2]+"%");
               }
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       View dialogView= LayoutInflater.from(mContext).inflate(R.layout.dialog_app_download,null);
        mDialog=new AlertDialog.Builder(mContext).setCancelable(false)
                .setTitle("文件下载")
                .setView(dialogView)
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cancel(true);
                    }
                }).create();
        mDialog.show();
        progressBar= (ProgressBar) dialogView.findViewById(R.id.pb_download_progress);
        mProgressTxt= (TextView) dialogView.findViewById(R.id.txt_download_progress);

    }

    public DownloaderTask setParameter(String url,String dir,String filename){
        this.mDownUrl=url;
        this.mDownDir=dir;
        this.mFileName=filename;
        return this;

    }

    public DownloaderTask setmTaskListener(ZrodAsyncTask.ZrodTaskListener mTaskListener) {
        this.mTaskListener = mTaskListener;
        return this;
    }
}
