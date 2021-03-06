package com.example.myapplication.listener;

import android.view.View;

import com.example.myapplication.model.ViewOrderModel;

import java.util.List;

public interface IViewOrderLoadListener {
    void onViewOrderLoadSuccess(List<ViewOrderModel> viewOrderModelList);
    void onViewOrderLoadFailed(String message);
}
