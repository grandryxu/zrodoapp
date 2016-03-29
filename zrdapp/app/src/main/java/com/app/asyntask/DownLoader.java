package com.app.asyntask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class DownLoader {
    private String downLoadUrl;
    private DownLoaderListener downLoaderListener;

    public DownLoaderListener getDownLoaderListener() {
        return downLoaderListener;
    }

    public void setDownLoaderListener(DownLoaderListener downLoaderListener) {
        this.downLoaderListener = downLoaderListener;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public void downLoad2SD(String downLoadUrl,String storeDir,String filename)throws IOException {

            URL url=new URL(downLoadUrl);
        URLConnection connection=url.openConnection();
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Accept-Encoding","identity");
        connection.setConnectTimeout(5*1000);
        connection.connect();

        //获取数据流
        InputStream is=connection.getInputStream();
        if(is==null){
            throw new RuntimeException("inputstream is null");
        }
        //获取下载文件大小
        int fileSize=connection.getContentLength();
        if(downLoaderListener!=null){
            downLoaderListener.onGetFileSize(fileSize);
        }
        File file=new File(storeDir,filename);
        FileOutputStream fos=new FileOutputStream(file);
        int downloadSize=0;
        int readSize=0;
        byte[] buffer=new byte[1024];
        while((readSize=is.read(buffer))!=-1){
            fos.write(buffer,0,readSize);
            downloadSize+=readSize;
            if(downLoaderListener!=null){
                downLoaderListener.onDownloadSizeChanged(downloadSize,downloadSize*100/fileSize);
            }
        }
        is.close();

    }




    public interface  DownLoaderListener{
        void onGetFileSize(int fileSize);
        void onDownloadSizeChanged(int downloadSize, int percentValue);
    }
}
