package com.app.asyntask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.common.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class ZrodAsyncTask extends AsyncTask<Void, Void, Void> {
    private ZrodTaskListener listener;
    private ExceptionHandler exceptionHandler;
    private ProgressDialog progressDialog;
    private View progressDialogView;
    private Context mContext;
    private boolean ifContinueWhileError = true;
    private List<Exception> exceptions = new ArrayList<Exception>();

    public ZrodAsyncTask() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mContext != null && progressDialogView != null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.show();
            progressDialog.setContentView(progressDialogView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }

    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            this.listener.doInBack();
        } catch (Exception e) {
            exceptions.add(e);
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            // View v = progressDialog.getWindow().getDecorView();
            //  progressDialog.getWindow().getWindowManager().removeViewImmediate(v);
            //   progressDialog.getWindow().getWindowManager().removeView(v);

        }
        if (exceptionHandler != null) {
            if(exceptions.size()>0){
                ifContinueWhileError=exceptionHandler.handlerException(exceptions.get(0));
            }

        }
        if (ifContinueWhileError) {
            this.listener.onPostExecute();
        }


    }


    public ZrodTaskListener getListener() {
        return listener;
    }

    public void setListener(ZrodTaskListener listener) {
        this.listener = listener;
    }

    public void setExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setDialogView(View View) {
        this.progressDialogView = View;
    }

    public interface ZrodTaskListener {
        public void doInBack() throws Exception;

        public void onPostExecute();
    }


    public static class Builder {
        private ZrodAsyncTask zrodTask;

        public Builder() {
            zrodTask = new ZrodAsyncTask();
        }

        public Builder(Context context) {
            zrodTask = new ZrodAsyncTask();
            zrodTask.setContext(context);
        }

        public Builder setZrdoTaskListener(ZrodTaskListener taskListener) {
            zrodTask.setListener(taskListener);
            return this;
        }

        public Builder setExceptionHandler(ExceptionHandler handler) {
            zrodTask.setExceptionHandler(handler);
            return this;
        }

        public Builder setDialogView(View dialogView) {
            zrodTask.setDialogView(dialogView);
            return this;
        }

        public ZrodAsyncTask create() {
            return zrodTask;
        }

        public void execute() {
            zrodTask.execute();
        }
    }
}
