package com.hackuniv.daanveer;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityDeliveryModeBinding;

import java.util.Date;

public class DeliveryMode extends AppCompatActivity {
    ActivityDeliveryModeBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    RadioButton rbMode;
    Donation donation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase.getReference().child("Donation").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donation=snapshot.getValue(Donation.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        String recieveOrgId = getIntent().getStringExtra("orgId");
//        String testing = getIntent().getStringExtra("test");
//        if(testing.equals("103"))
//            firebaseDatabase.getReference().child("Donation").child(auth.getUid()).child("organinsation").setValue(recieveOrgId);

            binding.rbByOrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.byself.setVisibility(GONE);
                    binding.byorg.setVisibility(View.VISIBLE);
                }
            });

            binding.rbBySelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.byself.setVisibility(View.VISIBLE);
                binding.byorg.setVisibility(GONE);
            }
            });

        binding.btnSaveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = binding.radioGroup2.getCheckedRadioButtonId();
                rbMode = findViewById(selectedId);
                firebaseDatabase.getReference().child("Donation").child(auth.getUid()).child("mode").setValue(rbMode.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        donation.setTimestamp(new Date().getTime());
                        firebaseDatabase.getReference().child("UsersDonation").child(auth.getUid()).push().setValue(donation);
                        Toast.makeText(DeliveryMode.this, "Your donation is registered!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DeliveryMode.this,DonatorActivity.class));
                        finish();
                    }
                });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}