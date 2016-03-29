package com.zrodo.zrdapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.achartengine.ZrodoColumnChart;
import com.app.asyntask.ZrodAsyncTask;
import com.app.common.ZrodoActivity;
import com.app.entity.DataResult;
import com.app.provider.Provider;
import com.app.provider.ZrodoProviderManager;
import com.app.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by grandry.xu on 15-11-12.
 */
public class InfoQueryActivity extends ZrodoActivity implements Animation.AnimationListener {

    private LinearLayout queryContentLinear;
    private TextView infoEdit;
    private TextView txt_lydw,txt_fzsj,txt_jyzh,txt_cdly,txt_fzdw;
    private DataResult<String> result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_query_layout);

    }

    @Override
    protected void initView() {
        queryContentLinear = findView(R.id.query_content_linear);
        infoEdit=findView(R.id.query_edit);
        txt_lydw=findView(R.id.info_lydw);
        txt_fzsj=findView(R.id.info_fzsj);
        txt_jyzh=findView(R.id.info_jyzh);
        txt_fzdw=findView(R.id.info_fzdw);
        txt_cdly=findView(R.id.info_cdly);
    }

    @Override
    public boolean handlerException(Exception e) {
        return false;
    }

    public void queryInfo(View view) {
        /**
         * 隐藏软键盘
         */
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        clearTxt();
        if(TextUtils.isEmpty(infoEdit.getText().toString())){
            Toast.makeText(this,"请输入检疫证号",Toast.LENGTH_LONG).show();
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(InfoQueryActivity.this);
        View vi = inflater.inflate(R.layout.progress_dialog_content_view, null);
        ImageView imageView = (ImageView) vi.findViewById(R.id.img);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        new ZrodAsyncTask.Builder(InfoQueryActivity.this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() throws Exception {
                try{
                    Provider mprovider = ZrodoProviderManager.getmProvider();
                    result =mprovider.requestForInfoQueryData(infoEdit.getText().toString());


                }catch (Exception e){
                    throw  new Exception("网络错误，请求数据失败");
                }

            }

            @Override
            public void onPostExecute() {
                queryContentLinear.setVisibility(View.VISIBLE);
                int[] location = new int[2];
                queryContentLinear.getLocationOnScreen(location);
                Animation translationAnimation = new TranslateAnimation(0, 0, -location[1], 0);
                translationAnimation.setDuration(300);
                translationAnimation.setAnimationListener(InfoQueryActivity.this);
                queryContentLinear.startAnimation(translationAnimation);
                if(result!=null&&result.status== DataResult.Status.OK){
                    JSONObject obj= JSONUtil.toJSONObject(result.getData());
                    try{
                        JSONObject json=obj.getJSONObject("data");
                        txt_fzdw.setText(json.getString("fzdw"));
                        txt_fzsj.setText(json.getString("fzsj"));
                        txt_jyzh.setText(json.getString("jyzh"));
                        txt_lydw.setText(json.getString("lydw"));
                        txt_cdly.setText(json.getString("cdly"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }).setDialogView(vi)
          .setExceptionHandler(this)
          .execute();


    }


    private void clearTxt(){

       int len=queryContentLinear.getChildCount();
       for(int i=0;i<len;i++){
           LinearLayout layout= (LinearLayout) queryContentLinear.getChildAt(i);
           TextView txtView= (TextView) layout.getChildAt(1);
           txtView.setText("");
       }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Animation popAnimation = new TranslateAnimation(0, 0, -10, 0);
        popAnimation.setInterpolator(new CycleInterpolator(3));
        popAnimation.setDuration(500);
        queryContentLinear.startAnimation(popAnimation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void attachQueryResult(String key, String name) {
  /*      LinearLayout resultLayout = new LinearLayout(this);
        resultLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        resultLayout.setLayoutParams(lp);

        TextView txtKey = new TextView(this);
        LinearLayout.LayoutParams keyLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        keyLp.setMargins(10, 0, 10, 0);
        txtKey.setText(key);
        txtKey.setTextColor(getResources().getColor(R.color.white));
        txtKey.setGravity(Gravity.CENTER);
        txtKey.setLayoutParams(keyLp);
        resultLayout.addView(txtKey);

        TextView txtName = new TextView(this);
        LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        nameLp.setMargins(10, 0, 10, 0);
        txtName.setText(name);
        txtName.setTextColor(getResources().getColor(R.color.white));
        txtName.setGravity(Gravity.CENTER);
        txtName.setLayoutParams(keyLp);
        resultLayout.addView(txtName);
        queryContentLinear.addView(resultLayout);*/
    }
}