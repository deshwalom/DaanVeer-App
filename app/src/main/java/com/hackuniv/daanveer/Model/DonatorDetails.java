package com.hackuniv.daanveer.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackuniv.daanveer.DonatorIntro;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityDonatorDetailsBinding;

public class DonatorDetails extends AppCompatActivity {
    ActivityDonatorDetailsBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    RadioButton rbGender;
    RadioButton rbVolunter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonatorDetailsBinding.inflate(getLayoutInflater())   ;
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        binding.textEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //for any type "*/*"
                startActivityForResult(intent,33);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etUserName.getText().toString().trim().isEmpty()){
                    binding.tilUsername.setError("Username is Required !");
                    return;
                }
                if(binding.etAge.getText().toString().trim().isEmpty()){
                    binding.tilAge.setError("Age is Required !");
                    return;
                }
                if(binding.etMobile.getText().toString().trim().isEmpty()){
                    binding.tilMobile.setError("Mobile No. is Required !");
                    return;
                }
                if((binding.etMobile.getText().toString().trim()).length() != 10){
                    binding.tilMobile.setError("Not a valid no. !");
                    return;
                }
                if(binding.etEmail.getText().toString().trim().isEmpty()){
                    binding.tilEmail.setError("Email is Required !");
                    return;
                }
                if(binding.etAddress.getText().toString().trim().isEmpty()){
                    binding.tilAddress.setError("Address is Required !");
                    return;
                }
                if(binding.etCity.getText().toString().trim().isEmpty()){
                    binding.tilCity.setError("City Name is Required !");
                    return;
                }
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                int selectedId2 = binding.radioGroup2.getCheckedRadioButtonId();
                rbGender = findViewById(selectedId);
                rbVolunter = findViewById(selectedId2);
                Donators donators = new Donators(binding.etUserName.getText().toString().trim(),binding.etMobile.getText().toString().trim(),binding.etEmail.getText().toString().trim(),
                        binding.etAddress.getText().toString().trim(),binding.etCity.getText().toString().trim(),rbGender.getText().toString(),rbVolunter.getText().toString(), binding.etAge.getText().toString().trim());
                firebaseDatabase.getReference().child("Donators").child(auth.getUid()).setValue(donators).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").setValue("1");
                        Toast.makeText(DonatorDetails.this, "Your profile is created!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DonatorDetails.this, DonatorIntro.class));
                        finish();
                    }
                });



            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SachinKadian","onDestroy invoked 12312123132");
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).removeValue();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid());
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(DonatorDetails.this, "Done...", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Donators donators = new Donators();
                            donators.setProfileImage(uri.toString());
                            firebaseDatabase.getReference().child("Donators").child(auth.getUid()).child("profileImage").setValue(donators.profileImage);//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(DonatorDetails.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}

