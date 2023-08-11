package com.example.votingadmin;

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


public class AdminProfileFragment extends Fragment {
    TextView proName,proEmail,proMobile,proAddress;
    TextView proAadhar;
    Button proEdit;
    FirebaseFirestore db;

    ImageView profile_img;
    StorageReference storageReference;
    String uID;

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
    void fetchTheData(String id){
        DocumentReference docRef = db.collection("AdminData").document(id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Toast.makeText(getActivity(), "Successfully getting the data...", Toast.LENGTH_SHORT).show();
                    AdminAddedData c = document.toObject(AdminAddedData.class);
                    assert c != null;
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
        });
    }


    public AdminProfileFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        // Initialization of variables
        proAadhar = view.findViewById(R.id.pAadhar1);
        proAddress = view.findViewById(R.id.pAddress);
        proEmail = view.findViewById(R.id.pEmail);
        proMobile = view.findViewById(R.id.pmobile);
        proName = view.findViewById(R.id.pname);
        proEdit = view.findViewById(R.id.pEdit);
        db = FirebaseFirestore.getInstance();
        profile_img = view.findViewById(R.id.profile_image);
        uID = AdminLogin.logID;
        fetchTheData(uID);
        fetchImage(uID);
        AdminProfileFragmentEdit adminProfileFragmentEdit=new AdminProfileFragmentEdit();

        proEdit.setOnClickListener(view1 -> {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutContainer23, adminProfileFragmentEdit)
                    .commit();
        });
        return view;
    }
}