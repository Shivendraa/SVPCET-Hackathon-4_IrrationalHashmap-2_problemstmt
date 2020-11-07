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

public class AuthorityRegistration extends AppCompatActivity {
    private TextInputLayout autEmail, autName, autPass, autRepass, autUserName;
    private boolean doubleBackToExitPressedOnce = false;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authority_registration);

        autEmail = findViewById(R.id.autEmail);
        autName = findViewById(R.id.autName);
        autPass = findViewById(R.id.autPass);
        autRepass = findViewById(R.id.autRepass);
        autUserName = findViewById(R.id.autUName);
        Button newLogin = findViewById(R.id.autNewLogin);
        Button btnRegister = findViewById(R.id.autBtnRegister);

        newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthorityRegistration.this, AuthLogin.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sname = Objects.requireNonNull(autName.getEditText()).getText().toString();
                String semail = Objects.requireNonNull(autEmail.getEditText()).getText().toString();
                String suname = Objects.requireNonNull(autUserName.getEditText()).getText().toString();
                String spass = Objects.requireNonNull(autPass.getEditText()).getText().toString();
                String srepass = Objects.requireNonNull(autRepass.getEditText()).getText().toString();

                if(sname.isEmpty() || semail.isEmpty() || suname.isEmpty() || spass.isEmpty() || srepass.isEmpty())
                {
                    if(sname.isEmpty())
                        autName.setError("Name Can Not Be Empty");
                    if(semail.isEmpty())
                        autEmail.setError("Email can Not be Empty");
                    if(suname.isEmpty())
                        autUserName.setError("Username can Not be Empty");
                    if(spass.isEmpty())
                        autPass.setError("Password can Not be Empty");
                }
                else if(!spass.equals(srepass)){
                    autRepass.setError("Password do not match!!");
                }
                else {
                    Intent intent = new Intent(AuthorityRegistration.this, AuthRegNextStep.class);
                    intent.putExtra("AuthUserName",suname);
                    intent.putExtra("AuthName",sname);
                    intent.putExtra("AuthEmail",semail);
                    intent.putExtra("AuthPass",spass);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(AuthorityRegistration.this, HomeActivity.class);
            startActivity(intent);
            finish();
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