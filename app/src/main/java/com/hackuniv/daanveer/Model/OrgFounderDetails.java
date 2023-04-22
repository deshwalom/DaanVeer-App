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
import com.hackuniv.daanveer.CharityActivity;
import com.hackuniv.daanveer.DonatorIntro;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityOrgDetailsBinding;
import com.hackuniv.daanveer.databinding.ActivityOrgFounderDetailsBinding;

public class OrgFounderDetails extends AppCompatActivity {
    ActivityOrgFounderDetailsBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    RadioButton rbGender;
    String founderimage = "empty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrgFounderDetailsBinding.inflate(getLayoutInflater());
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
                rbGender = findViewById(selectedId);
                OrgFounder orgFounder = new OrgFounder(binding.etUserName.getText().toString().trim(),founderimage,binding.etMobile.getText().toString().trim(),binding.etEmail.getText().toString().trim(),
                        binding.etAddress.getText().toString().trim(),binding.etCity.getText().toString().trim(),rbGender.getText().toString(), binding.etAge.getText().toString().trim());
                firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").setValue(orgFounder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").setValue("1");
//                        Toast.makeText(OrgFounderDetails.this, "Your profile is created!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrgFounderDetails.this, CharityActivity.class));
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
//        FirebaseDatabase.getInstance().getReference().child("Organisation").child(FirebaseAuth.getInstance().getUid()).removeValue();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid()).child("OrganisationFounderImage");
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            founderimage = uri.toString();
//                            Donators donators = new Donators();
//                            donators.setProfileImage(uri.toString());
//                            firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).child("OrgFounder").child("profileImage").setValue(uri.toString());//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(OrgFounderDetails.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}