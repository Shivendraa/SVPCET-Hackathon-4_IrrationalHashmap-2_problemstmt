package com.nighteye.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

public class AuthRegNextStep extends AppCompatActivity {
    private TextInputLayout autContact, autPos, autDoctype, autCity;
    EditText autDocPic;
    private  Uri filePath1;
    private static final int PICK_IMAGE_REQUEST = 234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authreg_nextstep);

        autContact = findViewById(R.id.autContact);
        autPos = findViewById(R.id.autPos);
        autDoctype = findViewById(R.id.etAutDocType);
        autCity = findViewById(R.id.autCity);

        String[] docTypes = new String[] {"PAN Card", "Aadhar Card", "Voter ID", "Recommendation from Mayor", "Recommendation from State Govt", "Recommendation from Central Govt" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, docTypes);
        final AutoCompleteTextView docTypeDd = findViewById(R.id.autDoctypeDd);
        docTypeDd.setAdapter(adapter);

        String[] states = new String[] {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana",  "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Nagaland", "Odisha",  "Punjab","Rajasthan", "Tamil Nadu",
                "Telangana", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.list_item, states);
        final AutoCompleteTextView stateDd = findViewById(R.id.autStateDd);
        stateDd.setAdapter(adapter2);

        autDocPic = findViewById(R.id.autDocPic);
        autDocPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
            }
        });


        Button btnToDB = findViewById(R.id.autBtnToDB);
        btnToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = stateDd.getText().toString();

                Intent intent = new Intent(AuthRegNextStep.this, AuthDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && Objects.requireNonNull(data).getData() != null) {
            filePath1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                autDocPic.setText(filePath1.getEncodedPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}