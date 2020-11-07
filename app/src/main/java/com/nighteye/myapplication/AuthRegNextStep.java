package com.nighteye.myapplication;

import androidx.annotation.NonNull;
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
import java.util.Objects;

public class AuthRegNextStep extends AppCompatActivity {
    private TextInputLayout autContact, autPos, autDoctype, autCity, autState;

    EditText autDocPic;
    private  Uri filePath1;
    private static final int PICK_IMAGE_REQUEST = 234;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private StorageReference mStorageRef;
    private String sauthusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authreg_nextstep);

        autContact = findViewById(R.id.autContact);
        autPos = findViewById(R.id.autPos);
        autDoctype = findViewById(R.id.etAutDocType);
        autCity = findViewById(R.id.autCity);
        autState = findViewById(R.id.etAutState);

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

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("DataStore");
                mStorageRef = FirebaseStorage.getInstance().getReference();

                sauthusername = getIntent().getStringExtra("AuthUserName");
                String sauthname = getIntent().getStringExtra("AuthName");
                String sauthemail = getIntent().getStringExtra("AuthEmail");
                String sauthpass = getIntent().getStringExtra("AuthPass");

                String sauthcontact = Objects.requireNonNull(autContact.getEditText()).getText().toString();
                String sauthposition = Objects.requireNonNull(autPos.getEditText()).getText().toString();
                String sauthdoctype = docTypeDd.getText().toString();
                String sauthcity = Objects.requireNonNull(autCity.getEditText()).getText().toString();
                String sauthstate = stateDd.getText().toString();

                if(sauthcontact.isEmpty() || sauthposition.isEmpty() || sauthdoctype.isEmpty() || sauthcity.isEmpty() || sauthstate.isEmpty())
                {
                    if(sauthcontact.isEmpty())
                        autContact.setError("Contact Can Not Be Empty");
                    if(sauthposition.isEmpty())
                        autPos.setError("Authority position can Not be Empty");
                    if(sauthcity.isEmpty())
                        autCity.setError("City can Not be Empty");
                    if(sauthdoctype.isEmpty())
                        autDoctype.setError("State can Not be Empty");
                    if(sauthstate.isEmpty())
                        autState.setError("Post Code can Not be Empty");
                }
                else {
                    uploadFile();
                    UserHelper user = new UserHelper(sauthemail,sauthname,sauthusername,sauthpass,sauthcontact,sauthposition,sauthdoctype,sauthcity,sauthstate);
                    reference.child("Authority").child(sauthusername).child("Details").setValue(user);
                    Intent intent = new Intent(AuthRegNextStep.this, RegisterSuccess.class);
                    intent.putExtra("UserName",sauthusername);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void uploadFile()
    {
        if(filePath1 != null){
            StorageReference riversRef1 = mStorageRef.child("Authority Docs/"+sauthusername+"/AuthorizationProof.jpg");
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
                autDocPic.setText(filePath1.getEncodedPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}