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

import com.example.myapplication.adapter.ReservationAdapter;
import com.example.myapplication.listener.IReservationLoadListener;
import com.example.myapplication.model.Reservation;
import com.example.myapplication.model.Rice;
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

public class reservation_page extends AppCompatActivity implements View.OnClickListener, IReservationLoadListener {
    private Button account1,home1,orderHistory1, cart1;

    @BindView(R.id.reservationListRecycler)
    RecyclerView reservationListRecycler;

    @BindView(R.id.reservation_layout)
    RelativeLayout reservation_layout;

    IReservationLoadListener reservationLoadListener;
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
        loadReservationFromFirebase();

    }

    @Override
    public void onReservationLoadSuccess(List<Reservation> ReservationModelList) {
        ReservationAdapter adapter=new ReservationAdapter(this,ReservationModelList);
        reservationListRecycler.setAdapter(adapter);
    }

    @Override
    public void onReservationLoadFailed(String message) {
        Snackbar.make(reservation_layout,message,Snackbar.LENGTH_LONG).show();
    }

    private void loadReservationFromFirebase() {

        List<Reservation> reservationModels = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("Reservation");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot riceSnapshot:snapshot.getChildren())
                    {
                        Reservation reservationModel = riceSnapshot.getValue(Reservation.class);
                        reservationModel.setKey(riceSnapshot.getKey());
                        reservationModels.add(reservationModel);
                    }
                    reservationLoadListener.onReservationLoadSuccess(reservationModels);
                }
                else
                    reservationLoadListener.onReservationLoadFailed("Can't find the Rice");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reservationLoadListener.onReservationLoadFailed(error.getMessage());
            }
        });
    }

    private void init(){
        ButterKnife.bind(this);


        reservationLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        reservationListRecycler.setLayoutManager(linearLayoutManager);
        reservationListRecycler.addItemDecoration(new SpaceItemDecoration());


    }
}
