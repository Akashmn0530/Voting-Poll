package com.example.votingpoll.candidate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.example.votingpoll.user.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class CandiFeedbackFragment extends Fragment {

    public CandiFeedbackFragment() {
        // Required empty public constructor
    }
    RatingBar ratingBar;
    Button getRating;
    FirebaseFirestore db;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRating = getView().findViewById(R.id.getRating);
        ratingBar = getView().findViewById(R.id.rating);
        db = FirebaseFirestore.getInstance();
        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(getActivity(), rating+"", Toast.LENGTH_LONG).show();
                updateUserStar(rating);

            }
        });
    }

    void updateUserStar(float rating){
        String email = CandiLogin.emailId;
        //int rating1 = Integer.parseInt(rating);
        if (email != null) {
            DocumentReference update1 = db.collection("CandiData").document(email);
            //Update DB
            update1
                    .update("aucRating", rating
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                            Log.d("Akash", "DocumentSnapshot successfully updated!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            Log.w("Akash", "Error updating document", e);
                        }
                    });
            //To update Admin's database
            DocumentReference update2 = db.collection("PollData").document(email);
            //Update DB
            update2
                    .update("aRating", rating
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                            Log.d("Akash", "DocumentSnapshot successfully updated!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            Log.w("Akash", "Error updating document", e);
                        }
                    });
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candi_feedback, container, false);
    }
}