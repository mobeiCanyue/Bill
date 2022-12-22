package com.example.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.bill.R;
import com.example.bill.entity.BillInfo;

import java.util.List;
import java.util.Locale;

public class BillListAdapter extends BaseAdapter {

    //Adapter是用于连接后端数据和前端显示的适配器接口，
    // 是数据data和 UI（View）之间一个重要的纽带

    //ListView仅作为容器（列表），用于装载 & 显示数据（即 列表项Item）
    //而容器内的具体数据（列表项Item）则是由 适配器（Adapter）提供
    private final Context mContext;
    private final List<BillInfo> mBillList;

    public BillListAdapter(Context context, List<BillInfo> billInfoList) {
        this.mContext = context;
        this.mBillList = billInfoList;
    }

    @Override
    public int getCount() {
        return mBillList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBillList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill, null);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillInfo bill = mBillList.get(position);
        holder.tv_date.setText(bill.getDate());
        holder.tv_remark.setText(bill.getRemark());
        holder.tv_amount.setText(String.format(Locale.CHINA,"%s%d元", bill.getType() == 0 ? "+" : "-", (int) bill.getAmount()));
        return convertView;
    }

    public static final class ViewHolder {
        public TextView tv_date;
        public TextView tv_remark;
        public TextView tv_amount;
    }
}
