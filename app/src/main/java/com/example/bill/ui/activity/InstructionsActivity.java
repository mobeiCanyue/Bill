package com.example.bill.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bill.R;

import java.util.Objects;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_instructions);

        ImageView back = (ImageView) this.findViewById(R.id.iv_back);
        back.setOnClickListener(v -> finish());
    }
}