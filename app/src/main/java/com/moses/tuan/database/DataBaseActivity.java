package com.moses.tuan.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moses.tuan.R;
import com.moses.tuan.sign.MainSignActivity;

/**
 * Created by 丹 on 2014/12/11.
 */
public class DataBaseActivity extends Activity implements View.OnClickListener {
    Button mInsertBtn, mDeleteBtn, mUpdateBtn, mQueryBtn, mJumpBtn, mClearBtn, mLoadBtn;
    private TextView mShowInfoTxt;

    DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_helper);
        initViews();
    }

    /**
     * 销毁活动时关闭数据库
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mInsertBtn = (Button) findViewById(R.id.insertBtn);
        mDeleteBtn = (Button) findViewById(R.id.deleteBtn);
        mUpdateBtn = (Button) findViewById(R.id.updateBtn);
        mQueryBtn = (Button) findViewById(R.id.queryBtn);
        mJumpBtn = (Button) findViewById(R.id.jumpToLogIn);
        mClearBtn = (Button) findViewById(R.id.clearBtn);
        mLoadBtn = (Button) findViewById(R.id.loadBtn);
        mShowInfoTxt = (TextView) findViewById(R.id.showInfoBtn);


        mInsertBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);
        mJumpBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
        mLoadBtn.setOnClickListener(this);


        /**
         * 获取数据库操作对象
         */
        mDataBaseHelper = DataBaseHelper.getInstance(this);
        mDB = mDataBaseHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {


        /**
         * 获得db单例对象。 监听按键事件
         */

        switch (v.getId()) {
            case R.id.insertBtn:
                insertDB();
                makeToast("插入数据成功");
                break;
            case R.id.deleteBtn:
                deleteDB();
                makeToast("删除数据成功");
                break;
            case R.id.updateBtn:
                updateDB();
                makeToast("更新数据成功");
                break;
            case R.id.queryBtn:
                mShowInfoTxt.setText("");
                queryCursor();
                break;
            case R.id.jumpToLogIn:
                startActivity(new Intent(DataBaseActivity.this, MainSignActivity.class));
                break;
            case R.id.clearBtn:
                clearInfoFromMDB();
                break;
            case R.id.loadBtn:
                loadDBTable();
            default:
                break;
        }
    }

    /**
     * 增删改查
     */
    public void insertDB() {
        String sql = "insert into user(userName,password) values('a','123')";
        mDB.execSQL(sql);
    }

    public void deleteDB() {
        String sql = "delete from user where userName = 'android'";
        mDB.execSQL(sql);
    }

    public void updateDB() {
        String sql = "update user set userName = 'android' where userName = 'a'";
        mDB.execSQL(sql);
    }

    public void clearInfoFromMDB() {
        String sql = "drop table user";
        mDB.execSQL(sql);
        Toast.makeText(this, "已完成清除用户信息！", Toast.LENGTH_SHORT).show();
    }

    public void loadDBTable() {
        String sql = "create table user(userName varchar(20) not null,password varchar(60) not null)";
        mDB.execSQL(sql);
    }

    /**
     * 获取游标
     *
     * @return
     */
    public Cursor getCursor() {
        return mDB.query("user", null, null, null, null, null, null);
    }

    /**
     * 根据游标查询遍历
     */
    public void queryCursor() {
        Cursor cursor = getCursor();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String info = "用户名：" + userName + "密码：" + password + "\n";
                mShowInfoTxt.append(info);
                cursor.moveToNext();
            }
            cursor.close();   //查询游标后应关闭
        }
    }

    public void makeToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
