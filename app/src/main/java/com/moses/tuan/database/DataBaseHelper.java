package com.moses.tuan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by 丹 on 2014/12/11.
 * cursor 的遍历，注意for（）循环和 moveToNext（）的关系。
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    /**
     * 防止重复获取其对象，采用单例模式
     */
    private static DataBaseHelper mosesDataBaseHelper = null;
    private static final String DB_NAME = "mydata.db";
    private static final int VERSION = 1;

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DataBaseHelper getInstance(Context context) {
        if (mosesDataBaseHelper == null) {
            mosesDataBaseHelper = new DataBaseHelper(context);
        }
        return mosesDataBaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(userName varchar(20) not null,password varchar(60) not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// 数据库升级
        Log.i("Tag","DatabaseHelper onUpgrade oldVersion :" + oldVersion + " newVersion :" + newVersion);

        if(newVersion < 13){
            String sql = "alter table "+"user"+" add sex int(10)";
            db.execSQL(sql);
        }
    }
}
