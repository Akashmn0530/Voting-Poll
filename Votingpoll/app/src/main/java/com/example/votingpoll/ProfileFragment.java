package com.example.votingpoll;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {
    EditText proName,proEmail,proMobile,proAddress;
    TextView proAadhar;
    Button proEdit;
    FirebaseFirestore db;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialization of variables
        proAadhar = getView().findViewById(R.id.pAadhar);
        proAddress = getView().findViewById(R.id.pAddress);
        proEmail = getView().findViewById(R.id.pEmail);
        proMobile = getView().findViewById(R.id.pmobile);
        proName = getView().findViewById(R.id.pname);
        proEdit = getView().findViewById(R.id.pEdit);
        db = FirebaseFirestore.getInstance();
        // Getting Intent...
        String email =  Login.emailId;

        //Calling fetchData method...
        fetchTheData(email);
        proEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    void updateData(){
        // Get the updated data from the edit text fields
        String fullName = (proName).getText().toString();
        String email = (proEmail).getText().toString();
        long mobile = Long.parseLong((proMobile).getText().toString());
        String address = (proAddress).getText().toString();
       // long aadhar = Long.parseLong((proAddress).getText().toString());

        // Get other fields as needed

        if (email != null) {
            // Use the userId to update the Firestore data
//            ServerData proUpdate = new ServerData();
//            proUpdate.setauAddress(address);
//            proUpdate.setauEmail(email);
//            proUpdate.setauFullname(fullName);
//            proUpdate.setauMobile(mobile);

            DocumentReference update1 = db.collection("UserData").document(email);
            //Update DB
            update1
                    .update("auFullname", fullName,
                    "auEmail",email,
                            "auAddress",address,
                            "auMobile",mobile
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

    void fetchTheData(String email){
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
                        proName.setText(c.getAuFullname());
                        proMobile.setText(String.valueOf(c.getAuMobile()));
                        proEmail.setText(c.getAuEmail());
                        proAadhar.setText(String.valueOf(c.getAuAadhaar()));
                        proAddress.setText(c.getAuAddress());
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