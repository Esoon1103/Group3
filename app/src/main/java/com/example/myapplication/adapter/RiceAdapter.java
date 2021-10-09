package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Rice;
import com.example.myapplication.rice_page;

import java.util.List;

public class RiceAdapter extends RecyclerView.Adapter<RiceAdapter.RiceViewHolder>{

    Context context;
    List<Rice> riceList;

    public RiceAdapter(Context context, List<Rice> riceList) {
        this.context = context;
        this.riceList = riceList;
    }

    @NonNull
    @Override
    public RiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiceViewHolder(LayoutInflater.from(context).inflate(R.layout.rice_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiceViewHolder holder, int position) {

        holder.foodImage.setImageResource(riceList.get(position).getImage());
        holder.foodName.setText(riceList.get(position).getName());
        holder.foodPrice.setText(riceList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return riceList.size();
    }

    public static final class RiceViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodPrice, foodName;

        public RiceViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }
}
