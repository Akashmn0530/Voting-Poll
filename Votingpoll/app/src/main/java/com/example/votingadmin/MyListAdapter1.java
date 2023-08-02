package com.example.votingadmin;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import com.example.votingpoll.R;
public class MyListAdapter1 extends RecyclerView.Adapter<MyListAdapter1.ViewHolder>{
    private List<AddPollClass> listdata;
    private Context context;
    // RecyclerView recyclerView;
    public MyListAdapter1(Context context, List<AddPollClass> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);;
        return new MyListAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter1.ViewHolder holder, int position) {
        final AddPollClass addPollClass = listdata.get(position);
        holder.fullname.setText("Name :"+addPollClass.getaFullname());
        holder.aadhar.setText("Aadhar :"+addPollClass.getaAadhaar());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname,aadhar;
        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = (TextView) itemView.findViewById(R.id.rv_Name);
            this.aadhar = (TextView) itemView.findViewById(R.id.rv_aadhar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddPollClass addPollClass = listdata.get(getAdapterPosition());
                    Intent intent = new Intent(context, EditPollActivity.class);
                    Log.d("Akash",addPollClass.getaAadhaar());
                    intent.putExtra("userId",addPollClass);
                    context.startActivity(intent);
                }
            });
        }
    }
}