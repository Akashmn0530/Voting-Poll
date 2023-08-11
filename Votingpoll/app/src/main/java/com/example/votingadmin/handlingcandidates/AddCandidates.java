package com.example.votingadmin.handlingcandidates;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCandidates extends Fragment {
    private EditText candidateNameEditText, candidateAadhaarEditText;
    private AddCandidatesClass addCandidatesClass;
    private FirebaseFirestore db;

    private static final int MIN_NAME_LENGTH = 3;
    private static final String COLLECTION_NAME = "PollData";

    public AddCandidates() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_candidates, container, false);
        candidateNameEditText = view.findViewById(R.id.pollname);
        candidateAadhaarEditText = view.findViewById(R.id.pollaadhar);
        Button addButton = view.findViewById(R.id.polladd);
        Button viewButton = view.findViewById(R.id.pollView);
        Button cancelButton = view.findViewById(R.id.pollcancel);

        addCandidatesClass = new AddCandidatesClass();
        db = FirebaseFirestore.getInstance();

        addButton.setOnClickListener(view1 -> addCandidate());

        viewButton.setOnClickListener(view12 -> {
            ViewCandidates vUser = new ViewCandidates();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.viewfragmentContainer4, vUser)
                    .commit();
        });

        cancelButton.setOnClickListener(view13 -> clearFields());

        return view;
    }

    private void addCandidate() {
        String aadhar = candidateAadhaarEditText.getText().toString();
        String fullname = candidateNameEditText.getText().toString();

        if (TextUtils.isEmpty(fullname) || fullname.length() < MIN_NAME_LENGTH) {
            candidateNameEditText.setError("Invalid name!!!");
        } else if (TextUtils.isEmpty(aadhar)) {
            candidateAadhaarEditText.setError("Invalid aadhaar no.!!!");
        } else {
            addDataToFirestore(fullname, aadhar);
        }
    }

    private void addDataToFirestore(String fname, String aadhar) {
        addCandidatesClass.setaFullname(fname);
        addCandidatesClass.setaAadhaar(aadhar);

        db.collection(COLLECTION_NAME).document(aadhar)
                .set(addCandidatesClass)
                .addOnSuccessListener(unused -> {
                    clearFields();
                    Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show());
    }

    private void clearFields() {
        candidateNameEditText.setText("");
        candidateAadhaarEditText.setText("");
    }
}
