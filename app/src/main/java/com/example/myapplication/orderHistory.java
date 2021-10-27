package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class orderHistory extends AppCompatActivity implements View.OnClickListener {
    private Button account1, home1, orderHistory1, cart1, btnCompletedOrder, feedback_button;

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
                Intent toLogin3 = new Intent(this, CartActivity.class);
                startActivity(toLogin3);
                break;

            case R.id.btnCompletedOrder:
                Intent toLogin4 = new Intent(this, ViewOrderActivity.class);
                startActivity(toLogin4);
                break;

            case R.id.feedback_button:
                Intent i = new Intent(this, FeedbackActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        btnCompletedOrder = findViewById(R.id.btnCompletedOrder);
        btnCompletedOrder.setOnClickListener(this);

        feedback_button = findViewById(R.id.feedback_button);
        feedback_button.setOnClickListener(this);
    }

}
