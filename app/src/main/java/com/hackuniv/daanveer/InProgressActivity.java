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
import com.hackuniv.daanveer.Adapters.RejectedDonationAdapter;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityInProgressBinding;

import java.util.ArrayList;

public class InProgressActivity extends AppCompatActivity {
    ActivityInProgressBinding binding;
    ArrayList<Donation> list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("History");
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        RejectedDonationAdapter adapter=new RejectedDonationAdapter(list,InProgressActivity.this);
        binding.checkingRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(InProgressActivity.this);
        binding.checkingRecyclerView.setLayoutManager(linearLayoutManager);

        database.getReference().child("Donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){

                    Donation donation= snapshot1.getValue(Donation.class);
                    if(!(donation.getStatus().equals("Accept")) ){
                        list.add(donation);
                    }
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