package com.example.bill.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bill.ui.fragment.BillFragment;

//翻页适配器
public class BillPagerAdapter extends FragmentStateAdapter {
    private int mYear;

    public BillPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int mYear) {
        super(fragmentManager, lifecycle);
        this.mYear = mYear;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int month = position + 1;
        // 9 -> 09 10 -> 10
        String zeroMonth = month < 10 ? "0" + month : String.valueOf(month);
        String yearMonth = mYear + "-" + zeroMonth;
        // 2035-09
        Log.d("BillPagerAdapter", "createFragment: " + yearMonth);
        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getItemCount() {
        return 12;
    }

//    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
//        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        this.mYear = year;
//    }


//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        int month = position + 1;
//        // 9 -> 09 10 -> 10
//        String zeroMonth = month < 10 ? "0" + month : String.valueOf(month);
//        String yearMonth = mYear + "-" + zeroMonth;
//        // 2035-09
//        Log.d("ning", yearMonth);
//        return BillFragment.newInstance(yearMonth);
//    }

//    @Override
//    public int getCount() {
//        return 12;
//    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return (position + 1) + "月份";
//    }
}
