package com.moses.tuan.sign;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.moses.tuan.FragmentTabHostActivity;
import com.moses.tuan.MainActivity;
import com.moses.tuan.R;
import com.moses.tuan.database.DataBaseHelper;


/**
 * Created by 丹 on 2014/12/12.
 */
public class MainSignActivity extends Activity implements View.OnClickListener {
    private EditText mAccountEditTxt, mPasswordEditTxt;
    private CheckBox mSaveLogInfoBtn;
    Button mSignUpBtn, mLogInBtn;
    //    private LinearLayout mAccountLL, mPasswordLL;
    DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_main);
        intiViews();
    }

    /**
     * 销毁活动时应该关闭数据库
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }

    private void intiViews() {
        mAccountEditTxt = (EditText) findViewById(R.id.enterAccount);
        mPasswordEditTxt = (EditText) findViewById(R.id.enterPassword);
        mSignUpBtn = (Button) findViewById(R.id.signBtnInLogSign);
        mLogInBtn = (Button) findViewById(R.id.logBtnInLogSign);
        mSaveLogInfoBtn = (CheckBox) findViewById(R.id.saveInfoBtn);

//        mAccountLL = (LinearLayout) findViewById(R.id.ll_account_log);
//        mPasswordLL = (LinearLayout) findViewById(R.id.ll_password_log);


        mDataBaseHelper = DataBaseHelper.getInstance(this);
        mDB = mDataBaseHelper.getWritableDatabase();

        //注册监听
        mSignUpBtn.setOnClickListener(this);
        mLogInBtn.setOnClickListener(this);

    }

    public void logIn() {
        String str1 = mAccountEditTxt.getText().toString();
        String str2 = mPasswordEditTxt.getText().toString();
        Cursor cursor = mDB.query("user", null, null, null, null, null, null);

        /**
         * 遍历游标
         */
        if (cursor.moveToFirst()) {
            /**
             * 获取游标内用户名和对应密码的数组
             */
            String[] names = new String[cursor.getCount()];
            String[] passwords = new String[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                names[i] = userName;
                passwords[i] = password;
                cursor.moveToNext();
            }
            /**
             * 根据输入用户名和对应密码的正确性，执行操作
             */
            for (int i = 0; i < cursor.getCount(); i++) {
                if (names[i].equals(str1) && passwords[i].equals(str2)) {
                    Toast.makeText(MainSignActivity.this,"登陆成功，正在进入主界面...",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(MainSignActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainSignActivity.this,"用户名或密码错误,请重新输入...",Toast.LENGTH_SHORT).show();
                }
            }
            /**
             * 查询结束应当关闭游标
             */

        } else {
            Toast.makeText(MainSignActivity.this, "请先注册！", Toast.LENGTH_SHORT).show();
            /**
             * 查询结束应当关闭游标
             */
        }
        cursor.close();
    }


    @Override
    public void onClick(View v) {

        boolean isChecked = mSaveLogInfoBtn.isChecked();
        switch (v.getId()) {
            //注册按钮事件
            case R.id.signBtnInLogSign:
                //点击跳转到注册界面
                startActivity(new Intent(MainSignActivity.this, SignUpActivity.class));
                break;
            //登录按钮事件
            case R.id.logBtnInLogSign:
                /**
                 * 点击登陆
                 */
                logIn();
                break;

            default:
                break;
        }
    }
}
