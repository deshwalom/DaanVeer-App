package com.hackuniv.daanveer.AllFragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.AllRequestActivity;
import com.hackuniv.daanveer.InternetReceiver;
import com.hackuniv.daanveer.LetsDonate;
import com.hackuniv.daanveer.Model.Donators;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.SpecificRequestActivity;
import com.hackuniv.daanveer.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    FragmentHomeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    View view;
    BroadcastReceiver broadcastReceiver = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        broadcastReceiver = new InternetReceiver();
        Internetstatus();
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Fetching Data");
//        progressDialog.setMessage("We're fetching your details..");
//        progressDialog.show();
        database.getReference().child("Donators").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Donators donators = snapshot.getValue(Donators.class);
                binding.donatorUserName.setText(donators.getUsername());
                Picasso.get().load(donators.getProfileImage()).placeholder(R.drawable.profileuser).into(binding.profileImage);
//                Toast.makeText(getContext(), "Info Updated!!", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonatorProfileFragment second = new DonatorProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.linearlayout,second);
                transaction.commit();
            }
        });
        binding.btnLetsDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LetsDonate.class));
            }
        });








        return binding.getRoot();
    }
    public void Internetstatus(){
        getContext().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}