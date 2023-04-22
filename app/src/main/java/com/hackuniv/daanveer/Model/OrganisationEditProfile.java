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
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityOrganisationEditProfileBinding;
import com.squareup.picasso.Picasso;

public class OrganisationEditProfile extends AppCompatActivity {
    ActivityOrganisationEditProfileBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    RadioButton rbVerify;
    String orgimage = "";
    OrgFounder orgFounder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrganisationEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Organsiation");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        database.getReference().child("Organisation").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Organisation organisation = snapshot.getValue(Organisation.class);
                binding.etOrgUserName.setText(organisation.getOrgName());
                binding.etOrgMobile.setText(organisation.getOrgMobile());
                binding.etOrgEmail.setText(organisation.getOrgEmail());
                binding.etOrgAddress.setText(organisation.getOrgAddress());
                binding.etOrgCity.setText(organisation.getOrgCity());
                binding.etOrgDescription.setText(organisation.getOrgDsc());
                Picasso.get().load(organisation.getOrgPic()).placeholder(R.drawable.profileuser).into(binding.profileImageOrg);
                orgimage = organisation.getOrgPic();
                if(organisation.getVerifed() == false){
                    binding.radioGroup2.check(R.id.rbNo);
                    binding.tilRegNo.setVisibility(View.GONE);
                }else{
                    binding.radioGroup2.check(R.id.rbYes);
                    binding.tilRegNo.setVisibility(View.VISIBLE);
                    binding.etRegNo.setText(organisation.getOrgRegNo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orgFounder = snapshot.getValue(OrgFounder.class);
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
        binding.rbYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tilRegNo.setVisibility(View.VISIBLE);

            }
        });
        binding.rbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tilRegNo.setVisibility(View.GONE);
            }
        });
        binding.btnFounderEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganisationEditProfile.this,FounderEditProfile.class));
            }
        });
        binding.btnSaveVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etOrgUserName.getText().toString().trim().isEmpty()){
                    binding.tilOrgUsername.setError("Org. name is Required !");
                    return;
                }
                if(binding.rbYes.isChecked()){
                    if(binding.etRegNo.getText().toString().trim().isEmpty()){
                        binding.tilRegNo.setError("Registration no. is Required !");
                        return;
                    }
                }
                if(binding.etOrgMobile.getText().toString().trim().isEmpty()){
                    binding.tilOrgMobile.setError("Mobile No. is Required !");
                    return;
                }
                if((binding.etOrgMobile.getText().toString().trim()).length() != 10){
                    binding.tilOrgMobile.setError("Not a valid no. !");
                    return;
                }
                if(binding.etOrgEmail.getText().toString().trim().isEmpty()){
                    binding.tilOrgEmail.setError("Email is Required !");
                    return;
                }
                if(binding.etOrgAddress.getText().toString().trim().isEmpty()){
                    binding.tilOrgAddress.setError("Address is Required !");
                    return;
                }
                if(binding.etOrgCity.getText().toString().trim().isEmpty()){
                    binding.tilOrgCity.setError("City Name is Required !");
                    return;
                }
                if((binding.etOrgDescription.getText().toString().trim()).length() <= 20){
                    binding.tilDescription.setError("Min. characters required 20");
                    return;
                }
                int selectedId = binding.radioGroup2.getCheckedRadioButtonId();
                rbVerify = findViewById(selectedId);
                if(binding.rbYes.isChecked()){
                    Organisation organisation = new Organisation(binding.etOrgUserName.getText().toString(),true,binding.etRegNo.getText().toString(),binding.etOrgMobile.getText().toString(),binding.etOrgEmail.getText().toString(),binding.etOrgAddress.getText().toString(),binding.etOrgCity.getText().toString(),auth.getUid(),orgimage,orgFounder,binding.etOrgDescription.getText().toString().trim());
                    database.getReference().child("Organisation").child(auth.getUid()).setValue(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(OrganisationEditProfile.this,FounderEditProfile.class));
//                            finish();
                        }
                    });
                }
                else{
                    Organisation organisation = new Organisation(binding.etOrgUserName.getText().toString(),false,binding.etOrgMobile.getText().toString(),binding.etOrgEmail.getText().toString(),binding.etOrgAddress.getText().toString(),binding.etOrgCity.getText().toString(), auth.getUid(),orgimage,orgFounder,binding.etOrgDescription.getText().toString().trim());
                    database.getReference().child("Organisation").child(auth.getUid()).setValue(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(OrganisationEditProfile.this,FounderEditProfile.class));
//                            finish();
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri sFile = data.getData();
            binding.profileImageOrg.setImageURI(sFile);

            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid()).child("OrganisationImage");
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            orgimage = uri.toString();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("Organisation").child(auth.getUid()).child("orgPic").setValue(uri.toString());//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(OrganisationEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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