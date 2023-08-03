package com.example.votingpoll.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.votingpoll.R;

import java.util.ArrayList;
import java.util.List;

public class VotingPageFragment extends Fragment implements SelectListener{
    RecyclerView recyclerView;
    public VotingPageFragment() { }
    @Override
    public void onIemClicked(Item item) {
        Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voting_page, container, false);
        recyclerView = view.findViewById(R.id.recyclicview12);
        List<Item> items=new ArrayList<>();
        items.add(new Item(R.drawable.bg1,"Basavaraj",R.drawable.bg2,"BJP"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new VotingPageMyAdapter(getContext(),items,this));

        return view;
    }
}