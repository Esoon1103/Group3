package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.listener.IFeedbackLoadListener;
import com.example.myapplication.model.FeedbackModel;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private Context context;
    private List<FeedbackModel> feedbackModelList;
    private IFeedbackLoadListener feedbackLoadListener;

    //Display item layout by using the View Holder
    @NonNull
    @Override
    public FeedbackAdapter.FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new FeedbackAdapter.FeedbackViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_feedback,parent,false));
    }

    //Set the Order summary: orderId,image,name,price,quantity,totalPrice,time,date,
    //To the specific position by using the View Holder
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.FeedbackViewHolder holder, int position) {
       /* Glide.with(context)
                .load(feedbackModelList.get(position).getImage()) //Get image from the position
                .into(holder.imageView2); */
        holder.txtFeedback.setText(new StringBuilder().append(feedbackModelList.get(position).getFeedback()));

    }

    @Override
    public int getItemCount(){
        return feedbackModelList.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtFeedback)
        TextView txtFeedback;

        private Unbinder unbinder;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

    //Constructor
    public FeedbackAdapter(Context context, List<FeedbackModel> feedbackModelList, IFeedbackLoadListener feedbackLoadListener) {
        this.context = context;
        this.feedbackModelList = feedbackModelList;
        this.feedbackLoadListener = feedbackLoadListener;
    }

}
