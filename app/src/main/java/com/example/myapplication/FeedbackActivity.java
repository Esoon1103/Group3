package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.adapter.FeedbackAdapter;
import com.example.myapplication.listener.IFeedbackLoadListener;
import com.example.myapplication.model.FeedbackModel;
import com.example.myapplication.utils.SpaceItemDecoration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends AppCompatActivity implements IFeedbackLoadListener {

    EditText etFeedback;
    Button SubmitFeedback;

    DatabaseReference reference;

    @BindView(R.id.recycler_view_feedback)
    RecyclerView recycler_view_feedback;
    @BindView(R.id.feedbackLayout)
    RelativeLayout feedbackLayout;

    IFeedbackLoadListener feedbackLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<FeedbackModel> feedbackModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        etFeedback = findViewById(R.id.etFeedback);
        SubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        reference = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child(firebaseAuth.getUid()).child("Feedback");

        SubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Accessing to orderID for Feedback
                reference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            reference.child("Feedback");
                            String feedback = etFeedback.getText().toString();
                            HashMap hashMap = new HashMap();
                            hashMap.put("feedback", feedback);

                            //Write Feedback to database
                            //reference.child("Feedback").setValue(hashMap);

                            reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        init();
        loadOrderFromFirebase();

    }

    private void loadOrderFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference1 = database.getReference("Users")
                .child(firebaseAuth.getUid());

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String orderId = snapshot.child("orderId").getValue().toString();
                //System.out.println(orderId);
                if (snapshot.exists()) {
                    for (DataSnapshot feedbackSnapshot : snapshot.getChildren()) {
                        FeedbackModel feedbackModel = feedbackSnapshot.getValue(FeedbackModel.class);
                        feedbackModel.setKey(feedbackSnapshot.getKey());
                        feedbackModels.add(feedbackModel);
                               /* String testing = viewOrderModel.getOrderId();
                                System.out.println(testing);*/
                    }
                    feedbackLoadListener.onFeedbackLoadSuccess(feedbackModels);
                } else {
                    feedbackLoadListener.onFeedbackLoadFailed("No Feedback Given");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                feedbackLoadListener.onFeedbackLoadFailed(error.getMessage());
            }
        });
    }

    private void init() {
        ButterKnife.bind(this);

        feedbackLoadListener = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_feedback.setLayoutManager(linearLayoutManager);
        recycler_view_feedback.addItemDecoration(new SpaceItemDecoration());

    }

    @Override
    public void onFeedbackLoadSuccess(List<FeedbackModel> feedbackModelList) {
        FeedbackAdapter adapter = new FeedbackAdapter(this, feedbackModelList, feedbackLoadListener);
        recycler_view_feedback.setAdapter(adapter);
    }

    @Override
    public void onFeedbackLoadFailed(String message) {
        Snackbar.make(feedbackLayout, message, Snackbar.LENGTH_LONG).show();
    }
}