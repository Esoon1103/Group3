package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassword extends AppCompatActivity implements View.OnClickListener{

    Button forgotPass;
    Button changePass;
    EditText e1;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    public void onClick(View view) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        dialog = new ProgressDialog(this);

        e1 = (EditText)findViewById(R.id.new_password);
        auth = FirebaseAuth.getInstance();

        changePass = findViewById(R.id.btnConfirmChangePass);
        forgotPass = findViewById(R.id.btnForgotPass);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePassword.this, ForgotPasswordActivity.class));
            }
        });
    } //onCreate

    public void change(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            changePass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    dialog.setMessage("Changing password, Please wait");
                    dialog.show();
                    user.updatePassword(e1.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Your password has been changed",
                                                Toast.LENGTH_LONG);
                                        auth.signOut();
                                        finish();
                                        Intent i =new Intent(changePassword.this, LoginActivity.class);
                                        startActivity(i);
                                    } else{
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Your password could not be changed",
                                                Toast.LENGTH_LONG);
                                    }
                                }
                            });
                }
          });

    }
} //End class
