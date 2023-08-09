package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddContest extends Fragment {
    EditText contestName, contestID;
    Button addContest;
    FirebaseFirestore db;
    ContestClass contestClass;
    String id,name;
    private void addDatatoFireStore(String idData,String name) {
        contestClass.setConName(name);
        contestClass.setConId(idData);
        db.collection("contestData").document(idData)
                .set(contestClass).addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contestID = getView().findViewById(R.id.ContestID1);
        contestName = getView().findViewById(R.id.contestName1);
        addContest = getView().findViewById(R.id.addContest);
        contestClass = new ContestClass();
        db = FirebaseFirestore.getInstance();
        addContest.setOnClickListener(view1 -> {
            id = contestID.getText().toString();
            name = contestName.getText().toString();
            addDatatoFireStore(id,name); });

    }

    public AddContest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_contest, container, false);
        return view;
    }
}