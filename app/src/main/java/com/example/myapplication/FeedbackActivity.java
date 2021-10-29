package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends AppCompatActivity implements IFeedbackLoadListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etFeedback;
    Button SubmitFeedback;
    private Button account1,home1, orderHistory1,cart1;

    DatabaseReference reference;

    @BindView(R.id.recycler_view_feedback)
    RecyclerView recycler_view_feedback;
    @BindView(R.id.feedbackLayout)
    RelativeLayout feedbackLayout;

    IFeedbackLoadListener feedbackLoadListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<FeedbackModel> feedbackModels = new ArrayList<>();

    String timestamp = "" + System.currentTimeMillis();
    Calendar cal = Calendar.getInstance();
    String text = "";

    public void onClick(View view) {

        switch(view.getId()){
            case R.id.account1:
                Intent toLogin = new Intent(this, Account.class);
                startActivity(toLogin);
                break;
            case R.id.home1:
                Intent toLogin1 = new Intent(this, HomePage.class);
                startActivity(toLogin1);
                break;
            case R.id.orderHistory1:
                Intent toLogin2 = new Intent(this, orderHistory.class);
                startActivity(toLogin2);
                break;
            case R.id.cart1:
                Intent toLogin3 = new Intent(this, CartActivity.class);
                startActivity(toLogin3);
                break;

            case R.id.btnChangePass:
                Intent changePass = new Intent(this, changePassword.class);
                startActivity(changePass);
                break;
        } //switch
    } //onClick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        account1 = findViewById(R.id.account1);
        account1.setOnClickListener(this);

        home1 = findViewById(R.id.home1);
        home1.setOnClickListener(this);

        orderHistory1 = findViewById(R.id.orderHistory1);
        orderHistory1.setOnClickListener(this);

        cart1 = findViewById(R.id.cart1);
        cart1.setOnClickListener(this);

        etFeedback = findViewById(R.id.etFeedback);
        SubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerFood);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(FeedbackActivity.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.foodList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);


        reference = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child(firebaseAuth.getUid());

        SubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Accessing to orderID for Feedback
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String feedback = etFeedback.getText().toString();
                            String date = "" + cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH)+1) + "-" +
                                    cal.get(Calendar.YEAR);
                            String time = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

                            if(feedback.isEmpty()){
                                Toast.makeText(FeedbackActivity.this,
                                        "No input detected", Toast.LENGTH_LONG).show();
                            } else {

                                reference.child("Feedback")
                                        .child(timestamp).child("feedback")
                                        .setValue(feedback);

                                reference.child("Feedback")
                                        .child(timestamp).child("date")
                                        .setValue(date);

                                reference.child("Feedback")
                                        .child(timestamp).child("time")
                                        .setValue(time);

                                reference.child("Feedback")
                                        .child(timestamp).child("food")
                                        .setValue(text);

                                Intent i = new Intent(FeedbackActivity.this, orderHistory.class);
                                startActivity(i);

                                Toast.makeText(FeedbackActivity.this,
                                        "Thank you for your feedback!", Toast.LENGTH_LONG).show();
                            }

                          /*  reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                                }
                            });*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } //Onclick
        });
        init();
        loadOrderFromFirebase();
    }

    private void loadOrderFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference1 = database.getReference("Users")
                .child(firebaseAuth.getUid()).child("Feedback");

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot feedbackSnapshot : snapshot.getChildren()) {
                        FeedbackModel feedbackModel = feedbackSnapshot.getValue(FeedbackModel.class);
                        feedbackModel.setKey(feedbackSnapshot.getKey());
                        feedbackModels.add(feedbackModel);

                    }
                    feedbackLoadListener.onFeedbackLoadSuccess(feedbackModels);
                } else {
                    Toast.makeText(FeedbackActivity.this,
                            "No Feedback Given.", Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        text = parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}