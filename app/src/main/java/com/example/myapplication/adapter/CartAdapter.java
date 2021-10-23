package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.CartActivity;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.UpdateCartEvent;
import com.example.myapplication.model.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

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

        holder.btnNegativeOne.setOnClickListener(view -> {
            minusCartItem(holder, cartModelList.get(position));
        });
        holder.btnPlusOne.setOnClickListener(view -> {
            plusCartItem(holder, cartModelList.get(position));
        });

        holder.btnDelete.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete item")
                    .setMessage("Do you want to delete the item?")
                    .setNegativeButton("CANCEL", (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton("OK", (dialog2, which) -> {

                        //Temp remove
                        notifyItemRemoved(position);

                        deleteFoodFromFirebase(cartModelList.get(position));
                        //deleteNoodleFromFirebase(cartModelList.get(position));
                        //deleteDessertFromFirebase(cartModelList.get(position));
                        //deleteDrinkFromFirebase(cartModelList.get(position));
                        dialog2.dismiss();
                    }).create();
            dialog.show();
        });

    }

    private void deleteFoodFromFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Food")
                .child(cart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    /*
    private void deleteNoodleFromFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Noodle")
                .child(cart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    private void deleteDessertFromFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Dessert")
                .child(cart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    private void deleteDrinkFromFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Drink")
                .child(cart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    */
    private void plusCartItem(CartViewHolder holder, Cart cart) {
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setTotalPrice(cart.getQuantity()*Float.parseFloat(cart.getPrice()));

        holder.txtQuantity.setText(new StringBuilder().append(cart.getQuantity()));
        updateFoodFirebase(cart);
        //updateNoodleFirebase(cart);
        //updateDessertFirebase(cart);
        //updateDrinkFirebase(cart);
    }

    private void minusCartItem(CartViewHolder holder, Cart cart) {
        if(cart.getQuantity()>1)
        {

            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getQuantity()*Float.parseFloat(cart.getPrice()));

            holder.txtQuantity.setText(new StringBuilder().append(cart.getQuantity()));


            updateFoodFirebase(cart);
            //updateNoodleFirebase(cart);
            //updateDessertFirebase(cart);
            //updateDrinkFirebase(cart);
        }
    }

    private void updateFoodFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Food")
                .child(cart.getKey())
                .setValue(cart)
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }

    /*
    private void updateNoodleFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Noodle")
                .child(cart.getKey())
                .setValue(cart)
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    private void updateDessertFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Dessert")
                .child(cart.getKey())
                .setValue(cart)
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    private void updateDrinkFirebase(Cart cart) {
        FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Cart").child("UNIQUE_USER_ID").child("Drink")
                .child(cart.getKey())
                .setValue(cart)
                .addOnSuccessListener(aVoid-> EventBus.getDefault().postSticky(new UpdateCartEvent()));
    }
    */
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
        @BindView(R.id.btnOrder)
        Button btnOrder;

        Unbinder unbinder;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

}
