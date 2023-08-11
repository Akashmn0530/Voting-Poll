package com.example.votingadmin.handlingusers;

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

import com.example.votingadmin.handlingusers.AddUserData;
import com.example.votingadmin.handlingusers.MyListAdapter;
import com.example.votingpoll.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class ViewUser extends Fragment {
    private ArrayList myListData;
    private MyListAdapter myListAdapter;
    private FirebaseFirestore db;
    private ProgressBar loadingPB;

    public ViewUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_user, container, false);
        RecyclerView voteRV = view.findViewById(R.id.your_recycler_view_id1);
        loadingPB = view.findViewById(R.id.idProgressBar);

        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        Log.d("Aka","getting view");
        // Creating our new array list
        myListData = new ArrayList<>();

        voteRV.setHasFixedSize(true);
        voteRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adding our array list
        myListAdapter = new MyListAdapter(getContext(), myListData);

        voteRV.setAdapter(myListAdapter); // Setting the adapter to the RecyclerView

        fetchUserDataFromFirestore();

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchUserDataFromFirestore() {
        loadingPB.setVisibility(View.VISIBLE);

        db.collection("AddUser").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        loadingPB.setVisibility(View.GONE);
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            AddUserData c = d.toObject(AddUserData.class);
                            assert c != null;
                            c.setauAadhaar(d.getId());
                            myListData.add(c);
                            Log.d("Aka","getting data..");
                        }
                        myListAdapter.notifyDataSetChanged();
                    } else {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        Log.d("Aka","no data found");
                    }
                })
                .addOnFailureListener(e -> {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    Log.d("Aka","failure to get");
                });
    }
}