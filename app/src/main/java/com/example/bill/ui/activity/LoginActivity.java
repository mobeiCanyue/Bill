package com.example.bill.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bill.MainActivity;
import com.example.bill.R;
import com.example.bill.database.UserDBHelper;
import com.example.bill.entity.UserInfo;
import com.example.bill.util.ToastUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

//    private EditText etUser;
    private TextInputEditText etUser;
    private TextInputEditText etPassword;

    private UserDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);

        //给按钮设置跳转界面
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.local_use).setOnClickListener(this);
        findViewById(R.id.bt_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mDBHelper = UserDBHelper.getInstance(this);//在这里获取数据库实例，防止数据库被关闭
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
        switch (view.getId()) {
            case (R.id.bt_login):
                String username = etUser.getText().toString();//获取用户名
                String password = etPassword.getText().toString();//获取密码

                UserInfo userInfo = new UserInfo();//创建一个用户对象
                userInfo.setUsername(username);
                userInfo.setPassword(password);

                if (mDBHelper.query(userInfo)) {//查询数据库中是否有该用户
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", username);//传递用户名
                    hideSoftKey();
                    mDBHelper.closeLink();
                    startActivity(intent);
                } else {
                    //登录失败
                    ToastUtil.show(LoginActivity.this, "用户名或密码错误");
                }
                break;
            case (R.id.local_use):
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                hideSoftKey();
                break;
            case (R.id.bt_register):
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                hideSoftKey();
                break;
        }
    }

    private void hideSoftKey() {
        InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager.isActive()) {
            methodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}