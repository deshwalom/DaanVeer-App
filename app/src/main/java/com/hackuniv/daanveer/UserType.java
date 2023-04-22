package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.DonatorDetails;
import com.hackuniv.daanveer.Model.OrgDetails;
import com.hackuniv.daanveer.Model.Users;
import com.hackuniv.daanveer.databinding.ActivityCharityBinding;
import com.hackuniv.daanveer.databinding.ActivityUserTypeBinding;

public class UserType extends AppCompatActivity {
    ActivityUserTypeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String check;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        Log.d("SachinKadian","wlcm to userType bro");

//        Toast.makeText(this, "Welcome to UserType", Toast.LENGTH_LONG).show();
//        FirebaseUser user = auth.getCurrentUser();
//        String uid = user.getUid();
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("UserId").setValue(uid);

//        progressDialog = new ProgressDialog(UserType.this);
//        progressDialog.setTitle("Fetching Data");
//        progressDialog.setMessage("We're fetching your details..");
//        progressDialog.show();
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String check = snapshot.getValue(String.class);
//                Log.d("Checkin",check);
//                if(check.equals("1")){
//                    progressDialog.dismiss();
//                    startActivity(new Intent(UserType.this,DonatorActivity.class));
//                    finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void openDonator(View view){
        FirebaseUser user = auth.getCurrentUser();
        Users users = new Users(FirebaseAuth.getInstance().getUid(),"Donator", user.getPhoneNumber());
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).setValue(users);
        startActivity(new Intent(this, DonatorDetails.class));
//        finish();

    }
    public void openCharity(View view){
        Log.d("SachinKadian", "charity clicked!");
        FirebaseUser user = auth.getCurrentUser();
        Users users = new Users(FirebaseAuth.getInstance().getUid(),"Organizer", user.getPhoneNumber());
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).setValue(users);

        startActivity(new Intent(this, OrgDetails.class));
//        finish();
    }

}