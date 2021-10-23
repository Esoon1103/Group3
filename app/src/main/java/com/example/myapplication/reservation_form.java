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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class reservation_form  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IReservationLoadListener {
    TextView select_Time, select_Date;
int t1Hour, t1Minutes;
Button submit_btn;

DatePickerDialog.OnDateSetListener setListener;
IReservationLoadListener reservationLoadListener;
    private FirebaseAuth firebaseAuth;
FirebaseDatabase rootNode;
DatabaseReference reference;


    @Override
    public void onClick(View view) {


        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
DatePickerDialog datePickerDialog=new DatePickerDialog(reservation_form.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
, setListener, year,month,day);
datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
datePickerDialog.show();
            }
        });
setListener=new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
        month=month+1;
        String date = day+"/"+month+"/"+year;
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
                SimpleDateFormat f24Hours=new SimpleDateFormat(
                        "HH:mm"
                );
                try {
                    Date date= f24Hours.parse(time);
                    SimpleDateFormat f12Hours=new SimpleDateFormat(
                           "hh:mm aa"
                    );
                    select_Time.setText(f12Hours.format(date));

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


submit_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
rootNode=FirebaseDatabase.getInstance();
reference=rootNode.getReference("Table_Reservation");

String table_id= spinner1.getSelectedItem().toString();
String date=select_Date.getText().toString();
String time=select_Time.getText().toString();

        Reservation reservation=new Reservation(table_id, date, time);
reference.setValue(reservation);

    }
});
        }





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
}
