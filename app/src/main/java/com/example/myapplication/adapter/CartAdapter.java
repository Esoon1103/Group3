package com.example.myapplication.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Cart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Cart> cartModelList;

    public CartAdapter(Context context, List<Cart> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (cartModelList.isEmpty())
            return new CartViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cart_empty,parent,false));
        else
            return new CartViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(context)
                .load(cartModelList.get(position).getImage()) //Get image from the position
                .into(holder.foodImage); //Set into the food Image
        holder.foodPrice.setText(new StringBuilder("RM ").append(cartModelList.get(position).getPrice()));
        holder.foodName.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtQuantity.setText(new StringBuilder().append(cartModelList.get(position).getQuantity()));


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btnPlusOne)
        ImageButton btnPlusOne;
        @BindView(R.id.btnNegativeOne)
        ImageButton btnNegativeOne;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        @BindView(R.id.txtQuantity)
        TextView txtQuantity;
        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        Unbinder unbinder;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
