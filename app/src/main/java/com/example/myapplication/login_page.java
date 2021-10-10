package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login_page extends AppCompatActivity implements View.OnClickListener{
     Button buttonBackLanding, buttonForgetPassword, buttonHomePage;
     EditText editTextTextEmailAddress, editTextTextPassword;

     @Override
     public void onClick(View view) {

     }

    /* @Override
     protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_login);

          editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
          editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
          buttonBackLanding = (Button) findViewById(R.id.buttonBackLanding);
          buttonForgetPassword = (Button) findViewById(R.id.buttonForgetPassword);
          buttonHomePage = (Button) findViewById(R.id.buttonHomePage);

          buttonBackLanding.setOnClickListener(this);
          buttonForgetPassword.setOnClickListener(this);
          buttonHomePage.setOnClickListener(this);
     }

     @Override
     public void onClick(View view) {

          switch(view.getId()){
               case R.id.buttonBackLanding:
                    Intent backLanding = new Intent(this,landingPage.class);
                    startActivity(backLanding);
                    break;
          }

     }*/


}
