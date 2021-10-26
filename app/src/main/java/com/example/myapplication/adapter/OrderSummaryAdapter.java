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
import com.example.myapplication.listener.IOrderSummaryLoadListener;
import com.example.myapplication.listener.IViewOrderLoadListener;
import com.example.myapplication.model.OrderSummaryModel;
import com.example.myapplication.model.ViewOrderModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {
    private Context context;
    private List<OrderSummaryModel> orderSummaryModelList;
    private IOrderSummaryLoadListener orderSummaryLoadListener;

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public OrderSummaryAdapter.OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new OrderSummaryAdapter.OrderSummaryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_order_summary,parent,false));
    }

    //Set the Order summary: orderId,image,name,price,quantity,totalPrice,time,date,
    //To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.OrderSummaryViewHolder holder, int position) {
        Glide.with(context)
                .load(orderSummaryModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView2);
        holder.txtOrderID2.setText(new StringBuilder("#").append(orderSummaryModelList.get(position).getOrderId()));
        holder.txtDate2.setText(new StringBuilder().append(orderSummaryModelList.get(position).getDate()));
        holder.txtTime2.setText(new StringBuilder().append(orderSummaryModelList.get(position).getTime()));
        holder.txtfoodName.setText(new StringBuilder().append(orderSummaryModelList.get(position).getOrderId()));
        holder.txtfoodPrice.setText(new StringBuilder("$").append(orderSummaryModelList.get(position).getDate()));
        holder.txtfoodQty.setText(new StringBuilder().append(orderSummaryModelList.get(position).getTime()));
        holder.txtfoodTotal.setText(new StringBuilder("$").append(orderSummaryModelList.get(position).getOrderId()));
    }

    @Override
    public int getItemCount(){
        return orderSummaryModelList.size();
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.txtfoodName)
        TextView txtfoodName;
        @BindView(R.id.txtfoodPrice)
        TextView txtfoodPrice;
        @BindView(R.id.txtfoodQty)
        TextView txtfoodQty;
        @BindView(R.id.txtfoodTotal)
        TextView txtfoodTotal;
        @BindView(R.id.txtOrderID2)
        TextView txtOrderID2;
        @BindView(R.id.txtDate2)
        TextView txtDate2;
        @BindView(R.id.txtTime2)
        TextView txtTime2;

        private Unbinder unbinder;

        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

    //Constructor
    public OrderSummaryAdapter(Context context, List<OrderSummaryModel> orderSummaryModelList, IOrderSummaryLoadListener orderSummaryLoadListener) {
        this.context = context;
        this.orderSummaryModelList = orderSummaryModelList;
        this.orderSummaryLoadListener = orderSummaryLoadListener;
    }

}

