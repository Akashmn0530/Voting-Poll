package com.example.votingpoll.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StatusFragment extends Fragment {

    public StatusFragment(){ }
    TextView name, status, count;
    FirebaseFirestore db;
    StorageReference storageReference;
    ImageView profile_img;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getView().findViewById(R.id.statusName);
        status = getView().findViewById(R.id.statusStatus);
        count = getView().findViewById(R.id.statusCount);
        profile_img = getView().findViewById(R.id.profile_image);
        db = FirebaseFirestore.getInstance();
        fetchImage(Login.uidpass);
        fetchData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    void fetchData(){
        String id = Login.uidpass;
        Log.d("Akash","profile 70"+id);
        DocumentReference docRef = db.collection("UserData").document(id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                    Log.d("Akash", "DocumentSnapshot data: " + document.getData());
                    ServerData c = document.toObject(ServerData.class);
                    Log.d("Akash","setting data...");
                    assert c != null;
                    name.setText(c.getAuFullname());
                    status.setText(c.getVote());
                    count.setText(String.valueOf(c.getVoteCount()));


                } else {
                    Log.d("Akash", "No such document");
                }
            } else {
                Log.d("Akash", "get failed with ", task.getException());
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
}