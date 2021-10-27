package com.example.myapplication.model;

import com.example.myapplication.FeedbackActivity;

public class FeedbackModel {
    private String feedback, key;

    public FeedbackModel(){

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
