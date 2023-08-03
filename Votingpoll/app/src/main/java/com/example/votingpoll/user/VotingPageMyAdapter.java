package com.example.votingpoll.user;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.R;

import java.util.List;

public class VotingPageMyAdapter extends RecyclerView.Adapter<VotingPageMyViewHolder> {

    Context context;
    List<Item> items;
    SelectListener listener;
    public VotingPageMyAdapter(Context context, List<Item> items, SelectListener listener)
    {
        this.context=context;
        this.items=items;
        this.listener=listener;
    }
    //Item i = new Item("hhu", 88);
    @NonNull
    @Override
    public VotingPageMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VotingPageMyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @SuppressLint("RecyclerView")
    @NonNull
    @Override
    public void onBindViewHolder(VotingPageMyViewHolder holder, int position) {
        holder.textView.setText(items.get(position).getName());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.imageView1.setImageResource(items.get(position).getImage1());
        holder.textView1.setText(items.get(position).pname);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIemClicked(items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}