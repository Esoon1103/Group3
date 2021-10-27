package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.listener.IReservationLoadListener;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class reservation_form  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IReservationLoadListener {
    TextView select_Time, select_Date;
    private Button account1, home1, orderHistory1, cart1;
    String table_validate, time1, table, table1;
    int temp=0;
    ArrayList<String>list=new ArrayList<>();
int t1Hour, t1Minutes;
Button submit_btn;
String table_Number, temp1;

ListView show_avail_table;
DatePickerDialog.OnDateSetListener setListener;
IReservationLoadListener reservationLoadListener;
    private FirebaseAuth firebaseAuth;

FirebaseDatabase rootNode;
String compare_time="";
    DatabaseReference deleteNode,deleteNode1;
DatabaseReference reference, reference1;


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
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth =FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_form);
submit_btn=findViewById(R.id.submit_btn);


        Spinner spinner1=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence>adapter1=ArrayAdapter.createFromResource(this, R.array.name1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);





        select_Date =findViewById(R.id.select_Date);

        Calendar calendar =Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day1=calendar.get(Calendar.DAY_OF_MONTH);

        select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
DatePickerDialog datePickerDialog=new DatePickerDialog(reservation_form.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
, setListener, year,month,day1);
datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
datePickerDialog.show();
            }
        });

setListener=new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
        month=month+1;
        String date = dayofMonth+"/"+month+"/"+year;
        select_Date.setText(date);
    }
};

        select_Time=findViewById(R.id.select_Time);
select_Time.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
TimePickerDialog timePickerDialog=new TimePickerDialog(
        reservation_form.this,
        android.R.style.Theme_Material_Light_Dialog_MinWidth,
        new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourofDay, int minutes) {
t1Hour=hourofDay;
t1Minutes=minutes;
//store in string
              String time=t1Hour+":"+t1Minutes;
                compare_time=t1Hour+"";
                SimpleDateFormat f24Hours=new SimpleDateFormat(
                        "HH:mm"
                );
                try {
                    Date date= f24Hours.parse(time);
                    SimpleDateFormat f12Hours=new SimpleDateFormat(
                           "hh:mm aa"
                    );
                    select_Time.setText(f12Hours.format(date));
                    String.format(compare_time, f24Hours);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        },12,0,false
);
timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
timePickerDialog.updateTime(t1Hour,t1Minutes);
timePickerDialog.show();
    }
});

        DatabaseReference reference_table1 =FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Table_Number");
        reference_table1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    table_Number = snapshot.child("table_Num").getValue(String.class);

                    temp=Integer.valueOf(table_Number);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference60 = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Table_Reservation")
                .child(firebaseAuth.getUid());

        reference60.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    table_validate=dataSnapshot.child("table_id").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });


        //temp=Integer.parseInt(table_Number);
submit_btn.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view) {
        System.out.println(table_validate);
        if(!validateDate(getDate_day (), getDate_month(), getDate_year())|!validateTime(compare_time)){
            return;
        }



if (table_validate==null){



    if(temp==0){//table num
        Toast.makeText(reservation_form.this,"No More Table", Toast.LENGTH_LONG).show();

    }
    else{

        firebaseAuth=FirebaseAuth.getInstance();
        rootNode=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference=rootNode.getReference("Table_Reservation").child(firebaseAuth.getUid()).child("reservation");

        String table_id= spinner1.getSelectedItem().toString();
        String date=select_Date.getText().toString();
        String time=select_Time.getText().toString();
        String compare_Time=compare_time;

        Reservation reservation=new Reservation(table_id, date, time,compare_Time );
        reference.setValue(reservation);


        //  deleteTable(table_id);

        Toast.makeText(getApplicationContext(), "Reserved", Toast.LENGTH_LONG).show();



        temp=temp-1;
        temp1=String.valueOf(temp);
        FirebaseDatabase add_table = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference3 =add_table.getReference("Table_Number").child("table_Num");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                reference3.setValue(temp1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    refreshPage_Reservation();

}
else {
    Toast.makeText(reservation_form.this,"You Already Have Reservation", Toast.LENGTH_LONG).show();
    refreshPage_Reservation();

}








    }

});

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);



        //show_avail_table=findViewById(R.id.show_avail_table);

/*
        ArrayAdapter adapter_table=new ArrayAdapter<String>(this,R.layout.show_table_item, list);
        show_avail_table.setAdapter(adapter_table);
        DatabaseReference reference_table =FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Table");
        reference_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot Snapshot_table : snapshot.getChildren()){
                    list.add(Snapshot_table.getValue().toString());

                }
                adapter_table.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/





        }

   /* private void deleteTable(String table_id) {
        DatabaseReference drTable=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Table")
                .child(table_id);
        drTable.removeValue();

    }*/


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        String text=parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
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

public Boolean validateDate(String day, String month, String year){
        int currday1, month1, year1;
        currday1=Integer.valueOf(day);
        month1=Integer.valueOf(month);
    year1=Integer.valueOf(year);
        String val =select_Date.getText().toString();
    String split_date[]=val.split("/");
    int bookday=Integer.valueOf(split_date[0]);
    int bookmonth=Integer.valueOf(split_date[1]);
    int bookyear=Integer.valueOf(split_date[2]);

    if (val.isEmpty()
    ) {
        select_Date.setError("Cannot Empty");
return false;
    }

    else if(bookday<currday1 ){
        select_Date.setError("Cannot Empty");
        return false;
    }
    else if(bookmonth<month1 ){
        select_Date.setError("Cannot Empty");
        return false;
    }
    else if(bookyear<year1 ){
        select_Date.setError("Cannot Empty");
        return false;
    }

    else{
        select_Date.setError(null);
        return  true;
    }

}
    public Boolean validateTime( String compare_time){
        String val =select_Time.getText().toString();
        int val1= Integer.valueOf(compare_time);
        if (val.isEmpty()
        ) {
            select_Time.setError("Cannot Empty");
            return false;
        }
        else if(val1>16|| val1< 8){
            select_Time.setError("Not Available");
            return false;
        }
        else{
            select_Time.setError(null);
            return  true;
        }
    }



    public void refreshPage_Reservation(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
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

/*public Boolean validate_Table(){
   reference1=FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Table_Reservation")
            .child(firebaseAuth.getUid());
    reference1.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                table1=dataSnapshot.child("table_id").getValue(String.class);
                temp=table;

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getApplicationContext(), "can", Toast.LENGTH_LONG).show();
        }
    });
    return true;}*/

}

