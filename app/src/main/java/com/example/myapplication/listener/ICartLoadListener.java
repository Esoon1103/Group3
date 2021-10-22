package com.example.myapplication.listener;

import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Dessert;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<Cart> cartModelList);
    void onCartLoadFailed(String message);
}
