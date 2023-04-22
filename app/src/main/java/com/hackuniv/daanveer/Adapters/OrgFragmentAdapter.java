package com.hackuniv.daanveer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.OrgSkeleton;
import com.hackuniv.daanveer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrgFragmentAdapter extends RecyclerView.Adapter<OrgFragmentAdapter.ViewHolder>  {
    ArrayList<Organisation> list = new ArrayList<>();
    Context context;

    public OrgFragmentAdapter(ArrayList<Organisation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_orglist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Organisation organisation = list.get(position);
        holder.orgName.setText(organisation.getOrgName());
        holder.orgCity.setText(organisation.getOrgCity());
        Picasso.get().load(organisation.getOrgPic()).placeholder(R.drawable.profileuser).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrgSkeleton.class);
                intent.putExtra("OrgName",organisation.getOrgName());
                intent.putExtra("FounderName",organisation.getOrgFounder().getUsername());
                intent.putExtra("isVerified",organisation.getVerifed());
                intent.putExtra("orgLocation",organisation.getOrgAddress());
                intent.putExtra("mobileNo",organisation.getOrgMobile());
                intent.putExtra("orgImage",organisation.getOrgPic());
                intent.putExtra("orgDsc",organisation.getOrgDsc());

                Log.d("SachinKadian","chl rha h bette!!!!!" + organisation.getOrgMobile());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView orgName;
        TextView orgCity;

        public ViewHolder( View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.orgImage);
            orgName=itemView.findViewById(R.id.orgName);
            orgCity=itemView.findViewById(R.id.tvLocation);
        }

    }
}
