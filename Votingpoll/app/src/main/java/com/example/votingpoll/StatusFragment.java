package com.example.votingpoll;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatusFragment extends Fragment {

    public StatusFragment(){ }
    TextView name, status, count;
    FirebaseFirestore db;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getView().findViewById(R.id.statusName);
        status = getView().findViewById(R.id.statusStatus);
        count = getView().findViewById(R.id.statusCount);
        db = FirebaseFirestore.getInstance();
        fetchData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    void fetchData(){
        String email = Login.emailId;
        Log.d("Akash","profile 70"+email);
        DocumentReference docRef = db.collection("UserData").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                        Log.d("Akash", "DocumentSnapshot data: " + document.getData());
                        ServerData c = document.toObject(ServerData.class);
                        Log.d("Akash","setting data...");
                        name.setText(c.getAuFullname());
                        status.setText(c.getVote());
                        count.setText(String.valueOf(c.getVoteCount()));
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