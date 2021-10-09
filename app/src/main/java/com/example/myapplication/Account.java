package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity implements View.OnClickListener{
    private Button account1,home1, orderHistory1,cart1;

    @Override
    public void onClick(View view) {
        //link to account page
        switch(view.getId()){
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
        setContentView(R.layout.account_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

    }

    public void onPaymentMethod(View view){
        //Link to Payment Method Changing
        Intent toPayment = new Intent(this, paymentMethod.class);
        startActivity(toPayment);
    }


    public void onChangePass(View view){
        //Link to Password Changing
        Intent toChangePass = new Intent(this, changePassword.class);
        startActivity(toChangePass);
    }

    public void onLogout(View view){
        //Link to Log Out

    }

} //End Class




