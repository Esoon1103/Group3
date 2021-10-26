package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.google.firebase.ktx.Firebase;

public class FeedbackPage extends AppCompatActivity {

    EditText etFeedback;
    Button SubmitFeedback;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference reference;

    public static String orderId;

    ArrayList<String> orderUid = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        etFeedback = findViewById(R.id.etFeedback);
        SubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        reference = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users");

        SubmitFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Accessing to orderID for Feedback
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            //String orderId = dataSnapshot.child("Order").getValue().toString();
                            String orderId = dataSnapshot.child(firebaseAuth.getUid()).child("Order")
                                    .child("Feedback").getValue().toString();
                            
                            orderUid.add(orderId);
                            System.out.println(orderId);

                        }
                        for (int i = 0; i < orderUid.size(); i++) {
                                System.out.println(orderUid.get(i));
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

                /*//Send feedback and store to firebase
                String feedback = etFeedback.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("feedback", feedback);

                reference.child(firebaseAuth.getUid())
                        .child("Order").child("1635047830898")
                        .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(FeedbackPage.this,"Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                });*/


    }

}

