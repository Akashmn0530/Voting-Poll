package com.example.votingadmin;

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

import com.example.votingpoll.R;
import com.example.votingpoll.user.ServerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AdminProfileFragment extends Fragment {
    EditText proName,proEmail,proMobile,proAddress;
    TextView proAadhar;
    Button proEdit;
    FirebaseFirestore db;
    String uID;

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
        uID = AdminLogin.logID;
        fetchTheData(uID);
        proEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    void fetchTheData(String id){
        DocumentReference docRef = db.collection("AdminData").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                        AdminAddedData c = document.toObject(AdminAddedData.class);
                        Log.d("Akash","setting data...");
                        proName.setText(c.getaFullname());
                        proMobile.setText(String.valueOf(c.getaMobile()));
                        proEmail.setText(c.getaEmail());
                        proAadhar.setText(String.valueOf(c.getaAadhaar()));
                        proAddress.setText(c.getaAddress());
                    } else {
                        Log.d("Akash", "No such document");
                    }
                } else {
                    Log.d("Akash", "get failed with ", task.getException());
                }
            }
        });
    }

    void updateData(){
        // Get the updated data from the edit text fields
        String fullName = (proName).getText().toString();
        String email = (proEmail).getText().toString();
        long mobile = Long.parseLong((proMobile).getText().toString());
        String address = (proAddress).getText().toString();
        String aadhar = (proAadhar).getText().toString();
        // Get other fields as needed
        if (aadhar != null) {
            // Use the userId to update the Firestore data
            DocumentReference update1 = db.collection("AdminData").document(aadhar);
            //Update DB
            update1
                    .update("aFullname", fullName,
                            "aEmail",email,
                            "aAddress",address,
                            "aMobile",mobile
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
    public AdminProfileFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }
}