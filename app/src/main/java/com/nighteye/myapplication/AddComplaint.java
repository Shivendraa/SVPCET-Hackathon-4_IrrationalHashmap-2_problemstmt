package com.nighteye.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class AddComplaint extends AppCompatActivity {
    private ImageView backButton;
    private TextInputLayout complaintInfo, etCompCategory;
    private EditText dateOccur, compImage;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath1;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private StorageReference mStorageRef;
    private String username;
    private int complaintId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_complaint);

        username = getIntent().getStringExtra("username");

        backButton = findViewById(R.id.back_icon);
        complaintInfo = findViewById(R.id.complaintInfo);
        etCompCategory = findViewById(R.id.etCompCategory);
        dateOccur = findViewById(R.id.dateOccur);
        compImage = findViewById(R.id.probImage);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String[] docTypes = new String[] {"Electricity Problem", "Water Problem", "Road Problem", "Waste Management Problem", "Bridge/Garden/Other Construction", "Others" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, docTypes);
        final AutoCompleteTextView compCategoryDd = findViewById(R.id.compCategoryDd);
        compCategoryDd.setAdapter(adapter);

        dateOccur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddComplaint.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = day+"/"+(month+1)+"/"+year;
                        if(!date.isEmpty())
                            dateOccur.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        compImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddComplaint.this, UserDashBoard.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        Button submit = findViewById(R.id.submitComplaint);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("DataStore");
                mStorageRef = FirebaseStorage.getInstance().getReference();

                String compCategory = compCategoryDd.getText().toString();
                String compInfo = Objects.requireNonNull(complaintInfo.getEditText()).getText().toString();
                String compDate = dateOccur.getText().toString();
                String compResolved = "No";
                complaintId =  new Random().nextInt();

                if(!(compCategory.isEmpty() && compInfo.isEmpty() && compDate.isEmpty())){
                    uploadFile();
                    UserHelper user = new UserHelper(compCategory,compInfo,compDate,compResolved,username);
                    reference.child("Complaints").child(complaintId+"").setValue(user);
                    Intent intent = new Intent(AddComplaint.this, AddComplaint.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void uploadFile()
    {
        if(filePath1 != null){
            StorageReference riversRef1 = mStorageRef.child("Complaint Docs/"+complaintId+".jpg");
            riversRef1.putFile(filePath1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),"Image not selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && Objects.requireNonNull(data).getData() != null) {
            filePath1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                compImage.setText(filePath1.getEncodedPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}