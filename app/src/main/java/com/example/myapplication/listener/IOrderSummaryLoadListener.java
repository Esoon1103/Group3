package com.example.myapplication.listener;

import com.example.myapplication.model.OrderSummaryModel;
import com.example.myapplication.model.ViewOrderModel;

import java.util.List;

public interface IOrderSummaryLoadListener {
    void onOrderSummaryLoadSuccess(List<OrderSummaryModel> orderSummaryModelList);
    void onOrderSummaryLoadFailed(String message);
}
