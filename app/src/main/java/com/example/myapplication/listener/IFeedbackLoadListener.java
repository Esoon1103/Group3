package com.example.myapplication.listener;

import com.example.myapplication.model.FeedbackModel;
import com.example.myapplication.model.ViewOrderModel;

import java.util.List;

public interface IFeedbackLoadListener {
    void onFeedbackLoadSuccess(List<FeedbackModel> feedbackModelList);
    void onFeedbackLoadFailed(String message);
}
