package com.example.bill.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bill.R;
import com.example.bill.adapter.MyListAdapter;
import com.example.bill.database.BillDBHelper;
import com.example.bill.entity.BillInfo;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.List;

public class BillFragment extends Fragment {
    //可以把fragment理解为Activity片段
    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("yearMonth", yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // "2012-05"
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
//        ListView lv_bill = view.findViewById(R.id.lv_bill);

        String user = requireActivity().getIntent().getStringExtra("user");

        BillDBHelper mDBHelper;//创建数据库帮助类
        if (user != null) {
            mDBHelper = BillDBHelper.getInstance(getContext(), user);
        } else {
            mDBHelper = BillDBHelper.getInstance(getContext());
        }
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        Bundle arguments = getArguments();
        String yearMonth = arguments.getString("yearMonth");
        List<BillInfo> billInfoList = mDBHelper.queryByMonth(yearMonth);

        RecyclerView rcv = view.findViewById(R.id.rv_bill);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        MyListAdapter adapter2 = new MyListAdapter(billInfoList, mDBHelper);
        rcv.setAdapter(adapter2);
        rcv.addItemDecoration(new MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL));


//        BillListAdapter adapter = new BillListAdapter(getContext(), billInfoList);
//        lv_bill.setAdapter(adapter);

        // 点击列表项，弹出对话框，选择删除或修改
//        lv_bill.setOnItemLongClickListener(
//                (adapterView, view1, which, l) -> {
//                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
//                    builder.setTitle("提示");
//                    builder.setMessage("是否删除该账单？");
//                    builder.setPositiveButton("确定", (dialogInterface, i) -> {
////                        boolean isSuccess;//是否成功
//                        BillInfo billInfo = billInfoList.get(which);//获取点击的账单
//
//                        if (mDBHelper.delete(billInfo.getId()) > 0) {
//                            billInfoList.remove(which);
////                            adapter2.notifyDataSetChanged();
//                            ToastUtil.show(getContext(), "删除成功");
////                            isSuccess = true;
//                        } else {
//                            ToastUtil.show(getContext(), "删除失败");
////                            isSuccess = false;
//                        }
//                    });
//                    builder.setNegativeButton("取消", null);
//                    builder.create().show();
//                    // 返回true避免与点击事件冲突
//                    return true;
//                });
        return view;
    }
}