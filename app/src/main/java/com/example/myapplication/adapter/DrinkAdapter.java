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
import com.example.myapplication.model.Drinks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>{

    Context context;
    List<Drinks> drinksList;

    //Constructor
    public DrinkAdapter(Context context, List<Drinks> drinkList) {
        this.context = context;
        this.drinksList = drinkList;
    }


    //Display item layout by using the View Holder
    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.rice_item,parent,false));
    }


    //Set the Rice details: Image, Name, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Glide.with(context)
                .load(drinksList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(drinksList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(drinksList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }


    //
    public class DrinkViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        private Unbinder unbinder;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);

        }
    }
}
