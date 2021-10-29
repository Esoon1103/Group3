package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.adapter.ViewOrderAdapter;
import com.example.myapplication.listener.IViewOrderLoadListener;
import com.example.myapplication.listener.RecyclerViewClickInterface;
import com.example.myapplication.model.ViewOrderModel;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewOrderActivity extends AppCompatActivity implements IViewOrderLoadListener, RecyclerViewClickInterface, View.OnClickListener {

    private Button account1,home1, orderHistory1,cart1;

    @BindView(R.id.recycler_view_order)
    RecyclerView recycler_view_order;
    @BindView(R.id.viewOrderLayout)
    RelativeLayout viewOrderLayout;

    IViewOrderLoadListener viewOrderLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<ViewOrderModel> viewOrderModels = new ArrayList<>();

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
                Intent toLogin3 = new Intent(this, CartActivity.class);
                startActivity(toLogin3);
                break;

            case R.id.btnChangePass:
                Intent changePass = new Intent(this, changePassword.class);
                startActivity(changePass);
                break;
        } //switch
    } //onClick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        init();
        loadOrderFromFirebase();
    }

    private void loadOrderFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Users")
                .child(firebaseAuth.getUid()).child("Order");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            for(DataSnapshot viewOrderSnapshot : snapshot.getChildren()){
                                ViewOrderModel viewOrderModel = viewOrderSnapshot.getValue(ViewOrderModel.class);
                                viewOrderModel.setKey(viewOrderSnapshot.getKey());
                                viewOrderModels.add(viewOrderModel);

                            }
                            viewOrderLoadListener.onViewOrderLoadSuccess(viewOrderModels);
                        } else {
                            Toast.makeText(ViewOrderActivity.this,
                                    "No Order Found.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        viewOrderLoadListener.onViewOrderLoadFailed(error.getMessage());
                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        viewOrderLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_order.setLayoutManager(linearLayoutManager);
        recycler_view_order.addItemDecoration(new SpaceItemDecoration());

    }

    @Override
    public void onViewOrderLoadSuccess(List<ViewOrderModel> viewOrderModelList) {
        ViewOrderAdapter adapter = new ViewOrderAdapter(this,viewOrderModelList, viewOrderLoadListener, this);
        recycler_view_order.setAdapter(adapter);
    }

    @Override
    public void onViewOrderLoadFailed(String message) {
        Snackbar.make(viewOrderLayout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(int position) {
        //Toast.makeText(this,"test", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ViewOrderActivity.this, ViewOrderActivity.class);
        startActivity(i);
    }

}