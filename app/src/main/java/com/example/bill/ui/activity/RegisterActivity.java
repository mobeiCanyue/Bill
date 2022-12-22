package com.example.bill.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bill.R;
import com.example.bill.database.UserDBHelper;
import com.example.bill.entity.UserInfo;
import com.example.bill.util.ToastUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText et_name;
    private TextInputEditText et_password;
    private TextInputEditText et_password_again;
    private UserDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setContentView(R.layout.activity_register);

        et_name = findViewById(R.id.et_name_register);
        et_password = findViewById(R.id.et_password_register);
        et_password_again = findViewById(R.id.et_password_again);

        findViewById(R.id.bt_register).setOnClickListener(this);
        findViewById(R.id.bt_cancel_register).setOnClickListener(this);

        mDBHelper = UserDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_register):
                register();
                break;
            case (R.id.bt_cancel_register):
//                mDBHelper.closeLink();
                finish();
                break;
        }
    }

    void register() {
        //用户进行注册
        String username = et_name.getText().toString();
        String password = et_password.getText().toString();
        String password_again = et_password_again.getText().toString();

        if (check(username, password, password_again)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            if (mDBHelper.insert(userInfo) > 0) {
                ToastUtil.show(this, "注册成功");
//                mDBHelper.closeLink();
                finish();
            } else {
                ToastUtil.show(this, "注册失败");
            }
        }
    }

    boolean check(String username, String password, String password_again) {
        if (username.isEmpty()) {
            ToastUtil.show(this, "用户名不能为空");
            return false;
        }
        if (password.isEmpty()) {
            ToastUtil.show(this, "密码不能为空");
            return false;
        }
        if (password_again.isEmpty()) {
            ToastUtil.show(this, "请再次输入密码");
            return false;
        }
        if (!password.equals(password_again)) {
            ToastUtil.show(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }
}