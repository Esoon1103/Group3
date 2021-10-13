package com.example.myapplication.listener;

import com.example.myapplication.model.Reservation;


import java.util.List;

public interface IReservationLoadListener {

    void onReservationLoadSuccess(List<Reservation> ReservationModelList);
    void onReservationLoadFailed(String message);
}
