package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.DessertAdapter;
import com.example.myapplication.adapter.DrinkAdapter;
import com.example.myapplication.adapter.NoodleAdapter;
import com.example.myapplication.adapter.RiceAdapter;
import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.listener.IRiceLoadListener;
import com.example.myapplication.model.Dessert;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Noodle;
import com.example.myapplication.model.Rice;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class rice_page extends AppCompatActivity implements IRiceLoadListener, View.OnClickListener{
    private Button account1,home1,orderHistory1;

    @BindView(R.id.riceListRecycler)
    RecyclerView riceListRecycler;
    @BindView(R.id.rice_layout)
    RelativeLayout rice_layout;
    @BindView(R.id.cart1)
    Button cart1;
    @BindView(R.id.cartBadge)
    NotificationBadge cartBadge;

    IRiceLoadListener riceLoadListener;
    IRiceLoadListener cartLoadListener;

    conditionRice riceTest = new conditionRice();
    conditionNoodle noodleTest = new conditionNoodle();
    conditionDessert dessertTest = new conditionDessert();
    conditionDrink drinkTest = new conditionDrink();

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
        loadFoodFromFirebase();

    }

    private void loadFoodFromFirebase() {
        List<Rice> riceModels = new ArrayList<>();
        List<Noodle> noodleModels = new ArrayList<>();
        List<Dessert> dessertModels = new ArrayList<>();
        List<Drinks> drinkModels = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference;

        if(riceTest.isRice()) {
            reference = database.getReference("Rice");
            referenceRiceAdd(reference, riceModels);
            System.out.println("Rice is TRUE");
        }
        else if (noodleTest.isNoodle()) {
            reference = database.getReference("Noodle");
            referenceNoodleAdd(reference, noodleModels);
            System.out.println("Noodle is TRUE");
        }
        else if (dessertTest.isDessert()) {
            reference = database.getReference("Dessert");
            referenceDessertAdd(reference, dessertModels);
            System.out.println("Dessert is TRUE");
        }
        else if(drinkTest.isDrinks()){
            reference = database.getReference("Drinks");
            referenceDrinkAdd(reference, drinkModels);
            System.out.println("Drink is TRUE");
        }

    }

    private void init(){
        ButterKnife.bind(this);

        riceLoadListener = this;
        cartLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        riceListRecycler.setLayoutManager(linearLayoutManager);
        riceListRecycler.addItemDecoration(new SpaceItemDecoration());


    }

    @Override
    public void onRiceLoadSuccess(List<Rice> riceModelList) {
        RiceAdapter adapter = new RiceAdapter(this, riceModelList);
        riceListRecycler.setAdapter(adapter);
    }

    @Override
    public void onRiceLoadFailed(String message) {
        Snackbar.make(rice_layout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onNoodleLoadSuccess(List<Noodle> noodleModelList) {
        NoodleAdapter adapter = new NoodleAdapter(this, noodleModelList);
        riceListRecycler.setAdapter(adapter);
    }

    @Override
    public void onNoodleLoadFailed(String message) {
        Snackbar.make(rice_layout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDrinkLoadSuccess(List<Drinks> drinksModelList) {
        DrinkAdapter adapter = new DrinkAdapter(this, drinksModelList);
        riceListRecycler.setAdapter(adapter);
    }

    @Override
    public void onDrinkLoadFailed(String message) {
        Snackbar.make(rice_layout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDessertLoadSuccess(List<Dessert> dessertModelList) {
        DessertAdapter adapter= new DessertAdapter(this, dessertModelList);
        riceListRecycler.setAdapter(adapter);
    }

    @Override
    public void onDessertLoadFailed(String message) {
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

    public void referenceRiceAdd(DatabaseReference reference, List<Rice>riceModels){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot riceSnapshot:snapshot.getChildren())
                    {
                        Rice riceModel = riceSnapshot.getValue(Rice.class);
                        riceModel.setKey(riceSnapshot.getKey());
                        riceModels.add(riceModel);
                    }
                    riceLoadListener.onRiceLoadSuccess(riceModels);
                }
                else
                    riceLoadListener.onRiceLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                riceLoadListener.onRiceLoadFailed(error.getMessage());
            }
        });
    }
    public void referenceNoodleAdd(DatabaseReference reference, List<Noodle>noodleModels){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot noodleSnapshot:snapshot.getChildren())
                    {
                        Noodle noodleModel = noodleSnapshot.getValue(Noodle.class);
                        noodleModel.setKey(noodleSnapshot.getKey());
                        noodleModels.add(noodleModel);
                    }
                    riceLoadListener.onNoodleLoadSuccess(noodleModels);
                }
                else
                    riceLoadListener.onNoodleLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                riceLoadListener.onNoodleLoadFailed(error.getMessage());
            }
        });
    }
    public void referenceDessertAdd(DatabaseReference reference, List<Dessert>dessertModels){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot noodleSnapshot:snapshot.getChildren())
                    {
                        Dessert dessertModel = noodleSnapshot.getValue(Dessert.class);
                        dessertModel.setKey(noodleSnapshot.getKey());
                        dessertModels.add(dessertModel);
                    }
                    riceLoadListener.onDessertLoadSuccess(dessertModels);
                }
                else
                    riceLoadListener.onRiceLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                riceLoadListener.onRiceLoadFailed(error.getMessage());
            }
        });
    }
    public void referenceDrinkAdd(DatabaseReference reference, List<Drinks>drinkModels){
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
                    riceLoadListener.onDrinkLoadSuccess(drinkModels);
                }
                else
                    riceLoadListener.onRiceLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                riceLoadListener.onRiceLoadFailed(error.getMessage());
            }
        });
    }


}

class conditionRice extends Rice{
    boolean bool;
}
class conditionNoodle extends Noodle{

}
class conditionDessert extends Dessert{

}
class conditionDrink extends  Drinks{

}