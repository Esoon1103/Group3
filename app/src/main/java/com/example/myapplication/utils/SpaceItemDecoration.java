package com.example.myapplication.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @androidx.annotation.NonNull View view, @androidx.annotation.NonNull RecyclerView parent, @androidx.annotation.NonNull RecyclerView.State state) {
        if(parent.getChildLayoutPosition(view) % 2 != 0)
        {
            outRect.top=50;
            outRect.bottom=50;
        }
    }
}
