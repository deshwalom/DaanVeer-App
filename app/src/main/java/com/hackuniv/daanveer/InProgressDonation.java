package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityDonationSkeletonBinding;
import com.hackuniv.daanveer.databinding.ActivityInProgressBinding;
import com.hackuniv.daanveer.databinding.ActivityInProgressDonationBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InProgressDonation extends AppCompatActivity {
    ActivityInProgressDonationBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String newest = "";
    ArrayList<String> arrayList=new ArrayList<>();
    String phnNo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInProgressDonationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        StringBuilder items=new StringBuilder();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Donation");
        String donatorName = getIntent().getStringExtra("donatorName");
        String donatorId = getIntent().getStringExtra("donatorId");
        String donatorImg = getIntent().getStringExtra("donatorImg");
        Picasso.get().load(donatorImg).placeholder(R.drawable.profileuser).into(binding.donatorImage);
        binding.donatorName1.setText(donatorName);

        database.getReference().child("Donation").child(donatorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Donation donation=snapshot.getValue(Donation.class);
                arrayList=donation.getItems();
                items.setLength(0);
                for(int i=0;i<arrayList.size();i++){
                    items.append(arrayList.get(i));
                }
                binding.donatedItems.setText(items);
                binding.donatorFullAddress.setText(donation.getDonatorAddress());
                binding.donatorMobileNo.setText(donation.getDonatorMobile());
                phnNo=donation.getDonatorMobile();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("UsersDonation").child(donatorId).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Donation donation = snapshot.getValue(Donation.class);
                if(snapshot.hasChildren()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                        donation = snapshot1.getValue(Donation.class);
//                        donation.setStatus("In Prgoress");
//                        holder.lastMessage.setText(snapshot1.child("message").getValue(String.class));
//                        Log.d("SachinKadian","sadasd===" + donation.getDonatorName());
//                        Log.d("SachinKadian","sadasd===" + snapshot1.getKey());
                        newest = snapshot1.getKey();
                    }
//                    database.getReference().child("UsersDonation").child(auth.getUid()).child(newest).child("status").setValue("In Progress");
                }
//                Log.d("SachinKadian","sadasd" + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InProgressDonation.this, "Your request is completed", Toast.LENGTH_SHORT).show();
                database.getReference().child("Donation").child(donatorId).child("status").setValue("Completed");
                database.getReference().child("UsersDonation").child(donatorId).child(newest).child("status").setValue("Completed");
            }
        });
        binding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Donation").child(donatorId).child("status").setValue("Reject");
                database.getReference().child("UsersDonation").child(donatorId).child(newest).child("status").setValue("Reject");
                Toast.makeText(InProgressDonation.this, "Your request is Rejected", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phnNo));
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}