package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.RiceAdapter;
import com.example.myapplication.model.Rice;

import java.util.ArrayList;
import java.util.List;

public class rice_page extends AppCompatActivity {

    RecyclerView riceListRecycler;
    RiceAdapter riceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rice_main_page);

        List<Rice> riceList = new ArrayList<>();
        riceList.add(new Rice("TOMYAM FRIED RICE", "RM 6", R.drawable.tomyam));
        riceList.add(new Rice("CHICKEN SLICE WITH RICE", "RM 6", R.drawable.slice));
        riceList.add(new Rice("CURRY FISH BALL WITH RICE", "RM 6", R.drawable.fishball));

        setRiceListRecycler(riceList);
    }

    private void setRiceListRecycler(List<Rice> riceList){

        riceListRecycler = findViewById(R.id.riceListRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        riceListRecycler.setLayoutManager(layoutManager);
        riceAdapter = new RiceAdapter(this,riceList);
        riceListRecycler.setAdapter(riceAdapter);
    }
}
