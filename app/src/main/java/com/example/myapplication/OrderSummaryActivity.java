package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.myapplication.adapter.OrderSummaryAdapter;
import com.example.myapplication.adapter.ViewOrderAdapter;
import com.example.myapplication.listener.IOrderSummaryLoadListener;
import com.example.myapplication.listener.IViewOrderLoadListener;
import com.example.myapplication.model.OrderSummaryModel;
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

public class OrderSummaryActivity extends AppCompatActivity implements IOrderSummaryLoadListener {

    @BindView(R.id.recycler_order_summary)
    RecyclerView recycler_order_summary;
    @BindView(R.id.orderSummaryLayout)
    RelativeLayout orderSummaryLayout;

    IOrderSummaryLoadListener orderSummaryLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<OrderSummaryModel> orderSummaryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        init();
        loadOrderFromFirebase();
    }

    private void loadOrderFromFirebase() {
        //List<OrderSummaryModel> orderSummaryModels = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Users")
                .child(firebaseAuth.getUid()).child("Items");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot orderSummarySnapshot : snapshot.getChildren()) {
                        OrderSummaryModel orderSummaryModel = orderSummarySnapshot.getValue(OrderSummaryModel.class);
                        orderSummaryModel.setKey(orderSummarySnapshot.getKey());
                        orderSummaryModels.add(orderSummaryModel);
                        //OrderIds.add(orderSummaryModel.getOrderId().toString());
                    }
                    orderSummaryLoadListener.onOrderSummaryLoadSuccess(orderSummaryModels);
                } else {
                    orderSummaryLoadListener.onOrderSummaryLoadFailed("No Food Ordered");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                orderSummaryLoadListener.onOrderSummaryLoadFailed(error.getMessage());
            }
        });
    }

    @Override
    public void onOrderSummaryLoadSuccess(List<OrderSummaryModel> orderSummaryModelList) {
        OrderSummaryAdapter adapter = new OrderSummaryAdapter(this, orderSummaryModelList, orderSummaryLoadListener);
        recycler_order_summary.setAdapter(adapter);
    }

    @Override
    public void onOrderSummaryLoadFailed(String message) {
        Snackbar.make(orderSummaryLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void init() {
        ButterKnife.bind(this);

        orderSummaryLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_order_summary.setLayoutManager(linearLayoutManager);
        recycler_order_summary.addItemDecoration(new SpaceItemDecoration());
    }

}




