package com.zrodo.zrdapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrodo.zrdapp.R;


/**
 * Created by grandry.xu on 15-11-17.
 */
public class PersonalSettingOneFrag extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.person_frag_1,container,false);
    }
}
