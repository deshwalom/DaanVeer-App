package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Adapters.AllRequestAdapter;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityAllrequestBinding;
import com.hackuniv.daanveer.databinding.ActivitySpecificRequestBinding;

import java.util.ArrayList;

public class SpecificRequestActivity extends AppCompatActivity {
    ActivitySpecificRequestBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    ArrayList<Donation> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySpecificRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Your Request");
        Log.d("SachinKadian","specific activity");
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        AllRequestAdapter adapter = new AllRequestAdapter(list, SpecificRequestActivity.this);
        binding.specificRequestRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SpecificRequestActivity.this);
        binding.specificRequestRecyclerView.setLayoutManager(layoutManager);

        firebaseDatabase.getReference().child("Donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Donation donation = snapshot1.getValue(Donation.class);
                    donation.setDonatorId(snapshot1.getKey());
                    Log.d("SachinKadian","specific activity"+donation.getOrganinsation());
                    Log.d("SachinKadian","specific activity"+auth.getUid());
                    if(donation.getOrganinsation().equals(auth.getUid()) && donation.getStatus().equals("Accept"))
                        list.add(donation);
                }
                Log.d("SachinKadian","specific activity"+list.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}