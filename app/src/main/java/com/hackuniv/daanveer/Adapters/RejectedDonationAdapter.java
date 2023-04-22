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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hackuniv.daanveer.DonationSkeleton;
import com.hackuniv.daanveer.InProgressDonation;
import com.hackuniv.daanveer.Model.Donation;
import com.hackuniv.daanveer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RejectedDonationAdapter extends RecyclerView.Adapter<RejectedDonationAdapter.ViewHolder> {
    ArrayList<Donation> list;
    Context context;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    public RejectedDonationAdapter(ArrayList<Donation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_donation_progress,parent,false);
        return new RejectedDonationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = list.get(position);
        holder.name.setText(donation.getDonatorName());
        Picasso.get().load(donation.getDonatorImage()).placeholder(R.drawable.profileuser).into(holder.imageView);
        holder.address.setText(donation.getDonatorAddress());
        if(donation.getStatus().equals("Reject")){
            holder.status.setTextColor(context.getResources().getColor(R.color.reddish_button_color));
            holder.status.setText("Rejected");
        }
        else if(donation.getStatus().equals("In Progress")){
            holder.status.setTextColor(context.getResources().getColor(R.color.orange_color));
//            holder.status.setTextColor(R.color.orange_color);
            holder.status.setText("Under Processing");
        }
        else {
            holder.status.setText("Completed");
            holder.status.setTextColor(context.getResources().getColor(R.color.green_color));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donation.getStatus().equals("Reject") || donation.getStatus().equals("Completed")){
                    Intent intent = new Intent(context, DonationSkeleton.class);
                    intent.putExtra("donatorName",donation.getDonatorName());
                    intent.putExtra("donatorId",donation.getDonatorId());
                    intent.putExtra("donatorImg",donation.getDonatorImage());

                    //  intent.putExtra("donatorLocation",donation.getDonatorAddress());
                    // intent.putExtra("userName",users.getUserName());
                    context.startActivity(intent);
                    }
                else{
                Intent intent = new Intent(context, InProgressDonation.class);
                intent.putExtra("donatorName",donation.getDonatorName());
                intent.putExtra("donatorId",donation.getDonatorId());
                intent.putExtra("donatorImg",donation.getDonatorImage());
                //  intent.putExtra("donatorLocation",donation.getDonatorAddress());
                // intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
                }
            }
        });
//        StringBuilder items = new StringBuilder();
//        ArrayList<String> listItems = donation.getItems();
//        items.setLength(0);
//        for(int i=1;i<listItems.size();i++ ){
//            items.append(listItems.get(i));
//            Log.d("SachinKadian",items.toString());
//        }
//        Log.d("SachinKadian",items.toString());
//        holder.items.setText(items);

//        holder.reject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("SachinKadian","reject click hogya!!");
//                database.getReference().child("Donation").child(donation.getDonatorId()).child("status").setValue("Reject");
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView address;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donatorName);
            imageView = itemView.findViewById(R.id.donatorImage);
            address = itemView.findViewById(R.id.donatorAddress);
            status=itemView.findViewById(R.id.progressStatus);
        }
    }

}
