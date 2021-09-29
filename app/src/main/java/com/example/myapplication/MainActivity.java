package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView5);
        String text = "inTea Delight";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);

        ss.setSpan(fcsRed, 0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
    }
}