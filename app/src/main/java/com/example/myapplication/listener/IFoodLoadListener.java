package com.example.myapplication.listener;

import com.example.myapplication.model.Food;

import java.util.List;

public interface IFoodLoadListener {
    void onRiceLoadSuccess(List<Food> foodModelList);
    void onRiceLoadFailed(String message);
}
