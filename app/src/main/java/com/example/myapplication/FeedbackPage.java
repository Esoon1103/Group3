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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class FeedbackPage extends AppCompatActivity {

    EditText etFeedback;
    Button SubmitFeedback;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        etFeedback = findViewById(R.id.etFeedback);
        SubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        //Save data in Firebase on button click
        SubmitFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance("https://intea-delight-default-rtdb.asia-southeast1.firebasedatabase.app/");

                reference = rootNode.getReference("Feedback");

                //Get the value of feedback
                String feedback = etFeedback.getText().toString();

                reference.setValue(feedback);
            }
        });
    }
}

