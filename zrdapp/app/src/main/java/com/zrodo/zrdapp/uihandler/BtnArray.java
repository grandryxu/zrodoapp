package com.zrodo.zrdapp.uihandler;

import android.content.Context;
import android.widget.Button;

/**
 * Created by grandry.xu on 15-11-6.
 */
public class BtnArray {
    private Button[] btns;
    public BtnArray(Button[] btns){
          this.btns=btns;
    }
    public Button getItemAtIndex(int index){
        return btns[index];
    }

    public Button[] getBtnArray(){
        return btns;
    }
}
