package com.app.cache;

/**
 * Created by grandry.xu on 15-9-23.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * webview访问的地址缓存
 */
public class OpenUrlCache {

    public static  final String TABLE_NAME="zrodurl";

    public  void createTable(SQLiteDatabase db){
        String sql="create table if not exists "+TABLE_NAME+"(id integer primary key autoincrement,url text)";
        db.execSQL(sql);
    }

    public String loadFromDb(SQLiteOpenHelper helper){
       String url=null;
       SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{"url"},null,null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            url=cursor.getString(0);
        }
        cursor.close();
        db.close();
        return url;
    }

    public void insertOrUpdateURL(SQLiteOpenHelper helper,String url){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{"url"},null,null,null,null,null);
        ContentValues values=new ContentValues();
        values.put("url",url);
        if(cursor.getCount()>0){
            db.update(TABLE_NAME,values,null,null);
        }else{
            db.insertOrThrow(TABLE_NAME,null,values);
        }
        cursor.close();
        db.close();

    }

}
