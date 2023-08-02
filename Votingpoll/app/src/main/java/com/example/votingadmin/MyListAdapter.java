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
import com.example.votingpoll.R;
import com.example.votingpoll.user.ServerData;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<AddUserData> listdata;
    private Context context;// Constructor to set the OnItemClickListener

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
        private TextView fullname,aadhar;

        public ViewHolder(View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.rv_Name);
            aadhar = itemView.findViewById(R.id.rv_aadhar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddUserData serverData = listdata.get(getAdapterPosition());
                    Intent intent = new Intent(context, EditUserActivity.class);
                    Log.d("Akash",serverData.getAuAadhaar());
                    intent.putExtra("userId1",serverData);
                    context.startActivity(intent);
                }
            });
        }
    }
}