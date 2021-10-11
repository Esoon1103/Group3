package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.RiceAdapter;
import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.listener.IRiceLoadListener;
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

public class rice_page extends AppCompatActivity implements IRiceLoadListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rice_main_page);

        init();
        loadRiceFromFirebase();
    }

    private void loadRiceFromFirebase() {

        List<Rice> riceModels = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("Rice");


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
}
