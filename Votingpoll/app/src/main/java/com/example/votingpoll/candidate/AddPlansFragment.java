package com.example.votingpoll.candidate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddPlansFragment extends Fragment {
    String plans;
    EditText plansEdittext,partySelect;
    Button addPlansBtn;
    FirebaseFirestore db;
    ImageView imageView;
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference storageReference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plansEdittext = getView().findViewById(R.id.addPlans123);
        addPlansBtn = getView().findViewById(R.id.addPlansBtn);
        partySelect = getView().findViewById(R.id.partySelect);
        imageView = getView().findViewById(R.id.imageViewParty);
        imageView.setOnClickListener(v -> selectImage());
        String aadhar = CandiLogin.cidpass;
        db = FirebaseFirestore.getInstance();
        fetchTheData(aadhar);
        addPlansBtn.setOnClickListener(view1 -> {
            String party = partySelect.getText().toString();
            plans = plansEdittext.getText().toString();
            if (TextUtils.isEmpty(party) || TextUtils.isEmpty(plans)) {
                partySelect.setError("Enter Party!!!");
                plansEdittext.setError("Enter Manifesto!!!");
            }else {
                try {
                    uploadIamage(aadhar);
                    updateData(plans, aadhar, party);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Please upload Image...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            assert data != null;
            if (data.getData() != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
    private void uploadIamage(String id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();
        id = id+"Party";
        storageReference = FirebaseStorage.getInstance().getReference("Party_Images/"+id);
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

            imageView.setImageURI(null);
            Toast.makeText(getActivity(), "Successfully uploaded", Toast.LENGTH_SHORT).show();

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }).addOnFailureListener(e -> {

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Toast.makeText(getActivity(), "Failed to upload", Toast.LENGTH_SHORT).show();
        });

    }

    void updateData(String plans,String aadhar,String party){
        if (aadhar != null) {
            // Use the userId to update the Firestore data
            DocumentReference update1 = db.collection("CandiData").document(aadhar);
            //Update DB
            update1.update("aucPlans",plans,
                            "partyName",party
                    )
                    .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());
        }
    }

    void fetchTheData(String cid){
        DocumentReference docRef = db.collection("CandiData").document(cid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    CandiData c = document.toObject(CandiData.class);
                    assert c != null;
                    plansEdittext.setText(c.getAucPlans());
                } else {
                    Log.d("Akash", "No Data");
                }
            } else {
                Log.d("Akash", "get failed with ", task.getException());
            }
        });
    }

    public AddPlansFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plans, container, false);
    }
}