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
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Drinks;
import com.example.myapplication.model.Rice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>{

    private Context context;
    private List<Drinks> drinksList;
    private ICartLoadListener cartLoadListener;

    //Constructor
    public DrinkAdapter(Context context, List<Drinks> drinkList, ICartLoadListener cartLoadListener) {
        this.context = context;
        this.drinksList = drinkList;
        this.cartLoadListener = cartLoadListener;
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
        holder.setListener((view, adapterPosition) -> {
            addToCart(drinksList.get(position));
        });
    }

    private void addToCart(Drinks drinkModel) {
        DatabaseReference userCart = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart")
                .child("drink");

        userCart.child(drinkModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            //Update quantity and totalPrice
                            Cart cartModel = snapshot.getValue(Cart.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("quantity", cartModel.getQuantity());
                            updateData.put("totalPrice", cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            userCart.child(drinkModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid -> {
                                        cartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> cartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        else // If item not have in cart, add new
                        {
                            Cart cartModel = new Cart();
                            cartModel.setName(drinkModel.getName());
                            cartModel.setImage(drinkModel.getImage());
                            cartModel.setQuantity(1);
                            cartModel.setKey(cartModel.getKey());
                            cartModel.setPrice(drinkModel.getPrice());
                            cartModel.setTotalPrice(Float.parseFloat(drinkModel.getPrice()));

                            userCart.child(drinkModel.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(aVoid -> {
                                        cartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> cartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }


    //
    public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.foodImage)
        ImageView foodImage;
        @BindView(R.id.foodName)
        TextView foodName;
        @BindView(R.id.foodPrice)
        TextView foodPrice;

        IAddtoCartClickListener listener;

        public void setListener(IAddtoCartClickListener listener) {
            this.listener = listener;
        }


        private Unbinder unbinder;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            listener.onAddtoCartClick(view,getAbsoluteAdapterPosition());
        }
    }
}
