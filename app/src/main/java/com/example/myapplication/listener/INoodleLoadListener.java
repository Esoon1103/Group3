package com.example.myapplication.listener;

import com.example.myapplication.model.Noodle;

import java.util.List;

public interface INoodleLoadListener {
    void onNoodleLoadSuccess(List<Noodle> noodleModelList);
    void onNoodleLoadFailed(String message);
}
