package com.moses.tuan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 丹 on 2014/12/4.
 */
public class LogInActivity extends Activity {
    EditText accountText, passwordText;
    Button logInButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        accountText = (EditText) findViewById(R.id.accountText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        logInButton = (Button) findViewById(R.id.logInButton);

        /**
         * 获取保存的账户信息
         */
        sharedPreferences = getSharedPreferences("myAccount", MODE_PRIVATE);

        String accountStr = sharedPreferences.getString("savedAccount", "");
        String passwordStr = sharedPreferences.getString("savedPassword", "");
        accountText.setText(accountStr);
        passwordText.setText(passwordStr);

        //点击事件判断输入条件， 符合则跳转页面
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountChar = accountText.getText().toString();
                String passwordChar = passwordText.getText().toString();
                if (accountChar.equals("abc") && passwordChar.equals("123456")) {
                    //符合则 跳转到下一个页面
                    startActivity(new Intent(LogInActivity.this, PagerActivity.class));
                    /**
                     * sharedPreference保存账号信息
                     */
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("savedAccount", accountChar);
                    editor.putString("savedPassword", passwordChar);
                    editor.apply();

                    finish();  //不再需要次活动，所以这里可以finish()掉
                } else Toast.makeText(LogInActivity.this, getResources()
                        .getString(R.string.wrongInfo), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
