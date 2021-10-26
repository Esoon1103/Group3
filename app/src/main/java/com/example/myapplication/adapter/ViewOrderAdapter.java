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
import com.example.myapplication.listener.IAddtoCartClickListener;
import com.example.myapplication.listener.ICartLoadListener;
import com.example.myapplication.listener.IViewOrderLoadListener;
import com.example.myapplication.listener.RecyclerViewClickInterface;
import com.example.myapplication.model.Rice;
import com.example.myapplication.model.ViewOrderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderViewHolder> {

    private Context context;
    private List<ViewOrderModel> viewOrderModelList;
    private IViewOrderLoadListener viewOrderLoadListener;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    //Constructor
    public ViewOrderAdapter(Context context, List<ViewOrderModel> viewOrderModelList, IViewOrderLoadListener viewOrderLoadListener, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.viewOrderModelList = viewOrderModelList;
        this.viewOrderLoadListener = viewOrderLoadListener;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public ViewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewOrderAdapter.ViewOrderViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_view_order, parent, false));
    }

    //Set the Order details: OrderID, Date, Price - To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull ViewOrderViewHolder holder, int position) {
      /*  Glide.with(context)
                .load(viewOrderModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView); */
        holder.txtOrderID.setText(new StringBuilder("#").append(viewOrderModelList.get(position).getOrderId()));
        holder.txtDate.setText(new StringBuilder().append(viewOrderModelList.get(position).getDate()));
        holder.txtTime.setText(new StringBuilder().append(viewOrderModelList.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return viewOrderModelList.size();
    }

    public class ViewOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView1)
        ImageView imageView;
        @BindView(R.id.txtOrderID)
        TextView txtOrderID;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtTime)
        TextView txtTime;

        private Unbinder unbinder;

        public ViewOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    recyclerViewClickInterface.onItemClick(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
