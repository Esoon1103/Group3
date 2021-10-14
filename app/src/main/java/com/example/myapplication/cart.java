package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Rice;

import java.util.List;

public class cart extends AppCompatActivity implements View.OnClickListener {
    private Button account1, home1, orderHistory1, cart1;
    private List<Rice> riceList;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account1:
                Intent toLogin = new Intent(this, Account.class);
                startActivity(toLogin);
                break;
            case R.id.home1:
                Intent toLogin1 = new Intent(this, HomePage.class);
                startActivity(toLogin1);
                break;
            case R.id.orderHistory1:
                Intent toLogin2 = new Intent(this, orderHistory.class);
                startActivity(toLogin2);
                break;
            case R.id.cart1:
                Intent toLogin3 = new Intent(this, cart.class);
                startActivity(toLogin3);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

    }
}


