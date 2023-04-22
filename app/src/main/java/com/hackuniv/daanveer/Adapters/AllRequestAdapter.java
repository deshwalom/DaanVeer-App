package com.hackuniv.daanveer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.DonationSkeleton;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllRequestAdapter extends RecyclerView.Adapter<AllRequestAdapter.ViewHolder>{
    ArrayList<Donation> list;
    Context context;
    String newest = "";
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    public AllRequestAdapter(ArrayList<Donation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_checkingitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = list.get(position);
        holder.name.setText(donation.getDonatorName());
        holder.address.setText(donation.getDonatorAddress());
        Picasso.get().load(donation.getDonatorImage()).placeholder(R.drawable.profileuser).into(holder.img);
//        StringBuilder items = new StringBuilder();
//        ArrayList<String> listItems = donation.getItems();
//        items.setLength(0);
//        for(int i=1;i<listItems.size();i++ ){
//            items.append(listItems.get(i));
//            Log.d("SachinKadian",items.toString());
//        }
//        Log.d("SachinKadian",items.toString());
//        holder.items.setText(items);
        database.getReference().child("UsersDonation").child(donation.getDonatorId()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonationSkeleton.class);
                intent.putExtra("donatorName",donation.getDonatorName());
                intent.putExtra("donatorId",donation.getDonatorId());
                intent.putExtra("donatorImg",donation.getDonatorImage());
                //  intent.putExtra("donatorLocation",donation.getDonatorAddress());
                // intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SachinKadian","reject click hogya!!");
                database.getReference().child("Donation").child(donation.getDonatorId()).child("status").setValue("Reject");
                database.getReference().child("UsersDonation").child(donation.getDonatorId()).child(newest).child("status").setValue("Reject");

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SachinKadian","accept click hogya!!");
                database.getReference().child("Donation").child(donation.getDonatorId()).child("status").setValue("In Progress");
                database.getReference().child("UsersDonation").child(donation.getDonatorId()).child(newest).child("status").setValue("In Progress");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView address;
        Button accept;
        Button reject;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donatorName);
            address = itemView.findViewById(R.id.donatorAddress);
            accept = itemView.findViewById(R.id.btnAccept);
            reject = itemView.findViewById(R.id.btnReject);
            img = itemView.findViewById(R.id.donatorImage);
        }
    }
}
