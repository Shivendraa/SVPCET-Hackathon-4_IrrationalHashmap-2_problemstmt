package com.nighteye.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class UserRegNextStep extends AppCompatActivity {
    private TextInputLayout contact, addr1, addr2, city, etState, pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_userreg_nextstep);

        contact = findViewById(R.id.contact);
        addr1 = findViewById(R.id.addr1);
        addr2 = findViewById(R.id.addr2);
        city = findViewById(R.id.city);
        etState = findViewById(R.id.etState);
        pincode = findViewById(R.id.pincode);

        String[] states = new String[] {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana",  "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Nagaland", "Odisha",  "Punjab","Rajasthan", "Tamil Nadu",
                "Telangana", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, states);
        final AutoCompleteTextView stateDd = findViewById(R.id.stateDd);
        stateDd.setAdapter(adapter);

        Button btnToDB = findViewById(R.id.btnToDB);
        btnToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = stateDd.getText().toString();

                Intent intent = new Intent(UserRegNextStep.this, UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}