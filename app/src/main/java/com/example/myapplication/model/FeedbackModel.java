package com.example.myapplication.model;

import com.example.myapplication.FeedbackActivity;

public class FeedbackModel {
    private String orderId, feedback, key;

    public FeedbackModel(){

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
