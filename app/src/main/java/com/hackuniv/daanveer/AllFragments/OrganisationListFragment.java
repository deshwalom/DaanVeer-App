package com.hackuniv.daanveer.AllFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Adapters.OrgFragmentAdapter;
import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.R;
import com.hackuniv.daanveer.databinding.FragmentOrganisationListBinding;

import java.util.ArrayList;

public class OrganisationListFragment extends Fragment {


    public OrganisationListFragment() {
        // Required empty public constructor
    }
    FragmentOrganisationListBinding binding;
    FirebaseDatabase database;
    ArrayList<Organisation> list= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrganisationListBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();
        OrgFragmentAdapter adapter = new OrgFragmentAdapter(list,getContext());
//        OrgFrgmentAdapter adapter=new OrgFragmentAdapter(list,getContext());
        Log.d("SachinKadian", getContext().toString());
        binding.rcvOrgList.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.rcvOrgList.setLayoutManager(linearLayoutManager);

        database.getReference().child("Organisation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Organisation users = dataSnapshot.getValue(Organisation.class);
//                    users.setUserId(dataSnapshot.getKey());                 //yhan userId get ki h
//                    if(users.getUserId().equals(FirebaseAuth.getInstance().getUid()))
//                        continue;
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}