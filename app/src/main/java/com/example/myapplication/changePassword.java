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

    //Button forgotPass;
    Button changePass;
    EditText newPass;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
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
                Intent toLogin3 = new Intent(this, cart.class);
                startActivity(toLogin3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        dialog = new ProgressDialog(this);

        newPass = (EditText)findViewById(R.id.new_password);

        changePass = findViewById(R.id.btnConfirmChangePass);
        /*forgotPass = findViewById(R.id.btnForgotPass);

         forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePassword.this, ForgotPasswordActivity.class));
            }
        }); */

            firebaseAuth = FirebaseAuth.getInstance();

            changePass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    dialog.setMessage("Changing password, Please wait");
                    dialog.show();
                    user.updatePassword(newPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        dialog.dismiss();
                                        Toast.makeText(changePassword.this,
                                                "Password has been changed", Toast.LENGTH_LONG).show();
                                        firebaseAuth.signOut();
                                        finish();
                                        Intent i =new Intent(changePassword.this, LoginActivity.class);
                                        startActivity(i);
                                    } else{
                                        dialog.dismiss();
                                        Toast.makeText(changePassword.this,
                                                "No input detected.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
          });

    } //onCreate
} //End class
