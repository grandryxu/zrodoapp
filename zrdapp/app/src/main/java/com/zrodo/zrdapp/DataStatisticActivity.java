package com.zrodo.zrdapp;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.app.achartengine.ZrodoCircleChart;
import com.app.achartengine.ZrodoLineChart;
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
import com.zrodo.zrdapp.uihandler.BtnArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by grandry.xu on 15-10-29.
 */
@TabNum(tabNum = 3)
public class DataStatisticActivity extends ZrodoActivity implements View.OnClickListener {
    private ZrodoViewPager viewPager;
    private TabHeader header;
    private Button firstBtn, secondBtn, thirdBtn;
    private Button[] buttons;
    private List<View> views = new ArrayList<View>();
    private ViewGroup dailyDataView, monthDataView, annualDataView;
    /**
     * 列表查询数据集合（日报表列表查询，月报表列表查询，年报表列表查询）
     */
    private LinkedList<DataResult<String>> results = new LinkedList<DataResult<String>>();
    private static final String[] detecItems = {"莱克多巴胺", "沙丁胺醇", "克伦特罗"};
    /**
     * 饼状图数据集合
     */
    private LinkedList<DataResult<String>> pieResults = new LinkedList<DataResult<String>>();
    /**
     * 柱状图数据集合
     */
    private LinkedList<DataResult<String>> columnResults = new LinkedList<DataResult<String>>();
    /**
     * 折线图数据集合
     */
    private LinkedList<DataResult<String>> lineResults = new LinkedList<DataResult<String>>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_statistic);
        asyncLoadContentBindToActivity();


    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.data_statistic_viewpager);
        header = findView(R.id.tab_head);
        if (header_tabnum != header.getTabnum())
            throw new IllegalArgumentException("请检查属性header_tabnum设置的值");
        firstBtn = header.getFirstTab();
        firstBtn.setOnClickListener(this);
        firstBtn.setTag(0);
        firstBtn.setBackgroundResource(R.drawable.tab_btn_backstyle2);
        secondBtn = header.getSecondTab();
        secondBtn.setOnClickListener(this);
        secondBtn.setTag(1);
        thirdBtn = header.getThirdTab();
        thirdBtn.setOnClickListener(this);
        thirdBtn.setTag(2);
        buttons = new Button[]{firstBtn, secondBtn, thirdBtn};
        LayoutInflater inflater = this.getLayoutInflater();
        dailyDataView = (ViewGroup) inflater.inflate(R.layout.data_statistic_layout, null);
        monthDataView = (ViewGroup) inflater.inflate(R.layout.data_statistic_layout, null);
        annualDataView = (ViewGroup) inflater.inflate(R.layout.data_statistic_layout, null);
        views.add(dailyDataView);
        views.add(monthDataView);
        views.add(annualDataView);


    }

    private void asyncLoadContentBindToActivity() {
        attachViewPagerUIEvent();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.progress_dialog_content_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        new ZrodAsyncTask.Builder(this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
            @Override
            public void doInBack() throws Exception {
                try {
                    Thread.sleep(4000);
                    Provider mprovider = ZrodoProviderManager.getmProvider();
                    DataResult<String> result = mprovider.requestForStatisticData(DataQueryType.DAY);
                    results.add(result);
                    DataResult<String> pieResult = mprovider.requestForPieChartData(DataQueryType.DAY);
                    pieResults.add(pieResult);
                } catch (Exception e) {
                    throw new Exception("网络异常，获取日报表列表数据失败");
                }
            }

            @Override
            public void onPostExecute() {
                DataResult<String> result = results.removeLast();
                List<Map<String, String>> list = queryListData2List(result);
                createViewAndBinderValues(dailyDataView, R.id.data_statistic_listview, list);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                /**
                 * 柱形图
                 */
         /*       ZrodoColumnChart chart = new ZrodoColumnChart(DataStatisticActivity.this);
                LinearLayout bartChartLinear = (LinearLayout) dailyDataView.findViewById(R.id.column_chart);
                bartChartLinear.addView(chart.getView(), lp);*/
                /**
                 * 饼状图
                 */
                LinearLayout pieChartLinear = (LinearLayout) dailyDataView.findViewById(R.id.circle_chart);
                ZrodoCircleChart pieChart = new ZrodoCircleChart(DataStatisticActivity.this);
                pieChartLinear.addView(pieChart.getView(queryPieData2Array(pieResults.removeLast()), detecItems), lp);
                /**
                 * 线性图
                 */
              /*  LinearLayout lineChartLinear = (LinearLayout) dailyDataView.findViewById(R.id.line_chart);
                ZrodoLineChart lineChart = new ZrodoLineChart(DataStatisticActivity.this);
                lineChartLinear.addView(lineChart.getView(), lp);*/
                // attachViewPagerUIEvent();
            }
        })
                .setDialogView(view)
                .setExceptionHandler(this)
                .execute();

    }

    private void attachViewPagerUIEvent() {
       final BtnArray btnArray = new BtnArray(buttons);
        viewPager.setViews(views, btnArray);
        viewPager.setAfterSelectedListener(new ZrodoViewPager.ZrodoAfterPagerSelectedListener() {
            @Override
            public void afterPagerSelected(final ViewGroup view, final int i) {
                LayoutInflater inflater = LayoutInflater.from(DataStatisticActivity.this);
                View vi = inflater.inflate(R.layout.progress_dialog_content_view, null);
                ImageView imageView = (ImageView) vi.findViewById(R.id.img);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();
                new ZrodAsyncTask.Builder(DataStatisticActivity.this).setZrdoTaskListener(new ZrodAsyncTask.ZrodTaskListener() {
                    @Override
                    public void doInBack() {
                        try {
                            Provider mprovider = ZrodoProviderManager.getmProvider();
                            //Thread.sleep(2000);
                            if (i == 1) {
                                DataResult<String> result = mprovider.requestForStatisticData(DataQueryType.MONTH);
                                results.add(result);
                                DataResult<String> pieResult = mprovider.requestForPieChartData(DataQueryType.MONTH);
                                pieResults.add(pieResult);
                            }
                            if (i == 2) {
                                DataResult<String> result = mprovider.requestForStatisticData(DataQueryType.ANNUAL);
                                results.add(result);
                                DataResult<String> pieResult = mprovider.requestForPieChartData(DataQueryType.ANNUAL);
                                pieResults.add(pieResult);
                                DataResult<String> lineResult = mprovider.requestForLineChartData(DataQueryType.ANNUAL);
                                lineResults.add(lineResult);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPostExecute() {
                        //  listener.doAfterBackExecute(v, R.id.data_statistic_listview, DataQueryType.DAY);
                        DataResult<String> result = results.removeLast();
                        List<Map<String, String>> list = queryListData2List(result);
                        createViewAndBinderValues(view, R.id.data_statistic_listview, list);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        /**
                         * 饼状图
                         */

                        LinearLayout pieChartLinear = (LinearLayout) view.findViewById(R.id.circle_chart);
                        ZrodoCircleChart pieChart = new ZrodoCircleChart(DataStatisticActivity.this);
                        pieChartLinear.addView(pieChart.getView(queryPieData2Array(pieResults.removeLast()), detecItems), lp);

                        /**
                         * 柱状图
                         */
                     /*   ZrodoColumnChart chart = new ZrodoColumnChart(DataStatisticActivity.this);
                        LinearLayout bartChartLinear = (LinearLayout) view.findViewById(R.id.column_chart);
                        bartChartLinear.addView(chart.getView(), lp);*/
                        if (i == 2) {
                            Object[] objs = queryLineData2Array(lineResults.removeLast());
                            if(objs==null)
                                return;
                            List<double[]> xValues = (List<double[]>) objs[0];
                            List<double[]> yValues = (List<double[]>) objs[1];
                            /**
                             * 线性图
                             */
                            LinearLayout lineChartLinear = (LinearLayout) view.findViewById(R.id.line_chart);
                            ZrodoLineChart lineChart = new ZrodoLineChart(DataStatisticActivity.this);
                            lineChartLinear.addView(lineChart.getView(detecItems, xValues, yValues), lp);
                            lineChartLinear.setVisibility(View.VISIBLE);

                        }

                    }
                }).setDialogView(vi)
                        .execute();
            }
        });
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

    private void createViewAndBinderValues(ViewGroup parentView, int id, List<Map<String, String>> list) {
        View view = parentView.findViewById(id);
        if (view instanceof ListView) {
            ListView v = (ListView) view;
            v.setAdapter(new DataListAdapter(DataStatisticActivity.this, R.layout.day_statistic_list_view_inflater, new String[]{"station"
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

    private double[] queryPieData2Array(DataResult<String> pieResult) {
        double[] d = new double[detecItems.length];
        if (pieResult != null && pieResult.status == DataResult.Status.OK) {
            JSONObject obj = JSONUtil.toJSONObject(pieResult.getData());
            try {
                String message = obj.getString("message");
                if ("false".equals(message)) {
                    return null;
                }
                JSONObject data = obj.getJSONObject("data");
                d[0] = data.getDouble("lk");
                d[1] = data.getDouble("sd");
                d[2] = data.getDouble("ys");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return d;

    }

    private List<?>[] queryLineData2Array(DataResult<String> lineResult) {
        List<?>[] listArrays = new List<?>[2];
        if (lineResult != null && lineResult.status == DataResult.Status.OK) {
            if("{}".equals(lineResult.getData())){
                return null;
            }
            JSONObject obj = JSONUtil.toJSONObject(lineResult.getData());
            try {
                //JSONArray arr = obj.getJSONArray("data");
                JSONArray lkArray=obj.getJSONArray("lk");
                JSONArray ysArray=obj.getJSONArray("ys");
                JSONArray sdArray=obj.getJSONArray("sd");
                List<double[]> xValueList = new ArrayList<double[]>();
                List<double[]> yValueList = new ArrayList<double[]>();
                jsonArray2xyValueList(lkArray,xValueList,yValueList);
                jsonArray2xyValueList(ysArray,xValueList,yValueList);
                jsonArray2xyValueList(sdArray,xValueList,yValueList);
                listArrays[0] = xValueList;
                listArrays[1] = yValueList;


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listArrays;
    }

    private void jsonArray2xyValueList(JSONArray array,List<double[]> xValueList,List<double[]> yValueList){
        try{
            int len = array.length();
            double[] xValues = new double[len];
            double[] yValues = new double[len];
            for (int i = 0; i < len; i++) {
                JSONObject json = array.getJSONObject(i);
                xValues[i] = json.getDouble("name");
                yValues[i] = json.getDouble("value");
            }
            xValueList.add(xValues);
            yValueList.add(yValues);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}