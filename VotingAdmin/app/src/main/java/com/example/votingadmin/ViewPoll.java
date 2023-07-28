package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewPoll extends Fragment {


    private RecyclerView voteRV;
    private ArrayList<AddPollClass> addPollClasses;
    private MyListAdapter1 myListAdapter1;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    public ViewPoll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_poll, container, false);
        voteRV = view.findViewById(R.id.your_recycler_view_id2);
        loadingPB = view.findViewById(R.id.idProgressBar);

        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        Log.d("Aka","getting view");
        // Creating our new array list
        addPollClasses = new ArrayList<>();

        voteRV.setHasFixedSize(true);
        voteRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adding our array list
        myListAdapter1 = new MyListAdapter1(getContext(), addPollClasses);

        voteRV.setAdapter(myListAdapter1); // Setting the adapter to the RecyclerView

        fetchUserDataFromFirestore();

        return view;
    }

    private void fetchUserDataFromFirestore() {
        loadingPB.setVisibility(View.VISIBLE);

        db.collection("PollData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                AddPollClass c = d.toObject(AddPollClass.class);
                                c.setaId(d.getId());
                                addPollClasses.add(c);
                                Log.d("Aka","getting data..");
                            }
                            myListAdapter1.notifyDataSetChanged();
                        } else {
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                            Log.d("Aka","no data found");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                        Log.d("Aka","failure to get");
                    }
                });
    }
}

