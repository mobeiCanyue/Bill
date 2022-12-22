package com.example.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bill.ui.activity.LoginActivity;

import java.util.Objects;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐层状态栏和标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏
        Objects.requireNonNull(getSupportActionBar()).hide();

        //加载启动图片
        setContentView(R.layout.activity_launch);
        //后台处理耗时任务
        new Thread(() -> {
            //耗时任务，比如加载网络数据
            runOnUiThread(() -> {
                //跳转至 BillPagerActivity
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                //结束当前的 Activity
                LaunchActivity.this.finish();
            });
        }).start();
    }
}