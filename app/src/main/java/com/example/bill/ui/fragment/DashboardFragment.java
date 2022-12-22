package com.example.bill.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bill.R;
import com.example.bill.adapter.BillPagerAdapter;
import com.example.bill.database.BillDBHelper;
import com.example.bill.util.DateUtil;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class DashboardFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_month;
    private Calendar calendar;
    private ViewPager2 viewPager_bill;
    private View view;
    private BillDBHelper mDBHelper;
    private BillPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate方法的主要作用就是将xml转换成一个View对象，用于动态的创建布局
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("账单列表");
        tv_title.getPaint().setFakeBoldText(true);

        tv_month = view.findViewById(R.id.tv_month);
        // 显示年月
        calendar = Calendar.getInstance();
        tv_month.setText(DateUtil.getYearMonth(calendar));
        // 设置点击事件
        tv_month.setOnClickListener(this);

        String user = requireActivity().getIntent().getStringExtra("user");
        Log.d("DashboardFragment", "user: " + user);
        if (user != null) {
            mDBHelper = BillDBHelper.getInstance(getContext(), user);
        } else {
            mDBHelper = BillDBHelper.getInstance(getContext());
        }
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        // 初始化翻页视图
        initViewPager();
        return view;
    }

    //Viewpager使用起来就是我们通过创建adapter给它填充多个view，
    // 左右滑动时，切换不同的view。Google官方是建议我们使用Fragment来填充ViewPager的，
    // 这样 可以更加方便的生成每个Page，以及管理每个Page的生命周期。
    // 初始化翻页视图
    private void initViewPager() {
        // 从布局视图中获取名叫pts_bill的翻页标签栏
//        PagerTabStrip pts_bill = view.findViewById(R.id.pts_bill);
        // 设置翻页标签栏的文本大小
//        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        viewPager_bill = view.findViewById(R.id.vp_bill);

        adapter = new BillPagerAdapter(getChildFragmentManager(),
                getLifecycle(), calendar.get(Calendar.YEAR));//新建适配器
        viewPager_bill.setAdapter(adapter);//设置适配器
        new TabLayoutMediator(tabLayout, viewPager_bill,
                (tab, position) -> tab.setText((position + 1) + "月份")).attach();//将tablayout与viewpager2绑定

        // 设置翻页监听器
        viewPager_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tv_month):
                MaterialDatePicker<Long> datePicker
                        = MaterialDatePicker.Builder.datePicker()
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                datePicker.show(getChildFragmentManager(), "DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    calendar.setTimeInMillis(selection);
                    tv_month.setText(DateUtil.getYearMonth(calendar));
                    adapter.setYear(calendar.get(Calendar.YEAR));
                    viewPager_bill.setCurrentItem(calendar.get(Calendar.MONTH));
                });
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置给文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_month.setText(DateUtil.getYearMonth(calendar));

        // 设置翻页视图显示第几页，上面的月份跟着下面翻页的一起变化
        viewPager_bill.setCurrentItem(month);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }
}