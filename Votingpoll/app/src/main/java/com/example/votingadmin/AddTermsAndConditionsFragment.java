package com.example.votingadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddTermsAndConditionsFragment extends Fragment {
    EditText editText;
    Button b;
    FirebaseFirestore db;
    TermsAndConditions termsAndConditions;

    public AddTermsAndConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText=getView().findViewById(R.id.editTextTextMultiLine);
        b=getView().findViewById(R.id.termsandconditionsbutton);
        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        termsAndConditions = new TermsAndConditions();
        b.setOnClickListener(v -> {
            String terms = editText.getText().toString();
            editText.setText("");
            addDatatoFireStore(terms);
        });
    }

    private void addDatatoFireStore(String terms) {
        // below 3 lines of code is used to set
        // data in our object class.
        termsAndConditions.setTcData(terms);
        //DocumentReference newDB = db.collection("PollData").document();
        // Add a new document with a generated ID

        db.collection("termsData").document("terms")
                .set(termsAndConditions).addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_terms_and_conditions, container, false);
    }

}