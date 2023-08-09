package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ViewContest extends Fragment {


    private ArrayList myListData;
    private AdapterContest myListAdapter;
    private FirebaseFirestore db;
    private ProgressBar loadingPB;

    public ViewContest() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_contest, container, false);
        RecyclerView voteRV = view.findViewById(R.id.your_recycler_view_id11);
        loadingPB = view.findViewById(R.id.idProgressBar11);

        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        Log.d("Aka", "getting view");
        // Creating our new array list
        myListData = new ArrayList<>();

        voteRV.setHasFixedSize(true);
        voteRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adding our array list
        myListAdapter = new AdapterContest(getContext(), myListData);

        voteRV.setAdapter(myListAdapter); // Setting the adapter to the RecyclerView

        fetchUserDataFromFirestore();

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchUserDataFromFirestore() {
        loadingPB.setVisibility(View.VISIBLE);

        db.collection("contestData").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        loadingPB.setVisibility(View.GONE);
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            ContestClass c = d.toObject(ContestClass.class);
                            assert c != null;
                            c.setConId(d.getId());
                            myListData.add(c);
                            Log.d("Aka", "getting data..");
                        }
                        myListAdapter.notifyDataSetChanged();
                    } else {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        Log.d("Aka", "no data found");
                    }
                })
                .addOnFailureListener(e -> {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    Log.d("Aka", "failure to get");
                });
    }
}
