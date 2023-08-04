package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackFragment extends Fragment {

    private RecyclerView voteRV;
    private ArrayList<ViewFeedback> viewFeedbacks;
    private FeedbackAdaptor feedbackAdaptor;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    public ViewFeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_feedback, container, false);
        voteRV = view.findViewById(R.id.your_recycler_view_id3);
        loadingPB = view.findViewById(R.id.idProgressBar1);

        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        Log.d("Aka","getting view");
        // Creating our new array list
        viewFeedbacks = new ArrayList<>();

        voteRV.setHasFixedSize(true);
        voteRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adding our array list
        feedbackAdaptor = new FeedbackAdaptor(getContext(), viewFeedbacks);

        voteRV.setAdapter(feedbackAdaptor); // Setting the adapter to the RecyclerView

        fetchUserDataFromFirestore();

        return view;
    }

    private void fetchUserDataFromFirestore() {
        loadingPB.setVisibility(View.VISIBLE);

        db.collection("FeedbackDB").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ViewFeedback c = d.toObject(ViewFeedback.class);
                               // c.setViewDescription(d.getId());
                                viewFeedbacks.add(c);
                                Log.d("Aka","getting data..");
                            }
                            feedbackAdaptor.notifyDataSetChanged();
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