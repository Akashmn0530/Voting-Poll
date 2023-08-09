package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.R;

import java.util.List;

public class FeedbackAdaptor extends RecyclerView.Adapter<FeedbackAdaptor.ViewHolder>{
    private final List<ViewFeedback> listdata;
    private final Context context;
    // RecyclerView recyclerView;
    public FeedbackAdaptor(Context context, List<ViewFeedback> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public FeedbackAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_items, parent, false);
        return new FeedbackAdaptor.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdaptor.ViewHolder holder, int position) {
        final ViewFeedback viewFeedback = listdata.get(position);
        holder.fullname.setText("Rating :"+viewFeedback.getViewRating());
        holder.aadhar.setText("Description :"+viewFeedback.getViewDescription());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname,aadhar;
        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = itemView.findViewById(R.id.rv_Name);
            this.aadhar = itemView.findViewById(R.id.rv_aadhar);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, EditCandidatesActivity.class);
                context.startActivity(intent);
            });
        }
    }
}


