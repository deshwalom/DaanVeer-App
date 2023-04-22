package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.Model.Donators;
import com.hackuniv.daanveer.databinding.ActivityLetsDonateBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LetsDonate extends AppCompatActivity {
    ActivityLetsDonateBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String donatorimg = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLetsDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ArrayList<String> items = new ArrayList<>();
        Donation donation = new Donation();
        ArrayList<String> list = new ArrayList<>();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(binding.checkBoxFood.isChecked() || binding.checkBoxCereals.isChecked() || binding.checkBoxClothes.isChecked() || binding.checkBoxShoes.isChecked() || binding.checkBoxEducation.isChecked() || (!binding.etOtherItem.getText().toString().isEmpty()))) {
                    Toast.makeText(LetsDonate.this, "Please select any item", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (binding.etDonatorName.getText().toString().isEmpty()) {
                        binding.tilDonatorName.setError("Name is required!");
                        return;
                    }
                    if (binding.etDonatorPhone.getText().toString().isEmpty()) {
                        binding.tilDonatorPhone.setError("Phone no. is required!");
                        return;
                    }
                    if((binding.etDonatorPhone.getText().toString().trim()).length() != 10){
                        binding.tilDonatorPhone.setError("Not a valid no. !");
                        return;
                    }
                    if (binding.etDonatorAdd.getText().toString().isEmpty()) {
                        binding.tilDonatorAdd.setError("Address is required!");
                        return;
                    }





                items.clear();
                list.clear();
//                items.add("Total donation items are : \n");
                if (binding.checkBoxFood.isChecked())
                    items.add(binding.checkBoxFood.getText().toString() + "\n");
                if (binding.checkBoxClothes.isChecked())
                    items.add(binding.checkBoxClothes.getText().toString() + "\n");
                if (binding.checkBoxEducation.isChecked())
                    items.add(binding.checkBoxEducation.getText().toString() + "\n");
                if (binding.checkBoxShoes.isChecked())
                    items.add(binding.checkBoxShoes.getText().toString() + "\n");
                if (binding.checkBoxCereals.isChecked())
                    items.add(binding.checkBoxCereals.getText().toString() + "\n");
                if (!binding.etOtherItem.getText().toString().isEmpty())
                    items.add(binding.etOtherItem.getText().toString() + "\n");

                donation.setItems(items);
                donation.setDonatorId(auth.getUid());
                donation.setDonatorAddress(binding.etDonatorAdd.getText().toString());
                donation.setDonatorMobile(binding.etDonatorPhone.getText().toString());
                donation.setDonatorName(binding.etDonatorName.getText().toString());
                donation.setStatus("Accept");

                    database.getReference().child("Donators").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Donators donators = snapshot.getValue(Donators.class);
                            donatorimg = donators.getProfileImage();
                            donation.setDonatorImage(donatorimg);
                            database.getReference().child("Donation").child(auth.getUid()).setValue(donation);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                database.getReference().child("Donation").child(auth.getUid()).child("Donate").setValue(items);
//                database.getReference().child("Donation").child(auth.getUid()).setValue(donation);
                startActivity(new Intent(LetsDonate.this, SelectOrg.class));
                finish();
//        database.getReference().child("Donation").child(auth.getUid()).setValue(items);
            } }
        });
        }
//        StringBuilder all = new StringBuilder();
//        ArrayList<Donation> list1 = new ArrayList<>();
//        database.getReference().child("Donation").child("103").child("Donate").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
////                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                Donation donation1 = snapshot.getValue(Donation.class);
//                Log.d("sachinKadian", "onDataChange: "+ snapshot.getKey());
//                Log.d("sachinKadian", "onDataChange: "+ donation1.getDonatorMobile());
//                list1.add(donation1);
//                Donation newdon = list1.get(0);
////                binding.textView.setText(newdon.getDonatorName());
//
////                }
////                all.setLength(0);
////                for (int i = 0; i < list.size(); i++) {
//                // Printing and display the elements in ArrayList
////                    System.out.print(list.get(i) + " ");
//
////                    all.append(list.get(i));
////                }
////                binding.textView.setText(all);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}