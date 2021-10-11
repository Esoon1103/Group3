package com.example.myapplication.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Rice;
import com.example.myapplication.rice_page;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RiceAdapter extends RecyclerView.Adapter<RiceAdapter.RiceViewHolder>{

    Context context;
    List<Rice> riceList;

    //Constructor
    public RiceAdapter(Context context, List<Rice> riceList) {
        this.context = context;
        this.riceList = riceList;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public RiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiceViewHolder(LayoutInflater.from(context).inflate(R.layout.rice_item,parent,false));
    }


    //Set the Rice details: Image, Name, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull RiceViewHolder holder, int position) {
        Glide.with(context)
                .load(riceList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(riceList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(riceList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return riceList.size();
    }


    //
    public class RiceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        private Unbinder unbinder;

        public RiceViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);

        }
    }
}
