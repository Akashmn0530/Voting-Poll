package com.example.votingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditUserActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = FirebaseFirestore.getInstance();

        String userId = getIntent().getStringExtra("userId");
        Log.d("Akash","EditUserClass"+userId);
        if (userId != null) {
            // Use the user ID to fetch the user data from Firestore and display it in the activity
            fetchUserData(userId);
//            EditText fullNameEditText = findViewById(R.id.editFullName);
//            EditText emailEditText = findViewById(R.id.editEmail);
//            EditText mobileEditText = findViewById(R.id.editMobile);
//            EditText addressEditText = findViewById(R.id.editAddress);
//            EditText aadharEditText = findViewById(R.id.editAadhar);
//            fullNameEditText.setText("Akash");
//            emailEditText.setText("serverData.getAuEmail()");
//            mobileEditText.setText(String.valueOf(1234567887l));
//            addressEditText.setText("serverData.getAuAddress()");
//            aadharEditText.setText(String.valueOf(121212789012l));
            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the Save button click to update the Firestore data
                    onSaveButtonClick();
                }
            });
        } else {
            // Handle case when user ID is not available
            Toast.makeText(this, "User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void fetchUserData(String userId) {
        db.collection("AddUser").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ServerData serverData = documentSnapshot.toObject(ServerData.class);
                            if (serverData != null) {
                                EditText fullNameEditText = findViewById(R.id.editFullName);
                                EditText emailEditText = findViewById(R.id.editEmail);
                                EditText mobileEditText = findViewById(R.id.editMobile);
                                EditText addressEditText = findViewById(R.id.editAddress);
                                EditText aadharEditText = findViewById(R.id.editAadhar);

                                fullNameEditText.setText(serverData.getAuFullname());
                                emailEditText.setText(serverData.getAuEmail());
                                mobileEditText.setText(String.valueOf(serverData.getAuMobile()));
                                addressEditText.setText(serverData.getAuAddress());
                                aadharEditText.setText(String.valueOf(serverData.getAuAadhaar()));
                            }
                        } else {
                            Toast.makeText(EditUserActivity.this, "User not found in database.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "Failed to fetch user data.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    // Implement the updateFirestoreData method to update the Firestore data with edited user data
    private void updateFirestoreData(String userId, String fullName, String email, long mobile, String address, long aadhar) {
// Use the userId to update the Firestore data
        DocumentReference userRef = db.collection("AddUser").document(userId);
        userRef.update(
                        "auFullname", fullName,
                        "auEmail", email,
                        "auMobile", mobile,
                        "auAddress", address,
                        "auAadhaar", aadhar
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUserActivity.this, "User data updated successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "Failed to update user data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Implement the method to handle the Save button click to update the Firestore data
    public void onSaveButtonClick() {
// Get the updated data from the edit text fields
        String fullName = ((EditText)findViewById(R.id.editFullName)).getText().toString();
        String email = ((EditText)findViewById(R.id.editEmail)).getText().toString();
        long mobile = Long.parseLong(((EditText)findViewById(R.id.editMobile)).getText().toString());
        String address = ((EditText)findViewById(R.id.editAddress)).getText().toString();
        long aadhar = Long.parseLong(((EditText)findViewById(R.id.editAadhar)).getText().toString());

// Get other fields as needed

        String userId = getIntent().getStringExtra("userId");
        if (userId != null) {
            updateFirestoreData(userId, fullName, email, mobile, address, aadhar); // Call the updateFirestoreData method to update data
        }
    }
}