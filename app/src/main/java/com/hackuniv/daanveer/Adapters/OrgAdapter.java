package com.hackuniv.daanveer.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hackuniv.daanveer.DeliveryMode;
import com.hackuniv.daanveer.Model.Organisation;
import com.hackuniv.daanveer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder>{
    ArrayList<Organisation> list;
    Context context;
    FirebaseDatabase database;
    public OrgAdapter(ArrayList<Organisation> list, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Organisation organisation = list.get(position);
        Picasso.get().load(organisation.getOrgPic()).placeholder(R.drawable.profileuser).into(holder.orgImage);
        holder.orgName.setText(organisation.getOrgName());
        holder.orgLocation.setText(organisation.getOrgCity());
        database = FirebaseDatabase.getInstance();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ChatDetailActivity.class);
//                intent.putExtra("userId",users.getUserId());
//                intent.putExtra("profilePic",users.getProfilepic());
//                intent.putExtra("userName",users.getUserName());
//                context.startActivity(intent);
                Intent intent = new Intent(context, DeliveryMode.class);
//                intent.putExtra("orgId",organisation.getOrgId());
//                intent.putExtra("test","103");
                database.getReference().child("Donation").child(FirebaseAuth.getInstance().getUid()).child("orgName").setValue(organisation.getOrgName());
                database.getReference().child("Donation").child(FirebaseAuth.getInstance().getUid()).child("organinsation").setValue(organisation.getOrgId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView orgImage;
        TextView orgName;
        TextView orgLocation;
        public ViewHolder( View itemView) {
            super(itemView);

            orgImage = itemView.findViewById(R.id.orgImage);
            orgName = itemView.findViewById(R.id.orgName);
            orgLocation = itemView.findViewById(R.id.tvLocation);


        }
        }


    }

