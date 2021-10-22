package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Rice;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, ICartLoadListener {
    private Button account1, home1, orderHistory1, cart1, btnOrder;


    @BindView(R.id.recyclerCart)
    RecyclerView recyclerCart;
    @BindView(R.id.cartLayout)
    RelativeLayout cartLayout;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;

    ICartLoadListener cartLoadListener;

    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    protected void onStop(){
        if(EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent.class));
        EventBus.getDefault().removeStickyEvent(UpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(UpdateCartEvent event)
    {
        loadCartFromFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        init();
        loadCartFromFirebase();

    }

    private void loadCartFromFirebase() {
        List<Cart> cartModels = new ArrayList<>();

        loadRicefromFirebase(cartModels);
        loadNoodlefromFirebase(cartModels);
        loadDessertfromFirebase(cartModels);
        loadDrinkfromFirebase(cartModels);
    }

    private void init(){
        ButterKnife.bind(this);

        cartLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account1:
                Intent toLogin = new Intent(this, Account.class);
                startActivity(toLogin);
                break;
            case R.id.home1:
            case R.id.btnOrder:
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
        }
    }

    @Override
    public void onCartLoadSuccess(List<Cart> cartModelList) {
        double sum = 0;
        for(Cart cartmodel: cartModelList)
        {
            sum+=cartmodel.getTotalPrice();
        }
        txtTotalPrice.setText(new StringBuilder("RM ").append(sum));
        CartAdapter adapter = new CartAdapter(this, cartModelList);
        recyclerCart.setAdapter(adapter);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(cartLayout,message,Snackbar.LENGTH_LONG).show();
    }

    private void loadRicefromFirebase(List<Cart> cartModels){
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart")
                .child("UNIQUE_USER_ID").child("Rice")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot cartSnapshot:snapshot.getChildren())
                            {
                                Cart cartModel = cartSnapshot.getValue(Cart.class);
                                cartModel.setKey(cartSnapshot.getKey());
                                cartModels.add(cartModel);
                            }
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }
                        else
                        {
                            cartLoadListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }
    private void loadNoodlefromFirebase(List<Cart> cartModels){
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart")
                .child("UNIQUE_USER_ID").child("Noodle")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot cartSnapshot:snapshot.getChildren())
                            {
                                Cart cartModel = cartSnapshot.getValue(Cart.class);
                                cartModel.setKey(cartSnapshot.getKey());
                                cartModels.add(cartModel);
                            }
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }
                        else
                        {
                            cartLoadListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }
    private void loadDessertfromFirebase(List<Cart> cartModels){
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart")
                .child("UNIQUE_USER_ID").child("Dessert")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot cartSnapshot:snapshot.getChildren())
                            {
                                Cart cartModel = cartSnapshot.getValue(Cart.class);
                                cartModel.setKey(cartSnapshot.getKey());
                                cartModels.add(cartModel);
                            }
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }
                        else
                        {
                            cartLoadListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }
    private void loadDrinkfromFirebase(List<Cart> cartModels){
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart")
                .child("UNIQUE_USER_ID").child("Drink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot cartSnapshot:snapshot.getChildren())
                            {
                                Cart cartModel = cartSnapshot.getValue(Cart.class);
                                cartModel.setKey(cartSnapshot.getKey());
                                cartModels.add(cartModel);
                            }
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }
                        else
                        {
                            cartLoadListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }
}


