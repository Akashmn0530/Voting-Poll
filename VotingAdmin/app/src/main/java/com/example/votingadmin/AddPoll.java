package com.example.votingadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPoll extends Fragment {
    EditText condName, condEmail, condMobile, condAddress, condAadhaar;
    Button condAdd, condView, condCancel;
    AddPollClass addPollClass;
    private FirebaseFirestore db;
    public AddPoll() {
// Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        condName = getView().findViewById(R.id.pollname);
        condEmail = getView().findViewById(R.id.pollemail);

        condMobile = getView().findViewById(R.id.pollmobile);
        condAddress = getView().findViewById(R.id.polladdress);

        condAadhaar = getView().findViewById(R.id.pollaadhar);
        condAdd = getView().findViewById(R.id.polladd);

        condView = getView().findViewById(R.id.pollView);
        condCancel = getView().findViewById(R.id.pollcancel);

        addPollClass = new AddPollClass();
        //for firestore
        db = FirebaseFirestore.getInstance();

        condAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCondidate();
            }
        });
        condView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the child fragment to the container view
                ViewPoll vUser = new ViewPoll();
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.viewfragmentContainer4, vUser)
                        .commit();
            }

        });
        condCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });


    }
    public void addCondidate () {
        // Take the value of two edit texts in Strings
        String email, password, fullname, address, conpassword;
        long mobile, aadhar;
        email = condEmail.getText().toString();
        fullname = condName.getText().toString();
        mobile = Long.parseLong(condMobile.getText().toString());
        address = condAddress.getText().toString();
        aadhar = Long.parseLong(condAadhaar.getText().toString());
        if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            condEmail.setError("Invalid mail!!!");
            return;
        }

        else if (TextUtils.isEmpty(fullname) || fullname.length() < 3) {
            condName.setError("Invalid name!!!");
            return;
        }
        else if (!(String.valueOf(mobile).length() == 10)) {
            condMobile.setError("Invalid mobile no!!!");
            return;
        }
        else if (TextUtils.isEmpty(address)) {
            condAddress.setError("Invalid address!!!");
            return;
        }
        else if (!(String.valueOf(aadhar).length() == 12)) {
            condAadhaar.setError("Invalid aadhaar no.!!!");
            return;
        }
        else{
            addDatatoFireStore(fullname, email, address, mobile, aadhar) ;
            }
    }


    private void addDatatoFireStore(String fname, String email, String address, long mobile,long aadhar) {
        // below 3 lines of code is used to set
        // data in our object class.
        addPollClass.setaFullname(fname);
        addPollClass.setaEmail(email);
        addPollClass.setaMobile(mobile);
        addPollClass.setaAddress(address);
        addPollClass.setaAadhaar(aadhar);
        //DocumentReference newDB = db.collection("PollData").document();
        // Add a new document with a generated ID
        db.collection("PollData")
                .add(addPollClass)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        clearFields();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_poll, container, false);
    }
    private void clearFields() {

        condName.setText("");
        condEmail.setText("");
        condAadhaar.setText("");
        condAddress.setText("");
        condMobile.setText("");
    }
}







