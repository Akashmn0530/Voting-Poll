package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.votingpoll.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.candidate.CandiData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewResultsFragment extends Fragment {
    private List<DocumentSnapshot> candidates = new ArrayList<>();
    private ResultsAdapter resultsAdapter;
    private RecyclerView recyclerView;
    // Assuming you have a ResultsAdapter that can work with Winner objects
    private List<CandiData> winners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_results, container, false);
        recyclerView = rootView.findViewById(R.id.your_recycler_view_id_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultsAdapter = new ResultsAdapter(winners);  // Update the adapter to work with winners
        recyclerView.setAdapter(resultsAdapter);
        retrieveAndSortCandidates();
        return rootView;
    }

    private void retrieveAndSortCandidates() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("CandiData")
                .orderBy("voteCountCandi", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            candidates.clear();
                            Log.d("Aka","Fetching...");
                            candidates.addAll(task.getResult().getDocuments());
                            // Sort candidates based on votes (Descending order)
                            Collections.sort(candidates, new Comparator<DocumentSnapshot>() {
                                @Override
                                public int compare(DocumentSnapshot doc1, DocumentSnapshot doc2) {
                                    Long votes1 = doc1.getLong("voteCountCandi");
                                    Long votes2 = doc2.getLong("voteCountCandi");
                                    if (votes1 != null && votes2 != null) {
                                        return votes2.compareTo(votes1);
                                    } else {
                                        return 0;
                                    }
                                }
                            });

                            // Clear the winners list and populate it with the top 3 candidates
                            winners.clear();
                            for (int i = 0; i < Math.min(3, candidates.size()); i++) {
                                DocumentSnapshot candidate = candidates.get(i);
                                String name = candidate.getString("aucFullname");
                                int voteCount = candidate.getLong("voteCountCandi").intValue();
                                String party = candidate.getString("partyName");
                                winners.add(new CandiData(name, voteCount, party));
                            }

                            resultsAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("Aka", "Not Fetching...");
                        }
                    }
                });
    }

}
