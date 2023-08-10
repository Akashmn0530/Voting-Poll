package com.example.votingpoll.user;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.example.votingpoll.candidate.CandiData;
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


public class FinalVotePage extends Fragment {
    TextView name, party;
    String idValue;
    ScrollView scrollView;
    Button voteBTN;
    ImageView profileIMG, partyIMG;
    StorageReference storageReference;
    FirebaseFirestore db;
    String idAadhaar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getView().findViewById(R.id.pnameFinal);
        party = getView().findViewById(R.id.pPartyFinal);
        voteBTN = getView().findViewById(R.id.pVote);
        scrollView = getView().findViewById(R.id.pScroll);
        profileIMG = getView().findViewById(R.id.profile_image);
        partyIMG = getView().findViewById(R.id.party_image);
        db = FirebaseFirestore.getInstance();
        idValue = Login.uidpass;
        idAadhaar = VotingPageFragment.toGetId;
        fetchImage(idAadhaar);
        fetchTheData(idAadhaar);
        voteBTN.setOnClickListener(view1 -> {
            updateVote(idAadhaar);
            updateStatus(idValue);
            showThankYouPopup(view);
        });
    }


    private void showThankYouPopup(View rootView) {
        // Inflate the popup layout
        LayoutInflater inflater = LayoutInflater.from(getActivity()); // Use the activity's context
        View popupView = inflater.inflate(R.layout.popup_thank_you, null);

        // Create a new PopupWindow
        int popupWidth = 900; // Replace with your desired width in pixels
        int popupHeight = 1000; // Replace with your desired height in pixels

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                popupWidth,
                popupHeight,
                true // Focusable
        );



        // Set the popup's position
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Set a dismiss listener for the popup
        popupWindow.setOnDismissListener(() -> {
            // Handle actions when the popup is dismissed
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
                            profileIMG.setImageBitmap(bitmap);

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


        ///
        storageReference = FirebaseStorage.getInstance().getReference("Party_Images/"+s+"Party");
        try {
            File localfile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            partyIMG.setImageBitmap(bitmap);

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
                        name.setText(c.getAucFullname());
                        party.setText(String.valueOf(c.getPartyName()));
                        TextView textView = new TextView(getContext());
                        assert c != null;
                        textView.setText(c.getAucPlans());
                        scrollView.addView(textView);
                    } else {
                        Log.d("Akash", "No such document");
                    }
                } else {
                    Log.d("Akash", "get failed with ", task.getException());
                }
            }
        });
    }

    void updateVote(String cid){
        DocumentReference docRef = db.collection("CandiData").document(cid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        CandiData c = document.toObject(CandiData.class);
                        int count = c.getVoteCountCandi() + 1;
                        DocumentReference update1 = db.collection("CandiData").document(cid);
                        //Update DB
                        update1
                                .update("voteCountCandi", count
                                )
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        voteBTN.setEnabled(false);
                                        Toast.makeText(getActivity(), "Successfully voted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                        Log.w("Akash", "Error updating document", e);
                                    }
                                });
                    } else {
                        Log.d("Akash", "No such document");
                    }
                } else {
                    Log.d("Akash", "get failed with ", task.getException());
                }
            }
        });
    }

    void updateStatus(String status){
        DocumentReference update1 = db.collection("UserData").document(status);
        //Update DB
        update1
                .update("vote", "Voted",
                        "voteCount",1
                )
                .addOnSuccessListener(aVoid -> {
                    voteBTN.setEnabled(false);
                }).addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    Log.w("Akash", "Error updating document", e);
                });
    }
    public FinalVotePage() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_vote_page, container, false);
    }
}