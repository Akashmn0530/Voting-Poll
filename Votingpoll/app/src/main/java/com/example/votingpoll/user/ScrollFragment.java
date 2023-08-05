package com.example.votingpoll.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingadmin.TermsAndConditions;
import com.example.votingpoll.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScrollFragment extends Fragment {
    FirebaseFirestore db;
    ScrollView scrollView;

    public ScrollFragment() { }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scroll, container, false);
        db = FirebaseFirestore.getInstance();
        scrollView = view.findViewById(R.id.termsScrollView);
        fetchTheData();
        return view;
    }
    void fetchTheData(){
        DocumentReference docRef = db.collection("termsData").document("terms");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                        TermsAndConditions c = document.toObject(TermsAndConditions.class);
                        Log.d("Akash","setting data...");
                        TextView textView = new TextView(getContext());
                        textView.setText(c.getTcData());
                        scrollView.addView(textView);
                    } else {
                        Log.d("Akash", "No such document");
                    }
                } else {
                    Log.d("Akash", "get failed with ", task.getException());
                }
            }
        });
    }
}