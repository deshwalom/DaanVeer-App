package com.hackuniv.daanveer.AllFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.AboutActivity;
import com.hackuniv.daanveer.DonatorHistory;
import com.hackuniv.daanveer.HelpActivity;
import com.hackuniv.daanveer.Model.DonatorDetails;
import com.hackuniv.daanveer.Model.DonatorEditProfile;
import com.hackuniv.daanveer.Model.Donators;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.SignUpPhoneNumber;
import com.hackuniv.daanveer.databinding.FragmentDonatorProfileBinding;
import com.hackuniv.daanveer.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

public class DonatorProfileFragment extends Fragment {

    public DonatorProfileFragment() {
        // Required empty public constructor
    }
    FragmentDonatorProfileBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        binding = FragmentDonatorProfileBinding.inflate(inflater,container,false);
        binding.editprofilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DonatorEditProfile.class));

            }
        });
        database.getReference().child("Donators").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Donators donators = snapshot.getValue(Donators.class);
                binding.usernameprofile.setText(donators.getUsername());
                Picasso.get().load(donators.getProfileImage()).placeholder(R.drawable.profileuser).into(binding.editProfileImage);
                binding.mobiletv.setText(donators.getMobile());
                binding.emailtv.setText(donators.getEmail());
                binding.tvcity.setText(donators.getCity());
//                Toast.makeText(getContext(), "Info Updated!!", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignUpPhoneNumber.class));
                getActivity().finish();
            }
        });
        binding.cardAbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });
        binding.cardHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HelpActivity.class));
            }
        });
        binding.btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DonatorHistory.class));
            }
        });



        return binding.getRoot();
    }
}