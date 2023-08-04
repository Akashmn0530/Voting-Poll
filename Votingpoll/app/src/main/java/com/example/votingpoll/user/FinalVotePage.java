package com.example.votingpoll.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.votingpoll.R;


public class FinalVotePage extends Fragment {
    TextView name, email;
    String idValue;
    ScrollView scrollView;
    Button voteBTN;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getView().findViewById(R.id.pnameFinal);
        email = getView().findViewById(R.id.pEmailFinal);
        voteBTN = getView().findViewById(R.id.pVote);
        scrollView = getView().findViewById(R.id.pScroll);
        idValue = Login.uidpass;
    }

    public FinalVotePage() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_vote_page, container, false);
        return view;
    }
}