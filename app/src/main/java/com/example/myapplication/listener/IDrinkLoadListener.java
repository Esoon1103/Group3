package com.example.myapplication.listener;

import com.example.myapplication.model.Drinks;

import java.util.List;

public interface IDrinkLoadListener {
    void onDrinkLoadSuccess(List<Drinks> drinksModelList);
    void onDrinkLoadFailed(String message);
}
