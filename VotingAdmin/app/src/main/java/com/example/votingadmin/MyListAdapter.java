package com.example.votingadmin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<ServerData> listdata;
    private Context context;// Constructor to set the OnItemClickListener

    // RecyclerView recyclerView;
    public MyListAdapter(Context context, List<ServerData> listdata) {
        this.context = context;
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServerData serverData = listdata.get(position);
        Log.d("Aka","onBindViewHolder");
        holder.fullname.setText("Name: " + serverData.getAuFullname());
        holder.email.setText("E-mail: " + serverData.getAuEmail());
        holder.mobile.setText("Mobile: " + serverData.getAuMobile());
        holder.aadhar.setText("Aadhar: " + serverData.getAuAadhaar());
        holder.address.setText("Address: " + serverData.getAuAddress());
        holder.voteCount.setText("Vote count: "+serverData.getVoteCount());
        holder.voteStatus.setText("Vote status: "+serverData.getVote());
        holder.rating.setText("User Rating: "+serverData.getAuRating());
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname, email, mobile, address, aadhar, voteStatus, voteCount,rating;

        public ViewHolder(View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.rv_Name);
            email = itemView.findViewById(R.id.rv_email);
            mobile = itemView.findViewById(R.id.rv_mobile);
            address = itemView.findViewById(R.id.rv_address);
            aadhar = itemView.findViewById(R.id.rv_aadhar);
            voteCount = itemView.findViewById(R.id.rv_count);
            voteStatus = itemView.findViewById(R.id.rv_vote);
            rating = itemView.findViewById(R.id.rv_rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ServerData serverData = listdata.get(getAdapterPosition());
                    Intent intent = new Intent(context, EditUserActivity.class);
                    Log.d("Akash",serverData.getAuEmail());
                    intent.putExtra("userId1",serverData);
                    context.startActivity(intent);
                }
            });
        }
    }
}