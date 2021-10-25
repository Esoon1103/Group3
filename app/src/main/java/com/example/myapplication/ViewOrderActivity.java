package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myapplication.adapter.RiceAdapter;
import com.example.myapplication.adapter.ViewOrderAdapter;
import com.example.myapplication.listener.IViewOrderLoadListener;
import com.example.myapplication.model.ViewOrderModel;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ViewOrderActivity extends AppCompatActivity implements IViewOrderLoadListener {

    @BindView(R.id.recycler_view_order)
    RecyclerView recycler_view_order;
    @BindView(R.id.viewOrderLayout)
    RelativeLayout viewOrderLayout;

    IViewOrderLoadListener viewOrderLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        init();
        loadOrderFromFirebase();
    }

    private void loadOrderFromFirebase() {
        List<ViewOrderModel> viewOrderModels = new ArrayList<>();

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_view_order.setLayoutManager(gridLayoutManager);
        recycler_view_order.addItemDecoration(new SpaceItemDecoration());
    }

    @Override
    public void onViewOrderLoadSuccess(List<ViewOrderModel> viewOrderModelList) {
        ViewOrderAdapter adapter = new ViewOrderAdapter(this,viewOrderModelList, viewOrderLoadListener);
        recycler_view_order.setAdapter(adapter);
    }

    @Override
    public void onViewOrderLoadFailed(String message) {
        Snackbar.make(viewOrderLayout,message,Snackbar.LENGTH_LONG).show();
    }
}