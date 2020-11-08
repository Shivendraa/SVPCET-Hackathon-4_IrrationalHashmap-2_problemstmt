package com.nighteye.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    private String username;
    private ImageView backButton;
    private EditText profName, profEmail, profUser, profContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        username = getIntent().getStringExtra("username");

        backButton = findViewById(R.id.back_icon2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, UserDashBoard.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        profName = findViewById(R.id.profName);
        profUser = findViewById(R.id.profUsername);
        profContact = findViewById(R.id.profContact);
        profEmail = findViewById(R.id.profEmail);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DataStore").child("Users").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String emailFromDB = Objects.requireNonNull(dataSnapshot.child("Basic Details").child("email").getValue()).toString();
                    String contactFromDB = Objects.requireNonNull(dataSnapshot.child("Address").child("contact").getValue()).toString();
                    String nameFromDB = Objects.requireNonNull(dataSnapshot.child("Basic Details").child("name").getValue()).toString();

                    profName.setText(nameFromDB);
                    profContact.setText(contactFromDB);
                    profEmail.setText(emailFromDB);
                    profUser.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}