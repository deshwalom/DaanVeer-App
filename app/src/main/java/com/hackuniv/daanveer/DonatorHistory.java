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
import com.hackuniv.daanveer.Adapters.DonationHistoryAdapter;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityDonatorHistoryBinding;

import java.util.ArrayList;

public class DonatorHistory extends AppCompatActivity {
    ActivityDonatorHistoryBinding binding;
    ArrayList<Donation> list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonatorHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("History");
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        DonationHistoryAdapter adapter = new DonationHistoryAdapter(list,DonatorHistory.this);
        binding.donationRcv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DonatorHistory.this);
        binding.donationRcv.setLayoutManager(linearLayoutManager);

        database.getReference().child("UsersDonation").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){

                    Donation donation= snapshot1.getValue(Donation.class);
                        list.add(donation);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        database.getReference().child("UsersDonation").child(auth.getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                if(snapshot.hasChildren()){
//                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                        Donation donation = snapshot1.getValue(Donation.class);
//                        list.add(donation);
////                        holder.lastMessage.setText(snapshot1.child("message").getValue(String.class));
//                        Log.d("SachinKadian","sadasd===" + donation.getDonatorName());
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}