package com.hackuniv.daanveer.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.R;

import java.util.ArrayList;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder>{
    ArrayList<Donation> list;
    Context context;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    public DonationHistoryAdapter(ArrayList<Donation> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_donation_history,parent,false);
        return new DonationHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = list.get(position);
        holder.name.setText(donation.getDonatorName());
        holder.org.setText(donation.getOrgName());
        holder.phone.setText(donation.getDonatorMobile());
        holder.mode.setText(donation.getMode());
        holder.status.setText(donation.getStatus());
        StringBuilder items = new StringBuilder();
        ArrayList<String> alldonation = donation.getItems();
        for(int i=0;i<alldonation.size();i++){
            items.append(alldonation.get(i));
        }
        items.deleteCharAt(items.length() - 1);
        holder.items.setText(items);
//        Log.d("SachinKadian", String.valueOf(alldonation.size())+items);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView org;
        TextView mode;
        TextView items;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.etDonationName);
            phone = itemView.findViewById(R.id.etPhnNo);
            org = itemView.findViewById(R.id.etOrgName);
            mode = itemView.findViewById(R.id.etMode);
            items = itemView.findViewById(R.id.etItems);
            status=itemView.findViewById(R.id.etStatus);
        }
    }

}
