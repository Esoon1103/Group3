package com.example.myapplication;

public class UserHelperClass {

    String feedback;

    public UserHelperClass() {
    }

    //Constructor
    public UserHelperClass(String feedback) {
        this.feedback = feedback;
    }

    //Getter
    public String getFeedback() {
        return feedback;
    }

    //Setter
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
