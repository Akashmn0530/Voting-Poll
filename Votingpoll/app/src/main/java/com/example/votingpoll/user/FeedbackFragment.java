package com.example.votingpoll.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votingadmin.ViewFeedback;
import com.example.votingpoll.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackFragment extends Fragment {

    public FeedbackFragment() {    }
    RatingBar ratingBar;
    Button getRating;
    EditText description;
    FirebaseFirestore db;
    ViewFeedback viewFeedback;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRating = getView().findViewById(R.id.getRating);
        ratingBar = getView().findViewById(R.id.rating);
        description = getView().findViewById(R.id.editTextTextMultiLine);
        db = FirebaseFirestore.getInstance();
        viewFeedback = new ViewFeedback();
        getRating.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String des = description.getText().toString();
            Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_LONG).show();
            updateUserStar(rating,des);

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }
    void updateUserStar(float rating,String des){
        String uidpass = Login.uidpass;
        //int rating1 = Integer.parseInt(rating);
        if (uidpass != null) {
            DocumentReference update1 = db.collection("UserData").document(uidpass);
            //Update DB
            update1
                    .update("auRating", rating,
                            "auFeedbackDescription", des
                    )
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                        Log.d("Akash", "DocumentSnapshot successfully updated!");
                        addDatatoFireStore(rating,des,uidpass);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        Log.w("Akash", "Error updating document", e);
                    });
            //To update Admin's database
        }

    }
    private void addDatatoFireStore(float ratings, String des,String uid) {
        // below 3 lines of code is used to set
        // data in our object class.
        viewFeedback.setViewRating(ratings);
        viewFeedback.setViewDescription(des);
        //DocumentReference newDB = db.collection("PollData").document();
        // Add a new document with a generated ID

        db.collection("FeedbackDB").document(uid)
                .set(viewFeedback).addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show());
    }
}