package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class reservation_form  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView select_Time, select_Date;
int t1Hour, t1Minutes;
DatePickerDialog.OnDateSetListener setListener;


    @Override
    public void onClick(View view) {


        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_form);

Spinner spinner=findViewById(R.id.spinner1);
ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.name, android.R.layout.simple_spinner_item);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);
spinner.setOnItemSelectedListener(this);

        Spinner spinner1=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence>adapter1=ArrayAdapter.createFromResource(this, R.array.name1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        //ListView myListView=findViewById(R.id.myList);
        //String item[]=new String []{"1st item", "2nd item", "3rd item"};
       //ArrayAdapter<String>myAdapter=new ArrayAdapter<String>(reservation_form.this, android.R.layout.simple_list_item_1,item);
        //myListView.setAdapter(myAdapter);

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

        }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        String text=parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
