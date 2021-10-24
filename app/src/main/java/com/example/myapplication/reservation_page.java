package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ReservationAdapter;
import com.example.myapplication.listener.IReservationLoadListener;
import com.example.myapplication.model.GetTimeAndDate;
import com.example.myapplication.model.Reservation;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class reservation_page extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IReservationLoadListener  {
    private Button account1,home1,orderHistory1, cart1;
    private FirebaseAuth firebaseAuth;
private ListView list_reservation_detail;
 private Button arrived;


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
                Intent toLogin3 = new Intent(this, CartActivity.class);
                startActivity(toLogin3);
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_main_page);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1= findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1= findViewById(R.id.cart1);
        cart1.setOnClickListener(this);
        arrived=findViewById(R.id.arrived);
        arrived.setOnClickListener(this);

        list_reservation_detail=findViewById(R.id.list_reservation_detail);
        firebaseAuth=FirebaseAuth.getInstance();
ArrayList<String> list=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.reservation_item, list);
        list_reservation_detail.setAdapter(adapter);

        DatabaseReference reference=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Table_Reservation").child(firebaseAuth.getUid()).child("reservation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){

                    list.add(snapshot1.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayList<String> list_date=new ArrayList<>();
        ArrayList<String> list_time=new ArrayList<>();
         final String date, time;
        arrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DatabaseReference reference1=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Table_Reservation")
                     .child(firebaseAuth.getUid()).child("reservation");
             reference1.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     for (DataSnapshot snapshot1: snapshot.getChildren()){
                         String date, time;
                         date=snapshot.child("Table_Reservation").child(firebaseAuth.getUid()).child("reservation").child("date").getValue(String.class);
                          time=snapshot.child("Table_Reservation").child(firebaseAuth.getUid()).child("reservation").child("time").getValue(String.class);
                         System.out.println(date);
                         System.out.println(time);
                     }




                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
            }

        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onReservationLoadSuccess(List<Reservation> ReservationModelList) {

    }

    @Override
    public void onReservationLoadFailed(String message) {

    }
    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getDate (){
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }
    private  void getlist(Reservation list){
        list.getDate();
        list.getTime();
    }
}
