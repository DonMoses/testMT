package com.moses.tuan.sign;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moses.tuan.FragmentTabHostActivity;
import com.moses.tuan.MainActivity;
import com.moses.tuan.R;
import com.moses.tuan.database.DataBaseHelper;

/**
 * Created by 丹 on 2014/12/12.
 */
public class SignUpActivity extends Activity {
    DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;
    private EditText mAccountTxt, mPasswordTxt, mEnsurePasswordTxt;
    Button mSignBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        intiViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }

    private void intiViews() {
        mDataBaseHelper = DataBaseHelper.getInstance(this);
        mDB = mDataBaseHelper.getWritableDatabase();

        mAccountTxt = (EditText) findViewById(R.id.isAccount);
        mPasswordTxt = (EditText) findViewById(R.id.isPassword);
        mEnsurePasswordTxt = (EditText) findViewById(R.id.ensurePassword);
        mSignBtn = (Button) findViewById(R.id.signBtnInSign);
        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignInfoToDatabase();
            }
        });


    }

    public void saveSignInfoToDatabase() {
        String str1 = mAccountTxt.getText().toString();
        String str2 = mPasswordTxt.getText().toString();
        String str3 = mEnsurePasswordTxt.getText().toString();
        Cursor cursor = mDB.query("user", null, null, null, null, null, null);
        /**
         * 遍历游标
         */
        if (cursor.moveToFirst()) {
            /**
             * 获取游标内用户名的数组
             */
            String[] names = new String[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                names[i] = userName;
                cursor.moveToNext();
            }
            /**
             * 根据输入用户名是否存在，执行操作
             */
            for (int i = 0; i < cursor.getCount(); i++) {
                if (names[i].equals(str1)) {
                    Toast.makeText(this, "用户名已存在，请重新输入...", Toast.LENGTH_SHORT).show();
                } else if (str1 != null && str2 != null && str3.equals(str2)) {
                    //android方式操作数据库
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userName", str1);
                    contentValues.put("password", str2);
                    mDB.insert("user", null, contentValues);

                    Toast.makeText(this, "注册成功！ 正在为您跳转到主界面...", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(SignUpActivity.this, FragmentTabHostActivity.class));
                    finish();

                } else {
                    Toast.makeText(this, "输入有误，请重新输入...", Toast.LENGTH_SHORT).show();
                }
            }
            /**
             * 如果数据库没有数据，则直接创建账户。提示成功，2秒后跳转
             */
        } else if (str1 != null && str2 != null && str3.equals(str2)) {
            mDB.execSQL("insert into user(userName,password) " + "values(" + str1 + "," + str2 + ");");
            Toast.makeText(this, "注册成功！ 正在为您跳转到主界面...", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "注册成功！ 正在为您跳转到主界面...", Toast.LENGTH_SHORT).show();
        }
        cursor.close();   //查询游标后应关闭
    }
}
