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
import com.hackuniv.daanveer.DonatorActivity;
import com.hackuniv.daanveer.DonatorIntro;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityDonatorEditProfileBinding;
import com.squareup.picasso.Picasso;

public class DonatorEditProfile extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    RadioButton rbGender;
    RadioButton rbVolunter;
    ActivityDonatorEditProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonatorEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        database.getReference().child("Donators").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Donators donators = snapshot.getValue(Donators.class);
                binding.etUserName.setText(donators.getUsername());
                Picasso.get().load(donators.getProfileImage()).placeholder(R.drawable.profileuser).into(binding.profileImageDonator);
                binding.etMobile.setText(donators.getMobile());
                binding.etAge.setText(donators.getAge());
                        if(donators.getGender().equals("Male"))
                            binding.radioGroup.check(R.id.rbMale);
                        else if(donators.getGender().equals("Female"))
                            binding.radioGroup.check(R.id.rbFemale);
                        else
                            binding.radioGroup.check(R.id.rbOthers);
                binding.etAddress.setText(donators.getAddress());
                binding.etEmail.setText(donators.getEmail());
                binding.etCity.setText(donators.getCity());
                if(donators.getVolunteer().equals("NO"))
                    binding.radioGroup2.check(R.id.rbNo);
                else
                    binding.radioGroup2.check(R.id.rbYes);
//                Toast.makeText(getContext(), "Info Updated!!", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        binding.btnSaveVerify.setOnClickListener(new View.OnClickListener() {
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
                final String[] image = {new String()};
                final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Donators donators = new Donators();
//                        donators.setProfileImage(uri.toString());
//                        image[0] = uri.toString();
                        database.getReference().child("Donators").child(auth.getUid()).child("profileImage").setValue(uri.toString());//.child("profilepic").setValue(uri.toString());
                    }
                });

                Donators donators = new Donators(binding.etUserName.getText().toString().trim(),binding.etMobile.getText().toString().trim(),binding.etEmail.getText().toString().trim(),
                        binding.etAddress.getText().toString().trim(),binding.etCity.getText().toString().trim(),rbGender.getText().toString(),rbVolunter.getText().toString(), binding.etAge.getText().toString().trim());
                database.getReference().child("Donators").child(auth.getUid()).setValue(donators).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DonatorEditProfile.this, "Your profile is updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DonatorEditProfile.this, DonatorActivity.class));
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
            binding.profileImageDonator.setImageURI(sFile);

            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid());
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Donators donators = new Donators();
                            donators.setProfileImage(uri.toString());
                            database.getReference().child("Donators").child(auth.getUid()).child("profileImage").setValue(uri.toString());//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(DonatorEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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