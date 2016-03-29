package com.zrodo.zrdapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.asyntask.NetworkFacade;
import com.app.asyntask.ZrodAsyncTask;
import com.app.common.ZrodoActivity;
import com.app.entity.DataResult;
import com.app.provider.Provider;
import com.app.provider.ZrodoProviderManager;
import com.app.util.ActivityUtil;
import com.app.util.JSONUtil;
import com.app.widget.ClearEditText;
import com.app.widget.ZrodoViewPager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by grandry.xu on 15-10-26.
 */
public class LoginActivity extends ZrodoActivity {
    private ClearEditText txt_user;
    private ClearEditText txt_password;
    private DataResult<String> loginResult;
    private Provider mProvider;
    private SharedPreferences preferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    protected void initView() {
        txt_user = findView(R.id.user_info);
        txt_password = findView(R.id.pass_info);
        preferences=getSharedPreferences("user_info",MODE_PRIVATE);
        String username=preferences.getString("username","admin");
        if(!TextUtils.isEmpty(username)){
            txt_user.setText(username);
        }
    }


    /**
     * 登录按钮click事件
     *
     * @param view
     */
    public void onClick(View view) {
        final String username = txt_user.getText().toString();
        final String password = txt_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            txt_user.shakeAimation(5);
            alert(ActivityUtil.MsgDuration.SHORT, "用户名不能为空");
            return;

        }
        if (TextUtils.isEmpty(password)) {
            txt_password.shakeAimation(5);
            alert(ActivityUtil.MsgDuration.SHORT, "密码不能为空");
            return;
        }
        new ZrodAsyncTask.Builder().setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() throws Exception {
                mProvider= ZrodoProviderManager.getmProvider();
                try {
                   loginResult=mProvider.login(username, password);
                } catch (Exception e) {
                    throw new Exception("网络异常，登录失败");
                }
            }

            @Override
            public void onPostExecute() {
                if(loginResult!=null&&loginResult.status==DataResult.Status.OK){
                    JSONObject obj= JSONUtil.toJSONObject(loginResult.getData());
                    try {
                        String message=obj.getString("message");
                        if("false".equals(message)){
                            Toast.makeText(LoginActivity.this,"用户名密码错误",Toast.LENGTH_LONG).show();
                            return;
                        }
                        JSONObject result=obj.getJSONObject("result");
                        String userId=result.getString("userid");
                        ZrodoProviderManager.userId=userId;
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("username",username);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, ModeGridActivity.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).setExceptionHandler(this)
          .execute();



    }

    @Override
    public boolean handlerException(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        return false;
    }
}