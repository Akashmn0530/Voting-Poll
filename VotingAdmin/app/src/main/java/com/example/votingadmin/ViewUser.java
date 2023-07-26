package com.example.votingadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewUser extends Fragment {
    private RecyclerView voteRV;
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
        voteRV = view.findViewById(R.id.your_recycler_view_id1);
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

    private void fetchUserDataFromFirestore() {
        loadingPB.setVisibility(View.VISIBLE);

        db.collection("AddUser").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ServerData c = d.toObject(ServerData.class);
                                myListData.add(c);
                                Log.d("Aka","getting data..");
                            }
                            myListAdapter.notifyDataSetChanged();
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