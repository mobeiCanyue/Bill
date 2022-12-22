package com.example.bill.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bill.R;
import com.example.bill.ui.activity.InstructionsActivity;
import com.example.bill.util.ToastUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class UserFragment extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate方法的主要作用就是将xml转换成一个View对象，用于动态的创建布局
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        String user = requireActivity().getIntent().getStringExtra("user");
        TextView user_tv = view.findViewById(R.id.tv_mine_user);
        user_tv.setText("尊敬的用户 " + user + " 您好");

        view.findViewById(R.id.rl_mine_exit).setOnClickListener(this);
        view.findViewById(R.id.rl_mine_update).setOnClickListener(this);
        view.findViewById(R.id.rl_mine_use).setOnClickListener(this);
        view.findViewById(R.id.rl_mine_call).setOnClickListener(this);
        view.findViewById(R.id.rl_mine_about).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.rl_mine_about):{
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setTitle("开发者信息");
                builder.setMessage("联系方式：" +
                        "\nmobeicanyue@gmail.com");
                builder.setPositiveButton("确定", null);
                builder.show();
                break;
            }

            case (R.id.rl_mine_use): {
                Intent intent = new Intent(getContext(), InstructionsActivity.class);
                startActivity(intent);
                break;
            }
            case (R.id.rl_mine_call): {
                Intent tell = new Intent(Intent.ACTION_DIAL);
                tell.setData(Uri.parse("tel:10086"));
                startActivity(tell);
                break;
            }
            case (R.id.rl_mine_update): {
                ToastUtil.show(getContext(), "当前已是最新版本");
                break;
            }
            case (R.id.rl_mine_exit): {
                //弹出对话框，提示用户是否退出
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setTitle("提示");
                builder.setMessage("是否退出登录？");

                builder.setPositiveButton("确定", (dialog, which) -> requireActivity().finish());
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            }
        }
    }
}