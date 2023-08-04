package com.example.votingadmin;

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

public class FeedbackAdaptor extends RecyclerView.Adapter<FeedbackAdaptor.ViewHolder>{
    private List<ViewFeedback> listdata;
    private Context context;
    // RecyclerView recyclerView;
    public FeedbackAdaptor(Context context, List<ViewFeedback> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public FeedbackAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_items, parent, false);;
        return new FeedbackAdaptor.ViewHolder(view);
    }

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
            this.fullname = (TextView) itemView.findViewById(R.id.rv_Name);
            this.aadhar = (TextView) itemView.findViewById(R.id.rv_aadhar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewFeedback viewFeedback = listdata.get(getAdapterPosition());
                    Intent intent = new Intent(context, EditPollActivity.class);
//                    Log.d("Akash",addPollClass.getaAadhaar());
//                    intent.putExtra("userId",addPollClass);
                    context.startActivity(intent);
                }
            });
        }
    }
}


