package com.zrodo.zrdapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.zrodo.zrdapp.fragment.PersonalSettingOneFrag;
import com.zrodo.zrdapp.fragment.PersonalSettingTwoFrag;
import com.app.adapter.ZrodoExLstViewAdapter;
import com.app.common.ZrodoActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by grandry.xu on 15-11-12.
 */
public class PersonalSettingActivity extends ZrodoActivity {
    private FragmentManager manager;
    private ExpandableListView exListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_settting_layout);
        String[] groupLst = getResources().getStringArray(R.array.exLstGroupName);
        Object[] childlst = transfarArray2ObjectArray(getResources().getStringArray(R.array.exLstChild));
        exListView.setAdapter(new ZrodoExLstViewAdapter(this, groupLst, childlst));
        exListView.setGroupIndicator(null);
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
/*          for(int i=0;i<exListView.getCount();i++){
              exListView.expandGroup(i);
          }*/

    }

    @Override
    protected void initView() {
        manager = getFragmentManager();
        exListView = findView(R.id.setting_expand_listview);
    }


    @Override
    public boolean handlerException(Exception e) {
        return false;
    }


    private Object[] transfarArray2ObjectArray(String[] array) {
        int count = array.length;
        List<String[]> list = new ArrayList<String[]>();
        for (int i = 0; i < count; i++) {
            if (array[i].contains(",")) {
                String[] s = array[i].split(",");
                list.add(s);
            } else {
                list.add(new String[]{array[i]});
            }
        }
        Object[] obj = new Object[list.size()];
        Iterator<String[]> iterator = list.listIterator();
        for (int i = 0; iterator.hasNext(); i++) {
            String[] strarry = iterator.next();
            obj[i] = strarry;
        }
        return obj;
    }

}