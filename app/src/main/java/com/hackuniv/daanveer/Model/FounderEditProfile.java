package com.hackuniv.daanveer.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackuniv.daanveer.CharityActivity;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityFounderEditProfileBinding;
import com.squareup.picasso.Picasso;

public class FounderEditProfile extends AppCompatActivity {
    ActivityFounderEditProfileBinding binding;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    RadioButton rbGender;
    String founderimage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFounderEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Founder Details");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        database.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrgFounder orgFounder = snapshot.getValue(OrgFounder.class);
                binding.etUserName.setText(orgFounder.getUsername());
                Picasso.get().load(orgFounder.getProfileImage()).placeholder(R.drawable.profileuser).into(binding.profileImage);
                founderimage = orgFounder.getProfileImage();
                binding.etMobile.setText(orgFounder.getMobile());
                binding.etAge.setText(orgFounder.getAge());
                if(orgFounder.getGender().equals("Male"))
                    binding.radioGroup.check(R.id.rbMale);
                else if(orgFounder.getGender().equals("Female"))
                    binding.radioGroup.check(R.id.rbFemale);
                else
                    binding.radioGroup.check(R.id.rbOthers);
                binding.etAddress.setText(orgFounder.getAddress());
                binding.etEmail.setText(orgFounder.getEmail());
                binding.etCity.setText(orgFounder.getCity());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                database.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").setValue(orgFounder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").setValue("1");
//                        Toast.makeText(OrgFounderDetails.this, "Your profile is created!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FounderEditProfile.this, CharityActivity.class));
                        finish();
                    }
                });



            }
        });


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
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").child("profileImage").setValue(uri.toString());//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(FounderEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}