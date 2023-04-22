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
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.ActivityOrgDetailsBinding;

public class OrgDetails extends AppCompatActivity {
ActivityOrgDetailsBinding binding;
FirebaseDatabase firebaseDatabase;
FirebaseAuth auth;
FirebaseStorage firebaseStorage;
RadioButton rbVerify;
String orgimage = "empty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrgDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        Log.d("SachinKadian","ye orgDetails open huii...");
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
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etOrgName.getText().toString().trim().isEmpty()){
                    binding.tilUsername.setError("Org. name is Required !");
                    return;
                }
                if(binding.rbYes.isChecked()){
                    if(binding.etRegNo.getText().toString().trim().isEmpty()){
                        binding.tilRegNo.setError("Registration no. is Required !");
                        return;
                    }
                }
                if(binding.etOrgMobile.getText().toString().trim().isEmpty()){
                    binding.tilMobile.setError("Mobile No. is Required !");
                    return;
                }
                if((binding.etOrgMobile.getText().toString().trim()).length() != 10){
                    binding.tilMobile.setError("Not a valid no. !");
                    return;
                }
                if(binding.etOrgEmail.getText().toString().trim().isEmpty()){
                    binding.tilEmail.setError("Email is Required !");
                    return;
                }
                if(binding.etOrgAddress.getText().toString().trim().isEmpty()){
                    binding.tilAddress.setError("Address is Required !");
                    return;
                }
                if(binding.etOrgCity.getText().toString().trim().isEmpty()){
                    binding.tilCity.setError("City Name is Required !");
                    return;
                }
                if((binding.etOrgDescription.getText().toString().trim()).length() <= 20){
                    binding.tilDescription.setError("Min. characters required 20");
                    return;
                }
                int selectedId = binding.radioGroup2.getCheckedRadioButtonId();
                rbVerify = findViewById(selectedId);
                if(binding.rbYes.isChecked()){
                    Organisation organisation = new Organisation(binding.etOrgName.getText().toString(),true,binding.etRegNo.getText().toString(),binding.etOrgMobile.getText().toString(),binding.etOrgEmail.getText().toString(),binding.etOrgAddress.getText().toString(),binding.etOrgCity.getText().toString(),auth.getUid(),orgimage,binding.etOrgDescription.getText().toString().trim());
                    firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).setValue(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(OrgDetails.this,OrgFounderDetails.class));
//                            finish();
                        }
                    });
                }
                else{
                    Organisation organisation = new Organisation(binding.etOrgName.getText().toString(),false,binding.etOrgMobile.getText().toString(),binding.etOrgEmail.getText().toString(),binding.etOrgAddress.getText().toString(),binding.etOrgCity.getText().toString(), auth.getUid(),orgimage,binding.etOrgDescription.getText().toString().trim());
                    firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).setValue(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(OrgDetails.this,OrgFounderDetails.class));
//                            finish();
                        }
                    });
                }
                firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").child("name").setValue("Sachin Kadian");
            }
        });
        Log.d("SachinKadian", "last line of orgDetails");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String id = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(id).removeValue();
        Log.d("SachinKadian","onDestroy invoked 12312123132");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(auth.getUid()).child("OrganisationImage");
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                            Organisation organisation = new Organisation();
//                            organisation.setOrgPic(uri.toString());
                            orgimage = uri.toString();
//                            firebaseDatabase.getReference().child("Organisation").child(auth.getUid()).child("orgPic").setValue(organisation.orgPic);//.child("profilepic").setValue(uri.toString());
                            Toast.makeText(OrgDetails.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}