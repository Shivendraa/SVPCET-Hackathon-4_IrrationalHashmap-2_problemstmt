package com.nighteye.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserRegistration extends AppCompatActivity {
    private TextInputLayout email, name, pass, repass, userName;
    private boolean doubleBackToExitPressedOnce = false;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_registration);

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        userName = findViewById(R.id.userName);
        Button newLogin = findViewById(R.id.newLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistration.this, UserLogin.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("DataStore");
                String sname = Objects.requireNonNull(name.getEditText()).getText().toString();
                String semail = Objects.requireNonNull(email.getEditText()).getText().toString();
                String suname = Objects.requireNonNull(userName.getEditText()).getText().toString();
                String spass = Objects.requireNonNull(pass.getEditText()).getText().toString();
                String srepass = Objects.requireNonNull(repass.getEditText()).getText().toString();

                if(sname.isEmpty() || semail.isEmpty() || suname.isEmpty() || spass.isEmpty() || srepass.isEmpty())
                {
                    if(sname.isEmpty())
                        name.setError("Name Can Not Be Empty");
                    if(semail.isEmpty())
                        email.setError("Email can Not be Empty");
                    if(suname.isEmpty())
                        userName.setError("Username can Not be Empty");
                    if(spass.isEmpty())
                        pass.setError("Password can Not be Empty");
                }
                else if(!spass.equals(srepass)){
                    repass.setError("Password do not match!!");
                }
                else {
                    UserHelper user = new UserHelper(semail,sname,suname,spass);
                    reference.child("Users").child(suname).child("Basic Details").setValue(user);
                    Intent intent = new Intent(UserRegistration.this, UserRegNextStep.class);
                    intent.putExtra("userName",suname);
                    startActivity(intent);
                    finish();
                }
            }
        });
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
}