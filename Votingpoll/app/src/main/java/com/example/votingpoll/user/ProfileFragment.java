package com.example.votingpoll.user;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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


public class ProfileFragment extends Fragment {
    TextView proName,proEmail,proMobile,proAddress;
    TextView proAadhar;
    Button proEdit;
    FirebaseFirestore db;

    ImageView profile_img;
    StorageReference storageReference;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        proAadhar = view.findViewById(R.id.pAadhar1);
        proAddress = view.findViewById(R.id.pAddress);
        proEmail = view.findViewById(R.id.pEmail);
        proMobile = view.findViewById(R.id.pmobile);
        proName = view.findViewById(R.id.pname);
        proEdit = view.findViewById(R.id.pEdit);
        db = FirebaseFirestore.getInstance();
        profile_img = view.findViewById(R.id.profile_image);
        // Getting Intent...
        String id =  Login.uidpass;
        //Calling fetchData method...
        fetchTheData(id);
        fetchImage(id);
        UserProfileFragmentEdit userProfileFragmentEdit=new UserProfileFragmentEdit();
        proEdit.setOnClickListener(view1 -> {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutContainer2, userProfileFragmentEdit)
                    .commit();
        });
        return view;
    }


    void fetchTheData(String id){
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