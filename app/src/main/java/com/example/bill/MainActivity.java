package com.example.bill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bill.ui.fragment.AddFragment;
import com.example.bill.ui.fragment.BillFragment;
import com.example.bill.ui.fragment.DashboardFragment;
import com.example.bill.ui.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        // 获取页面上的底部导航栏控件
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // 配置navigation与底部菜单之间的联系
        // 底部菜单的样式里面的item里面的ID与navigation布局里面指定的ID必须相同，否则会出现绑定失败的情况
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_user, R.id.navigation_add, R.id.navigation_dashboard)
                .build();
        // 建立fragment容器的控制器，这个容器就是页面的上的fragment容器
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // 获取fragment容器的控制器
        NavController navController =
                ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();

        // 启动
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        String user = getIntent().getStringExtra("user");//获取传递的值

        UserFragment userFragment = new UserFragment();//创建fragment对象
        DashboardFragment dashboardFragment = new DashboardFragment();
        AddFragment addFragment = new AddFragment();
        BillFragment billFragment = new BillFragment();

        Bundle bundle = new Bundle();
        bundle.putString("user", user);//传递值

        userFragment.setArguments(bundle);//给fragment设置参数
        dashboardFragment.setArguments(bundle);
        addFragment.setArguments(bundle);
        billFragment.setArguments(bundle);
    }
}