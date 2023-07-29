package com.example.votingadmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUser extends Fragment {
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ServerData eAddedData;
    private Button adduser, viewuser, canceluser;
    private EditText fulln, usern, eMobile, eAddress, eAadharno;

    public AddUser() {
// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        fulln = rootView.findViewById(R.id.userfullname);
        usern = rootView.findViewById(R.id.useremail);
        eMobile = rootView.findViewById(R.id.usermobile);
        eAadharno = rootView.findViewById(R.id.useraadhar);
        eAddress = rootView.findViewById(R.id.useraddress);
        progressBar = rootView.findViewById(R.id.userprogressBar);
        adduser = rootView.findViewById(R.id.useraddfragment);
        viewuser = rootView.findViewById(R.id.userviewfragment);
        canceluser = rootView.findViewById(R.id.usercancelfragment);

// For Firestore
        db = FirebaseFirestore.getInstance();
// Taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        eAddedData = new ServerData();

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
            }
        });

        canceluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Add the child fragment to the container view
                Log.d("Aka","view user button");
                ViewUser vUser = new ViewUser();
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.viewfragmentContainer3, vUser)
                        .commit();
            }
        });

        return rootView;
    }

    private void addNewUser() {
// Show the visibility of the progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

// Take the value of edit texts in Strings
        String email = usern.getText().toString().trim();
        String fullname = fulln.getText().toString().trim();
        long mobile = Long.parseLong(eMobile.getText().toString().trim());
        String address = eAddress.getText().toString().trim();
        long aadhar = Long.parseLong(eAadharno.getText().toString().trim());

// Validations for input
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usern.setError("Invalid email address!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(fullname) || fullname.length() < 3) {
            fulln.setError("Invalid name!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!(String.valueOf(mobile).length() == 10)) {
            eMobile.setError("Invalid mobile number!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            eAddress.setError("Invalid address!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!(String.valueOf(aadhar).length() == 12)) {
            eAadharno.setError("Invalid Aadhar number!");
            progressBar.setVisibility(View.GONE);
            return;
        }

        addUserDatatoFirestore(fullname, email, address, mobile, aadhar);
    }

    private void addUserDatatoFirestore(String fname, String email, String address, long mobile, long aadhar) {
        eAddedData.setauFullname(fname);
        eAddedData.setauEmail(email);
        eAddedData.setauMobile(mobile);
        eAddedData.setauAddress(address);
        eAddedData.setauAadhaar(aadhar);

        db.collection("AddUser").document(email)
                .set(eAddedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        fulln.setText("");
        usern.setText("");
        eMobile.setText("");
        eAadharno.setText("");
        eAddress.setText("");
    }
}