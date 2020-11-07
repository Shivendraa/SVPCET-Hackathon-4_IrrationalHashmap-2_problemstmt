package com.nighteye.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserLogin extends AppCompatActivity {
    private TextInputLayout uName,uPass;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView forgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        uName = findViewById(R.id.username);
        uPass = findViewById(R.id.password);
        Button newUser = findViewById(R.id.newUser);
        Button btnLogin = findViewById(R.id.btnLogin);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private Boolean ValidateUsername(){
        String val = Objects.requireNonNull(uName.getEditText()).getText().toString();
        if(val.isEmpty()){
            uName.setError("Username Cannot be empty!");
            return false;
        }
        else {
            uName.setError(null);
            uName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean ValidatePassword(){
        String val = Objects.requireNonNull(uPass.getEditText()).getText().toString();
        if(val.isEmpty()){
            uPass.setError("Password Cannot be empty!");
            return false;
        }
        else {
            uPass.setError(null);
            uPass.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(){
        if(!ValidateUsername() | !ValidatePassword()){
            return;
        }
        else{
            isUser();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void isUser(){
        final String userEnteredUsername = Objects.requireNonNull(uName.getEditText()).getText().toString();
        final String userEnteredPassword = Objects.requireNonNull(uPass.getEditText()).getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DataStore").child("Users").child(userEnteredUsername);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    uPass.setError(null);
                    uPass.setErrorEnabled(false);
                    String emailFromDB = Objects.requireNonNull(dataSnapshot.child("Basic Details").child("email").getValue()).toString();
                    String passFromDB = Objects.requireNonNull(dataSnapshot.child("Basic Details").child("password").getValue()).toString();
                    String nameFromDB = Objects.requireNonNull(dataSnapshot.child("Basic Details").child("name").getValue()).toString();
                    if(userEnteredPassword.equals(passFromDB)) {
                        Intent intent = new Intent(getApplicationContext(),UserDashBoard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("mail",emailFromDB);
                        intent.putExtra("uname",userEnteredUsername);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        uPass.setError("Username and Password do not match!");
                    }
                }
                else {
                    uName.setError("No Such Username exists!");
                    uName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}