package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.FounderEditProfile;
import com.hackuniv.daanveer.Model.OrgFounder;
import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.Model.OrganisationEditProfile;
import com.hackuniv.daanveer.databinding.ActivityOrgProfileBinding;
import com.squareup.picasso.Picasso;

public class OrgProfileActivity extends AppCompatActivity {
    ActivityOrgProfileBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrgProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Your Profile");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        database.getReference().child("Organisation").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Organisation organisation = snapshot.getValue(Organisation.class);
                binding.orgnameprofile.setText(organisation.getOrgName());
//                OrgFounder orgFounder = organisation.getOrgFounder();
//                Log.d("SachinKadian",organisation.getOrgFounder().getUsername());
//                binding.orgfoundernameprofile.setText(orgFounder.getUsername());
                binding.orgmobiletv.setText(organisation.getOrgMobile());
                binding.orgemailtv.setText(organisation.getOrgEmail());
                binding.orgcitytv.setText(organisation.getOrgCity());
                if(!organisation.getOrgPic().equals("empty"))
                    Picasso.get().load(organisation.getOrgPic()).placeholder(R.drawable.profileuser).into(binding.editOrgProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Organisation").child(auth.getUid()).child("orgFounder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrgFounder orgFounder = snapshot.getValue(OrgFounder.class);
                binding.orgfoundernameprofile.setText(orgFounder.getUsername());
//                binding.orgfoundernameprofile.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.tveditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrgProfileActivity.this, OrganisationEditProfile.class));

            }
        });
        binding.aboutcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrgProfileActivity.this, AboutActivity.class));
            }
        });
        binding.helpcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrgProfileActivity.this, HelpActivity.class));
            }
        });
        binding.logoutcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OrgProfileActivity.this, SignUpPhoneNumber.class));
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}