package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class reservation_page extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IReservationLoadListener  {
    private Button account1,home1,orderHistory1, cart1;
    private FirebaseAuth firebaseAuth;
private ListView list_reservation_detail;
String temp, table_Number1, temp2;
 private Button arrived;
 int temp1;


 int splited_day;
  int splited_month;
  int splited_year;
DatabaseReference reference1, reference2;
private String time, date, compare_time, time1, date1, table,table1;
int Reser_time  =0;
int curr_time    =0;
TextView timer_text;

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
        timer_text=findViewById(R.id.timer_text);
        firebaseAuth=FirebaseAuth.getInstance();
        ArrayList<String> list=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.reservation_item, list);
        list_reservation_detail.setAdapter(adapter);




        DatabaseReference reference=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Table_Reservation").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){

                   // list.add(snapshot1.getValue().toString());
                    time1 = snapshot1.child("time").getValue(String.class);
                    date1=snapshot1.child("date").getValue(String.class);
                    table=snapshot1.child("table_id").getValue(String.class);

                    list.add(time1);
                    list.add(date1);
                    list.add(table);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference_table3 =FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Table_Number");

        reference_table3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                table_Number1 = snapshot.child("table_Num").getValue(String.class);

                temp1=Integer.valueOf(table_Number1);




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        arrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






              reference1=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                     .getReference("Table_Reservation")
                     .child(firebaseAuth.getUid());
             reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                         table=dataSnapshot.child("table_id").getValue(String.class);
                          time = dataSnapshot.child("time").getValue(String.class);
                          date=dataSnapshot.child("date").getValue(String.class);
                           compare_time  =dataSnapshot.child("compare_time").getValue(String.class);

                          temp=time;
                          
                     }
if(time == null){
    Toast.makeText(getApplicationContext(), "No Reservation", Toast.LENGTH_LONG).show();
}
String split_date[]=date.split("/");

 splited_day=Integer.valueOf(split_date[0]);
  splited_month=Integer.valueOf(split_date[1]);
  splited_year=Integer.valueOf(split_date[2]);



 /*if (Integer.valueOf(getDate_day ())-splited_day>0  ){
     System.out.println("can compare");
 }*/


//current time -booking time >=1 then trigger this function
 if(Integer.parseInt(getCurrentTime_compare())-Integer.parseInt(compare_time)>=1 ){

    temp1=temp1+1;
    temp2=String.valueOf(temp1);


    DatabaseReference drTable2=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Table_Reservation")
            .child(firebaseAuth.getUid());
           drTable2.removeValue();



    FirebaseDatabase add_table5 = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");

    DatabaseReference reference5 =add_table5.getReference("Table_Number").child("table_Num");
    reference5.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {


            reference5.setValue(temp2);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

            Toast.makeText(reservation_page.this,"Reservation Deleted Due to late for More than 1 Hour", Toast.LENGTH_LONG).show();


}
 else if(Integer.parseInt(getCurrentTime_compare())-Integer.parseInt(compare_time)>=0
        && Integer.parseInt(getCurrentTime_compare())-Integer.parseInt(compare_time)<1){


        //duration
        long duration = TimeUnit.MINUTES.toMillis(1);

        //countdown timer
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration=String.format(Locale.ENGLISH,"%2d: %02d", TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                //set converted string on text view
                timer_text.setText(sDuration);
            }

            @Override
            public void onFinish() {
                temp1=temp1+1;
                temp2=String.valueOf(temp1);
//when finish
                //hide text view
                timer_text.setVisibility(View.GONE);
                DatabaseReference drTable1=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Table_Reservation")
                        .child(firebaseAuth.getUid()).child("reservation");
               // drTable1.removeValue();
                drTable1.setValue(null);
               // Toast.makeText(reservation_page.this,"Time Up", Toast.LENGTH_LONG).show();
                FirebaseDatabase add_table5 = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");

                DatabaseReference reference5 =add_table5.getReference("Table_Number").child("table_Num");
                reference5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        reference5.setValue(temp2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(getApplicationContext(), "Reservation Time End", Toast.LENGTH_LONG).show();



            }
        }.start();




}
 else{
     Toast.makeText(getApplicationContext(), "Wrong Time", Toast.LENGTH_LONG).show();
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

    private String getDate (){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private String getCurrentTime_compare(){

       return new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        
    }
    private String getDate_day (){
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }
    private String getDate_month (){
        return new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
    }
    private String getDate_year (){
        return new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
    }

/*public Boolean minusdate_day(int curr, int book){
        if (curr-book>0){
            return
        }
}*/



}
