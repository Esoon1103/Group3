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
import android.widget.Toast;

import com.example.myapplication.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//implements View.OnClickListener
public class landingPage extends AppCompatActivity implements View.OnClickListener{
    private Button buttonLoginLanding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        buttonLoginLanding = findViewById(R.id.buttonLoginLanding);
        buttonLoginLanding.setOnClickListener(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            //If there is user logged in, go to homepage
            startActivity(new Intent(this, HomePage.class));
        } else {
            //If not, bring user to login page
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

   @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonLoginLanding:
                Intent toLogin = new Intent(this, LoginActivity.class);
                startActivity(toLogin);
                break;
        }

    }
}
