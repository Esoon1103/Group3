package com.example.myapplication.listener;

import com.example.myapplication.model.Dessert;

import java.util.List;

public interface IDessertLoadListener {
    void onDessertLoadSuccess(List<Dessert> dessertModelList);
    void onDessertLoadFailed(String message);
}
