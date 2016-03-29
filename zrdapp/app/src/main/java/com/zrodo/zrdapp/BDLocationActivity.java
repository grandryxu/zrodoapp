package com.zrodo.zrdapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.asyntask.NetworkFacade;
import com.app.asyntask.ZrodAsyncTask;
import com.app.bean.BaseSample;
import com.app.common.ExceptionHandler;
import com.app.common.ZrodoActivity;
import com.app.entity.DataResult;
import com.app.provider.Provider;
import com.app.provider.ZrodoProviderManager;
import com.app.util.BitmapUtil;
import com.app.widget.ClearEditText;
import com.app.widget.ZrodoHeaderView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.SimpleFormatter;

/**
 * Created by lenovo on 2016-02-23.
 */
public class BDLocationActivity extends ZrodoActivity implements BDLocationListener,SurfaceHolder.Callback {
    private TextView locInfo,longilati;
    private LinearLayout sample_info_area;
    private BaseSample sample=new BaseSample();
    private ImageView photoImg;
    private TextView pic_sample_name,pic_sample_id,pic_sample_source,sample_time,sample_tempr;
    private ZrodoHeaderView headerView;
    private static int REQUEST_TACKPIC_CODE=1;
    private String tmpFilename=Environment.getExternalStorageDirectory()+"/123456789.jpg";
    private int mPhotoSizeInKb = 200;
    private ProgressDialog uploadDialog;


