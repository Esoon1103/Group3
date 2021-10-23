package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackPage extends AppCompatActivity {

    EditText txtFeedback;
    Button SubmitFeedback;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        SubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        txtFeedback = findViewById(R.id.etFeedback);

        SubmitFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                //Get all the values
                String feedback = txtFeedback.getText().toString();

                UserHelperClass helperClass = new UserHelperClass();

                reference.setValue(helperClass);
            }
        });
     }
    }


