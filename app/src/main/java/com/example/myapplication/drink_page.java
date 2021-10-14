package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.DessertAdapter;
import com.example.myapplication.adapter.DrinkAdapter;
import com.example.myapplication.adapter.NoodleAdapter;
import com.example.myapplication.listener.IDessertLoadListener;
import com.example.myapplication.listener.IDrinkLoadListener;
import com.example.myapplication.listener.INoodleLoadListener;
import com.example.myapplication.model.Dessert;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Noodle;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class drink_page extends AppCompatActivity implements IDrinkLoadListener, View.OnClickListener{
    private Button account1,home1,orderHistory1;

    @BindView(R.id.riceListRecycler)
    RecyclerView riceListRecycler;
    @BindView(R.id.rice_layout)
    RelativeLayout rice_layout;
    @BindView(R.id.cart1)
    Button cart1;

    IDrinkLoadListener drinkLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rice_main_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        init();
        loadDrinkFromFirebase();

    }

    private void loadDrinkFromFirebase() {
        List<Drinks> drinkModels = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("Drinks");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot drinkSnapshot:snapshot.getChildren())
                    {
                        Drinks drinkModel = drinkSnapshot.getValue(Drinks.class);
                        drinkModel.setKey(drinkSnapshot.getKey());
                        drinkModels.add(drinkModel);
                    }
                    drinkLoadListener.onDrinkLoadSuccess(drinkModels);
                }
                else
                    drinkLoadListener.onDrinkLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                drinkLoadListener.onDrinkLoadFailed(error.getMessage());
            }
        });
    }

    private void init(){
        ButterKnife.bind(this);

        drinkLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        riceListRecycler.setLayoutManager(linearLayoutManager);
        riceListRecycler.addItemDecoration(new SpaceItemDecoration());
    }


    @Override
    public void onDrinkLoadSuccess(List<Drinks> drinkModelList) {
        DrinkAdapter adapter = new DrinkAdapter(this, drinkModelList);
        riceListRecycler.setAdapter(adapter);
    }

    @Override
    public void onDrinkLoadFailed(String message) {
        Snackbar.make(rice_layout, message, Snackbar.LENGTH_LONG).show();
    }

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

        }
    }
}
