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

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class reservation_form  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView select_Time;
    EditText edit_Date;
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

        select_Time =findViewById(R.id.select_Time);
        edit_Date=findViewById(R.id.edit_Date);

        Calendar calendar =Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        select_Time.setOnClickListener(new View.OnClickListener() {
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
        select_Time.setText(date);
    }
};
edit_Date.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
DatePickerDialog datePickerDialog=new DatePickerDialog(reservation_form.this,
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
month=month+1;
String date=day+"/"+month+"/"+year;
edit_Date.setText(date);
            }
        }, year,month,day);
datePickerDialog.show();

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
