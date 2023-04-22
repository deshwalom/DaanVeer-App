package com.hackuniv.daanveer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.hackuniv.daanveer.databinding.ActivityOrgSkeletonBinding;
import com.squareup.picasso.Picasso;

public class OrgSkeleton extends AppCompatActivity {
ActivityOrgSkeletonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrgSkeletonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String orgName=getIntent().getStringExtra("OrgName");
        String founderName=getIntent().getStringExtra("FounderName");
        Boolean isVerified=getIntent().getBooleanExtra("isVerified",false);
        String orgLocation=getIntent().getStringExtra("orgLocation");
        String mobileNo=getIntent().getStringExtra("mobileNo");
        String orgImage=getIntent().getStringExtra("orgImage");
        String orgDsc=getIntent().getStringExtra("orgDsc");
        Log.d("SachinKadian","chl rha h bette!!!!!" + mobileNo);
        binding.orgName.setText(orgName);
        binding.orgFounderName.setText(founderName);
        binding.txtloc.setText(orgLocation);
        binding.txtCont.setText(mobileNo);
        binding.txtDesc.setText(orgDsc);
        Picasso.get().load(orgImage).placeholder(R.drawable.profileuser).into(binding.orgImage);
     if(isVerified==true)
        binding.orgName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_verified_24,0);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}