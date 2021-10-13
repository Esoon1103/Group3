package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Dessert;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Noodle;
import com.example.myapplication.model.Rice;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.xml.sax.DTDHandler;

import java.sql.SQLOutput;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    private Button account1,home1,orderHistory1,cart1;
    private ImageButton rice_btn_homepage,
            noodle_btn_homepage, drink_btn_homepage, dessert_btn_homepage, temp_btn_homepage;

    Rice riceCondition = new Rice(false);
    Noodle noodleCondition = new Noodle(false);
    Dessert dessertCondition = new Dessert(false);
    Drinks drinkCondition = new Drinks(false);

    //local variable for slide show
    SliderView sliderview1;
    int[] image={R.drawable.rice,R.drawable.dessert,R.drawable.drink};
    SliderAdp sliderAdp;


    @Override
    public void onClick(View view) {

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

            case R.id.rice_btn_homepage:
                Intent riceBtn = new Intent(this, rice_page.class);
                startActivity(riceBtn);
                onClickRice();
                break;

            case R.id.noodle_btn_homepage:
                Intent noodleBtn = new Intent(this, rice_page.class);
                startActivity(noodleBtn);
                onClickNoodle();
                break;

            case R.id.dessert_btn_homepage:
                Intent dessertBtn = new Intent(this, rice_page.class);
                startActivity(dessertBtn);
                onClickDessert();
                break;

            case R.id.drink_btn_homepage:
                Intent drinkBtn = new Intent(this, rice_page.class);
                startActivity(drinkBtn);
                onClickDrink();
                break;

            case R.id.temp_btn_homepage:
                Intent reservation_btn = new Intent(this, Reservation_home.class);
                startActivity(reservation_btn);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        rice_btn_homepage = findViewById(R.id.rice_btn_homepage);
        rice_btn_homepage.setOnClickListener(this);

        noodle_btn_homepage = findViewById(R.id.noodle_btn_homepage);
        noodle_btn_homepage.setOnClickListener(this);

        dessert_btn_homepage = findViewById(R.id.dessert_btn_homepage);
        dessert_btn_homepage.setOnClickListener(this);

        drink_btn_homepage = findViewById(R.id.drink_btn_homepage);
        drink_btn_homepage.setOnClickListener(this);

        temp_btn_homepage = findViewById(R.id.temp_btn_homepage);
        temp_btn_homepage.setOnClickListener(this);

//assign variable
       sliderview1=findViewById(R.id.slideshow_homepage);

       //initialize adapter
        sliderAdp=new SliderAdp(image);

        //set adapter
        sliderview1.setSliderAdapter(sliderAdp);
        //set indicator aniamtion
        sliderview1.setIndicatorAnimation(IndicatorAnimationType.WORM);
        //set transformation animation
        sliderview1.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        //autocycle
        sliderview1.startAutoCycle();

    }

    //Determine whether which button is clicked
    //to display the page by using the boolean condition
    //True = display the page, False = Not the page user clicked
    public void onClickRice(){
        if(riceCondition == null)
            System.out.println("Null pointer exception");
        else
            riceCondition.setRice(true);
    }
    public void onClickNoodle(){
        if(noodleCondition == null)
            System.out.println("NULL POINTER EXCEPTION");
        else
            noodleCondition.setNoodle(true);
    }
    public void onClickDrink(){
        drinkCondition.setDrinks(true);
    }
    public void onClickDessert(){
        dessertCondition.setDessert(true);
    }

}
