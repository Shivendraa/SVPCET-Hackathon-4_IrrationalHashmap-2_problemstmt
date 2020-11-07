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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserRegNextStep extends AppCompatActivity {
    private TextInputLayout contact, addr1, addr2, city, etState, pincode;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
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

                String suname = getIntent().getStringExtra("userName");
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("DataStore");
                String spincode = Objects.requireNonNull(pincode.getEditText()).getText().toString();
                String scontact = Objects.requireNonNull(contact.getEditText()).getText().toString();
                String saddr1 = Objects.requireNonNull(addr1.getEditText()).getText().toString();
                String saddr2 = Objects.requireNonNull(addr2.getEditText()).getText().toString();
                String scity = Objects.requireNonNull(city.getEditText()).getText().toString();
                String state = stateDd.getText().toString();

                if(scontact.isEmpty() || saddr1.isEmpty() || saddr2.isEmpty() || scity.isEmpty() || spincode.isEmpty() || state.isEmpty())
                {
                    if(scontact.isEmpty())
                        contact.setError("Contact Can Not Be Empty");
                    if(saddr1.isEmpty())
                        addr1.setError("Address can Not be Empty");
                    if(scity.isEmpty())
                        city.setError("City can Not be Empty");
                    if(state.isEmpty())
                        etState.setError("State can Not be Empty");
                    if(spincode.isEmpty())
                        pincode.setError("Post Code can Not be Empty");
                }
                else {
                    UserHelper user = new UserHelper(scontact,saddr1,saddr2,scity,state,spincode);
                    reference.child("Users").child(suname).setValue(user);
                    Intent intent = new Intent(UserRegNextStep.this, UserDashBoard.class);
                    intent.putExtra("userName",suname);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}