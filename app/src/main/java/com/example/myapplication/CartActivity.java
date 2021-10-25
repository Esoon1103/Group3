package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.text.format.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, ICartLoadListener {
    private Button account1, home1, orderHistory1, cart1, btnOrder;
    private FirebaseAuth firebaseAuth;
    Calendar cal = Calendar.getInstance();

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

        loadFoodFromFirebase(cartModels);
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
        refreshPage();
        Toast.makeText(CartActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadFoodFromFirebase(List<Cart> cartModels){

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(firebaseAuth.getUid()).child("Cart")
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
                            //Validate Place Order
                            validatePlaceOrder(cartModels);
                        }
                        else
                        {
                            cartLoadListener.onCartLoadFailed("Cart Empty");
                            validatePlaceOrder(cartModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    public void validatePlaceOrder(List<Cart> cartModelList){
        if(cartModelList.size() == 0) {
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(CartActivity.this, "No item in Cart!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(cartModelList.size() > 0){
            PlaceOrder(cartModelList); //Bring user to HOME PAGE
        }
    }

    private void PlaceOrder(List<Cart> cartModelList){
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click "Place Order"
                addOrderFirebaseData(cartModelList); // Copy cart data to Order data
                Intent backHome = new Intent(view.getContext(), HomePage.class);
                startActivity(backHome); //Navigate to HOME PAGE
                alertDialog(); //Alert successful order
                deleteCartFirebaseData(); // Order will be set in the Firebase
                refreshPage();
            }
        });
    }

    //Get Cart data and store  into a New Path called "Order"
    private void addOrderFirebaseData(List<Cart> cartModels) {

        String timestamp = ""+System.currentTimeMillis();

        String date = "" + cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR);
        String time = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        String feedback = "blank";


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase cart = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
                cart.getReference("Users")
                .child(firebaseAuth.getUid())
                .child("Cart")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        FirebaseDatabase order = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");

                                //Write Order Items to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Items").child(timestamp)
                                .setValue(dataSnapshot.getValue());

                                //Write orderID to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Order").child(timestamp).child("orderId")
                                .setValue(timestamp);

                                //Write date to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Order").child(timestamp).child("date")
                                .setValue(date);

                                //Write time to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Order").child(timestamp).child("time")
                                .setValue(time);

                                /*
                                //Write feedback to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Order").child(timestamp).child("feedback")
                                .setValue(feedback);


                                //Write orderID to database
                                order.getReference("Users")
                                .child(firebaseAuth.getUid())
                                .child("Order").child("Feedback").child(timestamp)
                                .setValue(timestamp);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CartActivity.this, "Failed to copy data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Delete cart after Place Order
    private void deleteCartFirebaseData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        firebase.getReference("Users")
                .child(firebaseAuth.getUid())
                .child("Cart")
                .setValue(null);

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void alertDialog(){
        AlertDialog alert = new AlertDialog.Builder(CartActivity.this)
                .setTitle("Order Status")
                .setMessage("Order Placed Successfully")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        dialog.dismiss();
                    }
                })
                .create();
        alert.show();
    }

    private void refreshPage(){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

    }
}


