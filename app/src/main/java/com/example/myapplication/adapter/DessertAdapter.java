package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Dessert;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.DessertViewHolder>{

    Context context;
    List<Dessert> dessertList;

    //Constructor
    public DessertAdapter(Context context, List<Dessert> dessertList) {
        this.context = context;
        this.dessertList = dessertList;
    }


    //Display item layout by using the View Holder
    @NonNull
    @Override
    public DessertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DessertViewHolder(LayoutInflater.from(context).inflate(R.layout.rice_item,parent,false));
    }


    //Set the Rice details: Image, Name, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull DessertViewHolder holder, int position) {
        Glide.with(context)
                .load(dessertList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(dessertList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(dessertList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return dessertList.size();
    }

    public class DessertViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        private Unbinder unbinder;

        public DessertViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);

        }
    }
}

