package com.zrodo.zrdapp;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrodo.zrdapp.uihandler.BtnArray;
import com.app.adapter.DataListAdapter;
import com.app.annotation.TabNum;
import com.app.asyntask.ZrodAsyncTask;
import com.app.common.DataQueryType;
import com.app.common.ZrodoActivity;
import com.app.entity.DataResult;
import com.app.provider.Provider;
import com.app.provider.ZrodoProviderManager;
import com.app.util.JSONUtil;
import com.app.widget.TabHeader;
import com.app.widget.ZrodoViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by grandry.xu on 15-10-29.
 */

@TabNum(tabNum = 2)
public class DataWarningActivity extends ZrodoActivity implements View.OnClickListener {
    private ZrodoViewPager viewPager;
    private TabHeader tabHeader;
    private Button firstButton, secondButton;
    private Button[] buttons;
    private List<View> views = new ArrayList<View>();
    private ViewGroup onTimeWarningView, historyWarningView;
    /**
     * 实时，历史预警数据列表数据集合
     */
    private LinkedList<DataResult<String>> results = new LinkedList<DataResult<String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_warning);
        asyncLoadContentBindToActivity();
    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.data_warning_viewpager);
        tabHeader = findView(R.id.tab_head);
        if (header_tabnum != tabHeader.getTabnum()) {
            throw new IllegalArgumentException("请检查属性header_tabnum设置的值");
        }
        firstButton = tabHeader.getFirstTab();
        firstButton.setOnClickListener(this);
        firstButton.setTag(0);
        firstButton.setBackgroundResource(R.drawable.tab_btn_backstyle2);
        secondButton = tabHeader.getSecondTab();
        secondButton.setOnClickListener(this);
        secondButton.setTag(1);
        buttons = new Button[]{firstButton, secondButton};
        LayoutInflater inflater = this.getLayoutInflater();
        onTimeWarningView = (ViewGroup) inflater.inflate(R.layout.ontime_warning_layout, null);
        historyWarningView = (ViewGroup) inflater.inflate(R.layout.history_warning_layout, null);
        views.add(onTimeWarningView);
        views.add(historyWarningView);

    }

    @Override
    public boolean handlerException(Exception e) {
        Toast.makeText(this, "wrong", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        viewPager.setCurrentItem((Integer) button.getTag());
    }

    private void asyncLoadContentBindToActivity(){
        attachViewPagerUIEvent();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.progress_dialog_content_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        new ZrodAsyncTask.Builder(this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() throws Exception {
                Thread.sleep(4000);
                Provider mprovider = ZrodoProviderManager.getmProvider();
                DataResult<String> result = mprovider.requestForOnTimeWarningData();
                results.add(result);

            }

            @Override
            public void onPostExecute() {
               final LayoutInflater flater=getLayoutInflater();
                final LinearLayout layout=findView(R.id.ontime_warning_scroll_linear);
                DataResult<String> result = results.removeLast();
                if(result==null||result.status!= DataResult.Status.OK){
                    Toast.makeText(DataWarningActivity.this,"未查询合适数据",Toast.LENGTH_LONG).show();
                    return;
                }
                JSONObject obj=JSONUtil.toJSONObject(result.getData());
                try{
                    String message=obj.getString("message");
                    JSONArray array=obj.getJSONArray("data");
                    if("false".equals(message)||array.length()<=0){
                        Toast.makeText(DataWarningActivity.this,"未查询合适数据",Toast.LENGTH_LONG).show();
                        return;
                    }
                    int len=array.length();
                    for(int i=0;i<len;i++){
                        JSONObject jb=array.getJSONObject(i);
                        View v=flater.inflate(R.layout.ontime_warning_result,null);
                        ((TextView)v.findViewById(R.id.warning_time_value)).setText(jb.getString("jcsj"));
                        ((TextView)v.findViewById(R.id.warning_area_value)).setText(jb.getString("province")+((jb.getString("city")!=null&&jb.getString("city").indexOf("ֱϽ")>0)?"":jb.getString("city")));
                        ((TextView)v.findViewById(R.id.detect_station_value)).setText(jb.getString("info_name"));
                        ((TextView)v.findViewById(R.id.detec_employee_value)).setText(jb.getString("info_user"));
                        ((TextView)v.findViewById(R.id.detect_item_value)).setText(jb.getString("itemname"));
                        ((TextView)v.findViewById(R.id.detect_result_value)).setText(jb.getString("resultname"));
                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        lp.setMargins(10,10,10,10);
                        layout.addView(v,lp);


                    }

                }catch (Exception e){

                }


            }
        }).setExceptionHandler(this)
          .setDialogView(view)
          .execute();
        onTimeWarningView.setTag(TAB_VIEW_IS_SHOWED);


    }

    private void attachViewPagerUIEvent() {
        BtnArray btnArray = new BtnArray(buttons);
        viewPager.setViews(views, btnArray);
        viewPager.setAfterSelectedListener(new ZrodoViewPager.ZrodoAfterPagerSelectedListener() {
            @Override
            public void afterPagerSelected(ViewGroup view, int index) {
                LayoutInflater inflater = LayoutInflater.from(DataWarningActivity.this);
                View vi = inflater.inflate(R.layout.progress_dialog_content_view, null);
                ImageView imageView = (ImageView) vi.findViewById(R.id.img);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();
                new ZrodAsyncTask.Builder(DataWarningActivity.this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
                    @Override
                    public void doInBack() throws Exception {
                        try{
                            Thread.sleep(4000);
                            Provider mprovider = ZrodoProviderManager.getmProvider();
                            DataResult<String> result = mprovider.requestForHistoryWarningData();
                            results.add(result);

                        }catch (Exception e){
                            throw new Exception("网络异常，获取日报表列表数据失败");
                        }

                    }

                    @Override
                    public void onPostExecute() {
                        DataResult<String> result = results.removeLast();
                        List<Map<String, String>> list = queryListData2List(result);
                        createViewAndBinderValues(historyWarningView, R.id.data_statistic_listview, list);


                    }
                }).setDialogView(vi)
                  .setExceptionHandler(DataWarningActivity.this)
                  .execute();

            }
        });
    }

    private void createViewAndBinderValues(ViewGroup parentView, int id, List<Map<String, String>> list) {
        View view = parentView.findViewById(id);
        if (view instanceof ListView) {
            ListView v = (ListView) view;
            v.setAdapter(new DataListAdapter(DataWarningActivity.this, R.layout.day_statistic_list_view_inflater, new String[]{"station"
                    , "laike", "kelun", "shading", "total"}, new int[]{R.id.detect_station, R.id.lai_ke_duo_ba_an, R.id.ke_lun_te_luo, R.id.sha_ding_an_chun, R.id.total}, list));

        }
        parentView.setTag(TAB_VIEW_IS_SHOWED);
    }

    private List<Map<String, String>> queryListData2List(DataResult<String> result) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (result != null && result.status == DataResult.Status.OK) {
            JSONObject obj = JSONUtil.toJSONObject(result.getData());
            try {
                JSONArray array = obj.getJSONArray("data");
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    JSONArray jsonArray = (JSONArray) array.get(i);
                    map.put("station", (String) jsonArray.get(0));
                    map.put("laike", (String) jsonArray.get(1));
                    map.put("kelun", (String) jsonArray.get(2));
                    map.put("shading", (String) jsonArray.get(3));
                    map.put("total", (String) jsonArray.get(4));
                    list.add(map);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;

    }

}