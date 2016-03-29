package com.zrodo.zrdapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.app.common.ZrodoActivity;
import com.zrodo.zrdapp.temp.HandCreateMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lenovo on 2016-02-26.
 * 页面九宫图布局
 */
public class ModeGridActivity extends ZrodoActivity implements GridView.OnItemClickListener{
    private GridView gridView;
    private List<Map<String,Object>>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_grid);
    }

    @Override
    protected void initView() {
        list= HandCreateMap.handMap();
        gridView=findView(R.id.gridView);
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.mode_grid_item,new String[]{"itemimag","itemtext"},
                new int[]{R.id.ItemImage,R.id.ItemText});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public boolean handlerException(Exception e) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Object> map=list.get(position);
        Object drawableId=map.get("itemimag");
        if(HandCreateMap.classmap.containsKey(drawableId)){
            Intent intent=new Intent(this,HandCreateMap.classmap.get(drawableId));
            startActivity(intent);

        }else{
            Toast.makeText(this,"功能即将开放",Toast.LENGTH_SHORT).show();
        }
    }
}
