package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.AllFragments.DonatorProfileFragment;
import com.hackuniv.daanveer.AllFragments.HomeFragment;
import com.hackuniv.daanveer.AllFragments.OrganisationListFragment;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.databinding.ActivityDonatorBinding;

public class  DonatorActivity extends AppCompatActivity {
ActivityDonatorBinding binding;
View view;
    Donation donation = new Donation();
    String newest = "";
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

//        database.getReference().child("UsersDonation").child(auth.getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                Donation donation = snapshot.getValue(Donation.class);
//                if(snapshot.hasChildren()){
//                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                         donation = snapshot1.getValue(Donation.class);
//                        donation.setStatus("In Prgoress");
////                        holder.lastMessage.setText(snapshot1.child("message").getValue(String.class));
//                        Log.d("SachinKadian","sadasd===" + donation.getDonatorName());
//                        Log.d("SachinKadian","sadasd===" + snapshot1.getKey());
//                        newest = snapshot1.getKey();
//                    }
//                    database.getReference().child("UsersDonation").child(auth.getUid()).child(newest).child("status").setValue("In Progress");
//                }
////                Log.d("SachinKadian","sadasd" + snapshot.getKey());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        openHomeFragment(view);
    }
    public void openOrgListFragment(View view){
        binding.imagelist.setColorFilter(Color.parseColor("#0067C4"));
        binding.imagehome.setColorFilter(Color.parseColor("#3987CD"));
        binding.imageprofile.setColorFilter(Color.parseColor("#3987CD"));
        binding.imagelist.setPadding(14,14,14,14);
        binding.imagehome.setPadding(25,25,25,25);
        binding.imageprofile.setPadding(25,25,25,25);
        OrganisationListFragment first = new OrganisationListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearlayout,first);
        transaction.commit();
    }
    public void openHomeFragment(View view){
        binding.imagehome.setColorFilter(Color.parseColor("#0067C4"));
        binding.imagelist.setColorFilter(Color.parseColor("#3987CD"));
        binding.imageprofile.setColorFilter(Color.parseColor("#3987CD"));
        binding.imagehome.setPadding(14,14,14,14);
        binding.imagelist.setPadding(25,25,25,25);
        binding.imageprofile.setPadding(25,25,25,25);
        //set the home
        HomeFragment first = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearlayout,first);
        transaction.commit();
    }
    public void openProfileFragment(View view){
        binding.imageprofile.setColorFilter(Color.parseColor("#0067C4"));
        binding.imagelist.setColorFilter(Color.parseColor("#3987CD"));
        binding.imagehome.setColorFilter(Color.parseColor("#3987CD"));
        binding.imageprofile.setPadding(14,14,14,14);
        binding.imagelist.setPadding(25,25,25,25);
        binding.imagehome.setPadding(25,25,25,25);
        DonatorProfileFragment second = new DonatorProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearlayout,second);
        transaction.commit();
    }
}