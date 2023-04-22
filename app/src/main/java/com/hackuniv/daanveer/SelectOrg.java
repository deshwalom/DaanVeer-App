package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Adapters.OrgAdapter;
import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.databinding.ActivitySelectOrgBinding;

import java.util.ArrayList;

public class SelectOrg extends AppCompatActivity {
    ActivitySelectOrgBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    ArrayList<Organisation> list = new ArrayList<>();
    BroadcastReceiver broadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
        binding.rbSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rvOrgList.setVisibility(View.VISIBLE);
                binding.nextbutton.setVisibility(View.GONE);
            }
        });
        binding.rbAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rvOrgList.setVisibility(View.GONE);
                binding.nextbutton.setVisibility(View.VISIBLE);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        OrgAdapter orgAdapter = new OrgAdapter(list,SelectOrg.this);
        binding.rvOrgList.setAdapter(orgAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectOrg.this);
        binding.rvOrgList.setLayoutManager(layoutManager);

        //getting data from firebase
        firebaseDatabase.getReference().child("Organisation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Organisation organisation = snapshot1.getValue(Organisation.class);
                    organisation.setOrgId(snapshot1.getKey());
                    list.add(organisation);
                }
                orgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.nextbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firebaseDatabase.getReference().child("Donation").child(FirebaseAuth.getInstance().getUid()).child("orgName").setValue("Anyone");
                firebaseDatabase.getReference().child("Donation").child(auth.getUid()).child("organinsation").setValue("any").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(SelectOrg.this,DeliveryMode.class));
                        finish();
                    }

                });

            }
        });


    }
    public void Internetstatus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}