package com.zrodo.zrdapp.temp;

import com.zrodo.zrdapp.BDLocationActivity;
import com.zrodo.zrdapp.DataStatisticActivity;
import com.zrodo.zrdapp.DataWarningActivity;
import com.zrodo.zrdapp.InfoQueryActivity;
import com.zrodo.zrdapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-02-26.
 */
public class HandCreateMap {
    private static List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    public static Map<Object,Class<?>> classmap=new HashMap<Object,Class<?>>();//图标与activity的对应map

    static {
        classmap.put(R.drawable.sample, BDLocationActivity.class);
        classmap.put(R.drawable.statistic, DataStatisticActivity.class);
        classmap.put(R.drawable.warning, DataWarningActivity.class);
        classmap.put(R.drawable.source, InfoQueryActivity.class);

    }
    /**
     * 初始化九宫图数据，硬编码方式，后期可以采用服务端配置的方式灵活获取图片
     */
    public static List<Map<String,Object>> handMap(){
        if(list.size()>0){
            list.clear();
        }
        Map<String,Object> sampleMap=new HashMap<String,Object>();
        sampleMap.put("itemimag", R.drawable.sample);
        sampleMap.put("itemtext", "采样管理");
        list.add(sampleMap);

        Map<String,Object> statisticMap=new HashMap<String,Object>();
        statisticMap.put("itemimag", R.drawable.statistic);
        statisticMap.put("itemtext", "数据统计");
        list.add(statisticMap);


        Map<String,Object> analyMap=new HashMap<String,Object>();
        analyMap.put("itemimag", R.drawable.analysis);
        analyMap.put("itemtext", "数据分析");
        list.add(analyMap);

        Map<String,Object> warningMap=new HashMap<String,Object>();
        warningMap.put("itemimag", R.drawable.warning);
        warningMap.put("itemtext", "风险预警");
        list.add(warningMap);

        Map<String,Object> sourceMap=new HashMap<String,Object>();
        sourceMap.put("itemimag", R.drawable.source);
        sourceMap.put("itemtext", "溯源查询");
        list.add(sourceMap);

        Map<String,Object> videoMap=new HashMap<String,Object>();
        videoMap.put("itemimag", R.drawable.video);
        videoMap.put("itemtext", "视频监控");
        list.add(videoMap);

        Map<String,Object> locationMap=new HashMap<String,Object>();
        locationMap.put("itemimag", R.drawable.location);
        locationMap.put("itemtext", "定位签到");
        list.add(locationMap);

        Map<String,Object> messageMap=new HashMap<String,Object>();
        messageMap.put("itemimag", R.drawable.message);
        messageMap.put("itemtext", "信息中心");
        list.add(messageMap);


        Map<String,Object> personMap=new HashMap<String,Object>();
        personMap.put("itemimag", R.drawable.person);
        personMap.put("itemtext", "个人中心");
        list.add(personMap);

        return list;

    }
}
