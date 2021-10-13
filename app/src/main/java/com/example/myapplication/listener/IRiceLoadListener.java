package com.example.myapplication.listener;

import com.example.myapplication.model.Dessert;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Noodle;
import com.example.myapplication.model.Rice;

import java.util.List;

public interface IRiceLoadListener {
    void onRiceLoadSuccess(List<Rice> riceModelList);
    void onRiceLoadFailed(String message);

    void onNoodleLoadSuccess(List<Noodle>noodleModelList);
    void onNoodleLoadFailed(String message);

    void onDessertLoadSuccess(List<Dessert>dessertModelList);
    void onDessertLoadFailed(String message);

    void onDrinkLoadSuccess(List<Drinks>drinksModelList);
    void onDrinkLoadFailed(String message);
}
