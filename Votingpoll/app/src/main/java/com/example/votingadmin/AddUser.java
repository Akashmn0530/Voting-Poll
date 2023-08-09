package com.example.votingadmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUser extends Fragment {
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private AddUserData eAddedData;
    private EditText fulln,eAadharno;

    public AddUser() {
// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        fulln = rootView.findViewById(R.id.userfullname);
        eAadharno = rootView.findViewById(R.id.useraadhar);
        progressBar = rootView.findViewById(R.id.userprogressBar);
        Button adduser = rootView.findViewById(R.id.useraddfragment);
        Button viewuser = rootView.findViewById(R.id.userviewfragment);
        Button canceluser = rootView.findViewById(R.id.usercancelfragment);

// For Firestore
        db = FirebaseFirestore.getInstance();

        eAddedData = new AddUserData();

        adduser.setOnClickListener(view -> addNewUser());

        canceluser.setOnClickListener(view -> clearFields());

        viewuser.setOnClickListener(view -> {
// Add the child fragment to the container view
            Log.d("Aka","view user button");
            ViewUser vUser = new ViewUser();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.viewfragmentContainer3, vUser)
                    .commit();
        });

        return rootView;
    }

    private void addNewUser() {
// Show the visibility of the progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

// Take the value of edit texts in Strings
        String fullname = fulln.getText().toString().trim();
        String aadhar = eAadharno.getText().toString().trim();

// Validations for input

        if (TextUtils.isEmpty(fullname) || fullname.length() < 3) {
            fulln.setError("Invalid name!");
            progressBar.setVisibility(View.GONE);
        }

        else if (TextUtils.isEmpty(aadhar)) {
            eAadharno.setError("Invalid Aadhar number!");
            progressBar.setVisibility(View.GONE);
        }
        else {
            addUserDatatoFirestore(fullname, aadhar);
        }
    }

    private void addUserDatatoFirestore(String fname, String aadhar) {
        eAddedData.setauFullname(fname);
        eAddedData.setauAadhaar(aadhar);

        db.collection("AddUser").document(aadhar)
                .set(eAddedData).addOnSuccessListener(unused -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        fulln.setText("");
        eAadharno.setText("");
    }
}