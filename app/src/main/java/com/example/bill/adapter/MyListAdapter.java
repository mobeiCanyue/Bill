package com.example.bill.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bill.R;
import com.example.bill.database.BillDBHelper;
import com.example.bill.entity.BillInfo;
import com.example.bill.util.ToastUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

/**
 * @author Lenovo
 * Create:  2022-12-13 18:01
 * Describe:
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

    private View itemView;
    private final List<BillInfo> mBillList;
    private final BillDBHelper mDBHelper;

    public MyListAdapter(List<BillInfo> mBillList, BillDBHelper mDBHelper) {
        this.mBillList = mBillList;
        this.mDBHelper = mDBHelper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行
        BillInfo billInfo = mBillList.get(position);
        holder.tv_date.setText(billInfo.getDate());
        holder.tv_remark.setText(billInfo.getRemark());
        holder.tv_amount.setText(String.format(Locale.getDefault(), "%.2f", billInfo.getAmount()));

        holder.itemView.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(itemView.getContext());
            builder.setTitle("提示");
            builder.setMessage("是否删除该账单？");
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                BillInfo billInfo_del = mBillList.get(position);//获取点击的账单

                if (mDBHelper.delete(billInfo_del.getId()) > 0) {
                    mBillList.remove(position);
                    notifyItemRemoved(position);//刷新删除的地方
                    ToastUtil.show(itemView.getContext(), "删除成功");
                } else {
                    ToastUtil.show(itemView.getContext(), "删除失败");
                }
            });
            builder.setNegativeButton("取消", null);
            builder.create().show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        Log.d("MyListAdapter", "getItemCount: " + mBillList.size());
        return mBillList.size();
    }

    @Override
    public long getItemId(int position) {
        return mBillList.get(position).getId();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        TextView tv_remark;
        TextView tv_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_remark = itemView.findViewById(R.id.tv_remark);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }
}

