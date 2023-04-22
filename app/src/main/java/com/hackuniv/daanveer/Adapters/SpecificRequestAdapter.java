package com.hackuniv.daanveer.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.R;

import java.util.ArrayList;

public class SpecificRequestAdapter extends RecyclerView.Adapter<SpecificRequestAdapter.ViewHolder> {
    ArrayList<Donation> list;
    Context context;

    public SpecificRequestAdapter(ArrayList<Donation> list, Context context) {
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
//        StringBuilder items = new StringBuilder();
//        ArrayList<String> listItems = donation.getItems();
//        items.setLength(0);
//        for(int i=1;i<listItems.size();i++ ){
//            items.append(listItems.get(i));
//            Log.d("SachinKadian",items.toString());
//        }
//        Log.d("SachinKadian",items.toString());
//        holder.items.setText(items);
        }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donatorName);
            address = itemView.findViewById(R.id.donatorAddress);

        }
    }
}
