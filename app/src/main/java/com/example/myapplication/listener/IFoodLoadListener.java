package com.example.myapplication.listener;

import com.example.myapplication.model.Food;

import java.util.List;

public interface IFoodLoadListener {
    void onFoodLoadSuccess(List<Food> foodModelList);
    void onFoodLoadFailed(String message);
}