    public LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_info);
        uploadDialog=new ProgressDialog(this);
        sample = new BaseSample();
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(this);
        initLocation();
        mLocationClient.start();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


    }



    @Override
    protected void initView() {
        locInfo = findView(R.id.location_addr);
        longilati=findView(R.id.location_logilati);
        photoImg=findView(R.id.phontoImg);
        sample_info_area=findView(R.id.sample_info_area);
        pic_sample_id=findView(R.id.pic_sample_id);
        pic_sample_name=findView(R.id.pic_sample_name);
        pic_sample_source=findView(R.id.pic_sample_source);
        sample_time=findView(R.id.sample_time);
        sample_tempr=findView(R.id.sample_temperature);
        headerView=findView(R.id.head);
        Button rightButton=headerView.getRightButton();
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncUpload();
            }
        });
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int code = bdLocation.getLocType();
        String addr="",lati="",longi="";
        if(code==BDLocation.TypeGpsLocation){
            addr=bdLocation.getAddrStr();
            lati = String.format("%.6f", bdLocation.getLatitude());
            longi = String.format("%.6f",bdLocation.getLongitude());
        }
        if(code==BDLocation.TypeNetWorkLocation){
            addr=bdLocation.getAddrStr();
            lati = String.format("%.6f", bdLocation.getLatitude());
            longi = String.format("%.6f",bdLocation.getLongitude());
        }
        StringBuilder sb = new StringBuilder("");
        sb.append("经度:").append(longi)
                .append(";").append("纬度:").append(lati);
        locInfo.setText(addr);
        longilati.setText(sb.toString());
        sample.setAddr(addr);
        sample.setAddrLati(lati);
        sample.setAddrLongi(longi);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setScanSpan(1000*2);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setIsNeedLocationDescribe(true);
        option.setLocationNotify(true);
        //option.setPriority(LocationClientOption.NetWorkFirst);
        mLocationClient.setLocOption(option);

    }

    @Override
    public boolean handlerException(Exception e) {
        if(uploadDialog.isShowing()){
            uploadDialog.dismiss();
        }
        Toast.makeText(this,"上传失败",Toast.LENGTH_LONG).show();
        return false;
    }


    public void takePic(View view){
        File tmpFile=new File(tmpFilename);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile));
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 120 * 1024);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_TACKPIC_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TACKPIC_CODE){
            String photoPath=tmpFilename;
            File file=new File(photoPath);
            if(!file.exists()){
                return;
            }
            final Bitmap bm=convert2SmallBitmap(photoPath);
            final Dialog dialog=new Dialog(this,R.style.Dialog_FullScreen);
            View contentView = LayoutInflater.from(this).inflate(R.layout.activity_camera_preview, null);
            ImageView iv = (ImageView) contentView.findViewById(R.id.photo);
            iv.setImageBitmap(bm);
            Button btnOK = (Button) contentView.findViewById(R.id.btn_ok);
            Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    photoImg.setVisibility(View.VISIBLE);
                    sample.setPhontoBase64(BitmapUtil.convertToBase64(bm));
                    //继续缩小图片尺寸，显示在缩略图上
                    Bitmap bitmap=BitmapUtil.scaleBitmapSmall(bm,0.6f,0.6f);
                    photoImg.setImageBitmap(getRoundedCornerBitmap(bitmap, 15.0f));
                    sample_info_area.setVisibility(View.VISIBLE);
                    notifyAutoGenerate();

                }
            });
            dialog.setContentView(contentView);
            dialog.show();
            file.delete();

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 转换成小图片
     * @return
     */
    private Bitmap convert2SmallBitmap(String photoPath){
        //将图片压缩,大小在mPhotoSizeInKb左右。
        byte[] data = BitmapUtil.scaleAndCompress(photoPath, 460, 800, mPhotoSizeInKb);
        Bitmap resizedBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        if (resizedBitmap.getWidth() > resizedBitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.setRotate(90);

            resizedBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight(), matrix, true);
        }
        return resizedBitmap;

    }

    /**
     * 创建圆角图片
     */
    public  Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 图片显示时候通知自动生成样品部分信息
     */
    private void notifyAutoGenerate(){
        StringBuilder sIdBuilder=new StringBuilder(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        String s="";
        while(s.length()<4){
            s=s.concat(String.format("%.0f",Math.random()*10));
        }
        String sId=sIdBuilder.append(s).toString();
        String sTime=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        pic_sample_id.setText(sId);
        sample_time.setText(sTime);
        sample.setsId(sId);
        sample.setTime(sTime);



    }

    /**
     * 上传数据
     */
    private void asyncUpload(){
        //上传前先检查数据是否全
        LinearLayout layout=findView(R.id.sample_info_parent);
        int count=layout.getChildCount();
        for(int i=0;i<count;i++){
            View editText=layout.getChildAt(i);
            if(editText instanceof ClearEditText){
                if(TextUtils.isEmpty(((ClearEditText) editText).getText().toString())){
                    ((ClearEditText) editText).shakeAimation(5);
                    String tip=((TextView) layout.getChildAt(i-1)).getText().toString();
                    Toast.makeText(this,tip+"不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

            }

        }
        sample.setSource(pic_sample_source.getText().toString());
        sample.setName(pic_sample_name.getText().toString());
        sample.setTemperature(sample_tempr.getText().toString());
        uploadDialog.setMessage("正在上传");
        uploadDialog.show();

        new ZrodAsyncTask.Builder(this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() throws Exception {

                Provider mprovider = ZrodoProviderManager.getmProvider();
                JSONObject obj=new JSONObject();
                constructJSONObj(obj);
                String postData=obj.toString();
                DataResult<String> result=mprovider.postSamplePic(postData);
                if (result.status!= DataResult.Status.OK)
                    throw  new Exception("上传失败");
            }

            @Override
            public void onPostExecute() {
                if(uploadDialog.isShowing()){
                    uploadDialog.dismiss();
                    Toast.makeText(BDLocationActivity.this,"上传成功",Toast.LENGTH_LONG).show();
                }

            }
        }).setExceptionHandler(this).execute();

    }

    /**
     * 构建jsonobject
     */

    private void constructJSONObj(JSONObject obj){
        try {
            obj.put("userid",ZrodoProviderManager.userId);
            obj.put("sampleId",sample.getsId());
            obj.put("sampleName",sample.getName());
            obj.put("sampleSource",sample.getSource());
            obj.put("picTime",sample.getTime());
            obj.put("location",sample.getAddr());
            obj.put("latitude",sample.getAddrLati());
            obj.put("longitude",sample.getAddrLongi());
            obj.put("sampleTemp",sample.getTemperature());
            obj.put("photo",sample.getPhontoBase64());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
