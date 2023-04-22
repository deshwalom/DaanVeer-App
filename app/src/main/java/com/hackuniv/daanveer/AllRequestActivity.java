package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Adapters.AllRequestAdapter;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityAllrequestBinding;

import java.util.ArrayList;

public class AllRequestActivity extends AppCompatActivity {
    ActivityAllrequestBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    ArrayList<Donation> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllrequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
//        getSupportActionBar().setDisplayShowTitleEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("All Donation");
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        AllRequestAdapter adapter = new AllRequestAdapter(list, AllRequestActivity.this);
        binding.checkingRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AllRequestActivity.this);
        binding.checkingRecyclerView.setLayoutManager(layoutManager);

        firebaseDatabase.getReference().child("Donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Donation donation = snapshot1.getValue(Donation.class);
                    donation.setDonatorId(snapshot1.getKey());
                    if(donation.getStatus().equals("Accept"))
                    list.add(donation);
                }
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