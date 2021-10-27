package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

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

public class ViewOrderActivity extends AppCompatActivity implements IViewOrderLoadListener, RecyclerViewClickInterface {

    @BindView(R.id.recycler_view_order)
    RecyclerView recycler_view_order;
    @BindView(R.id.viewOrderLayout)
    RelativeLayout viewOrderLayout;

    IViewOrderLoadListener viewOrderLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<ViewOrderModel> viewOrderModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

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
                       //String orderId = snapshot.child("orderId").getValue().toString();
                        //System.out.println(orderId);
                        if(snapshot.exists()){
                            for(DataSnapshot viewOrderSnapshot : snapshot.getChildren()){
                                ViewOrderModel viewOrderModel = viewOrderSnapshot.getValue(ViewOrderModel.class);
                                viewOrderModel.setKey(viewOrderSnapshot.getKey());
                                viewOrderModels.add(viewOrderModel);
                               /* String testing = viewOrderModel.getOrderId();
                                System.out.println(testing);*/
                            }
                            viewOrderLoadListener.onViewOrderLoadSuccess(viewOrderModels);
                        } else {
                            viewOrderLoadListener.onViewOrderLoadFailed("No Order Found");
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
        Intent i = new Intent(ViewOrderActivity.this, NotificationActivity.class);
        startActivity(i);
    }
}