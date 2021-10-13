package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class changePassword extends AppCompatActivity implements View.OnClickListener{

    Button forgotPass;

    @Override
    public void onClick(View view) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        forgotPass = findViewById(R.id.btnForgotPass);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePassword.this, ForgotPasswordActivity.class));
            }
        });
    }
}
