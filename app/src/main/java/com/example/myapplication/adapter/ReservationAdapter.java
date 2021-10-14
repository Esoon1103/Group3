package com.example.myapplication.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Reservation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>{
    Context context;
    List<Reservation> reservationList;

    //constructor
    public ReservationAdapter(Context context,List<Reservation> reservationList){
        this.context=context;
        this.reservationList=reservationList;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReservationViewHolder(LayoutInflater.from(context).inflate(R.layout.reservation_item,parent,false));
    }

    //set the rice detail
    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationViewHolder holder, int position) {
     holder.table_id.setText(new StringBuilder().append(reservationList.get(position).getTable_id()));
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        private Unbinder unbinder;

        @BindView(R.id.table_id)
        TextView table_id;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
