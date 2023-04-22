package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.databinding.ActivityCharityBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharityActivity extends AppCompatActivity {
    ActivityCharityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    int count = 0;
    int count1 = 0;
    BroadcastReceiver broadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Log.d("SachinKadian", "opening charityActivity opended!!!");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
        database.getReference().child("Organisation").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Organisation organisation = snapshot.getValue(Organisation.class);
                binding.orgName.setText(organisation.getOrgName());
                binding.textOrgName.setText(organisation.getOrgName());
                if(!organisation.getOrgPic().equals("empty"))
                    Picasso.get().load(organisation.getOrgPic()).placeholder(R.drawable.profileuser).into(binding.profileOrgImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = 0;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Donation donation = snapshot1.getValue(Donation.class);
                    if(donation.getStatus().equals("Accept"))
                        count++;
                }
                binding.totalDonation.setText(count + " ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count1 = 0;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Donation donation = snapshot1.getValue(Donation.class);
                    donation.setDonatorId(snapshot1.getKey());
                    if(donation.getOrganinsation().equals(auth.getUid()) && donation.getStatus().equals("Accept"))
                        count1++;
                }
                binding.totalRequests.setText(count1 + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.profileOrgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CharityActivity.this,OrgProfileActivity.class));
            }
        });
        binding.allDonationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CharityActivity.this,AllRequestActivity.class));
            }
        });
        binding.yourRequestsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CharityActivity.this,SpecificRequestActivity.class));
            }
        });
        binding.yourProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CharityActivity.this,OrgProfileActivity.class));
            }
        });
        binding.btnRecieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CharityActivity.this,InProgressActivity.class));
            }
        });
    }
    public void Internetstatus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        unregisterReceiver(broadcastReceiver);
//    }
}