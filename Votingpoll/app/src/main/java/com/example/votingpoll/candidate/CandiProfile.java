package com.example.votingpoll.candidate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.example.votingpoll.user.Login;
import com.example.votingpoll.user.ServerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class CandiProfile extends Fragment {
    EditText proName,proEmail,proMobile,proAddress;
    TextView proAadhar;
    Button proEdit;
    FirebaseFirestore db;
    ImageView profile_img;
    StorageReference storageReference;
    public CandiProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candi_profile, container, false);
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
        profile_img = getView().findViewById(R.id.profile_image);
        // Getting Intent...
        String cidpass =  CandiLogin.cidpass;
        // This callback will only be called when MyFragment is at least Started.
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent intent = new Intent(getContext(), HomeActivity.class);
//                startActivity(intent);
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);
        //Calling fetchData method...
        fetchTheData(cidpass);
        fetchImage(cidpass);
        proEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void fetchImage(String s) {
        storageReference = FirebaseStorage.getInstance().getReference("images/"+s);
        try {
            File localfile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            profile_img.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to retrieve", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void updateData(){
        // Get the updated data from the edit text fields
        String fullName = (proName).getText().toString();
        String email = (proEmail).getText().toString();
        long mobile = Long.parseLong((proMobile).getText().toString());
        String address = (proAddress).getText().toString();
        //String aadhar = (proAddress).getText().toString();
        // Get other fields as needed
        String aadhar = CandiLogin.cidpass;
        if (aadhar != null) {
            // Use the userId to update the Firestore data
            DocumentReference update1 = db.collection("CandiData").document(aadhar);
            //Update DB
            update1
                    .update("aucFullname", fullName,
                            "aucEmail",email,
                            "aucAddress",address,
                            "aucMobile",mobile
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

    void fetchTheData(String cid){
        DocumentReference docRef = db.collection("CandiData").document(cid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                        Log.d("Akash", "DocumentSnapshot data: " + document.getData());
                        CandiData c = document.toObject(CandiData.class);
                        Log.d("Akash","setting data...");
                        proName.setText(c.getAucFullname());
                        proMobile.setText(String.valueOf(c.getAucMobile()));
                        proEmail.setText(c.getAucEmail());
                        proAadhar.setText(String.valueOf(c.getAucAadhaar()));
                        proAddress.setText(c.getAucAddress());
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