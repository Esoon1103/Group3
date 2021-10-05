package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;
//implements View.OnClickListener
public class landingPage extends AppCompatActivity {
    private Button buttonLoginLanding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        //buttonLoginLanding = findViewById(R.id.buttonLoginLanding);
       // buttonLoginLanding.setOnClickListener(this);

    }


   /* @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonLoginLanding:
                Intent toLogin = new Intent(this, HomeFragment.class);
                startActivity(toLogin);
                break;
        }
    }*/
}