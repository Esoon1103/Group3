package com.example.myapplication.listener;

import com.example.myapplication.model.Dessert;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Noodle;
import com.example.myapplication.model.Rice;

import java.util.List;

public interface IRiceLoadListener {
    void onRiceLoadSuccess(List<Rice> riceModelList);
    void onRiceLoadFailed(String message);
}
