package com.example.votingadmin.handlingusers;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.R;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private final List<AddUserData> listdata;
    private final Context context;// Constructor to set the OnItemClickListener

    // RecyclerView recyclerView;
    public MyListAdapter(Context context, List<AddUserData> listdata) {
        this.context = context;
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AddUserData serverData = listdata.get(position);
        Log.d("Aka","onBindViewHolder");
        holder.fullname.setText("Name: " + serverData.getAuFullname());
        holder.aadhar.setText("Aadhar: " + serverData.getAuAadhaar());
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullname;
        private final TextView aadhar;

        public ViewHolder(View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.rv_Name);
            aadhar = itemView.findViewById(R.id.rv_aadhar);
            itemView.setOnClickListener(view -> {
                AddUserData serverData = listdata.get(getAdapterPosition());
                Intent intent = new Intent(context, EditUserActivity.class);
                Log.d("Akash",serverData.getAuAadhaar());
                intent.putExtra("userId1",serverData);
                context.startActivity(intent);
            });
        }
    }
}