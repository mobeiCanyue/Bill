package com.example.bill.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bill.R;
import com.example.bill.database.BillDBHelper;
import com.example.bill.entity.BillInfo;
import com.example.bill.util.DateUtil;
import com.example.bill.util.ToastUtil;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;


public class AddFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_date;
    private Calendar calendar;
    private EditText et_remark;
    private EditText et_amount;
    private RadioGroup rg_type;
    private BillDBHelper mDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("请填写账单");
        tv_title.getPaint().setFakeBoldText(true);

        tv_date = view.findViewById(R.id.tv_date);
        rg_type = view.findViewById(R.id.rg_type);
        et_remark = view.findViewById(R.id.et_remark);
        et_amount = view.findViewById(R.id.et_amount);
        view.findViewById(R.id.btn_save).setOnClickListener(this);

        // 显示当前日期
        calendar = Calendar.getInstance();
        tv_date.setText(DateUtil.getDate(calendar));
        // 点击弹出日期对话框
        tv_date.setOnClickListener(this);

        String user = requireActivity().getIntent().getStringExtra("user");
        if (user != null) {
            mDBHelper = BillDBHelper.getInstance(getContext(), user);
        } else {
            mDBHelper = BillDBHelper.getInstance(getContext());
        }
//        mDBHelper = BillDBHelper.getInstance(getContext(), user);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tv_date):
                MaterialDatePicker.Builder<Long> builder
                        = MaterialDatePicker.Builder.datePicker().setSelection(MaterialDatePicker.todayInUtcMilliseconds());
                MaterialDatePicker<Long> datePicker = builder.build();
                datePicker.show(getChildFragmentManager(), "DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    calendar.setTimeInMillis(selection);
                    tv_date.setText(DateUtil.getDate(calendar));
                });

                break;

            case (R.id.btn_save):
                // 保存订单信息
                BillInfo bill = new BillInfo();
                bill.setDate(tv_date.getText().toString());
                bill.setType(rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
                        BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST);
                bill.setRemark(et_remark.getText().toString());
                bill.setAmount(Double.parseDouble(et_amount.getText().toString()));

//                bill.date = tv_date.getText().toString();
//                bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
//                        BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
//                bill.remark = et_remark.getText().toString();
//                bill.amount = Double.parseDouble(et_amount.getText().toString());
                if (mDBHelper.insert(bill) > 0) {
                    ToastUtil.show(getContext(), "添加账单成功");
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 日历控件选择完毕之后，设置文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(DateUtil.getDate(calendar));
    }

}