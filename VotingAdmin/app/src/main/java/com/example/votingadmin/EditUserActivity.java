package com.example.votingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    ServerData userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = FirebaseFirestore.getInstance();
        userId = (ServerData) getIntent().getSerializableExtra("userId1");
        Log.d("Akash",userId.getAuEmail()+"Edit class 37 line...");
        Log.d("Akash","EditUserClass"+userId);
        if (userId != null) {
            // Use the user ID to fetch the user data from Firestore and display it in the activity
            fetchUserData(userId);
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

    private void fetchUserData(ServerData userId) {
         String mail = userId.getAuEmail();
         Log.d("Akash",mail+"line 59");
                db.collection("AddUser")
                        .whereEqualTo("auEmail", mail)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String,Object> server1 = document.getData();
                                        EditText fullNameEditText = findViewById(R.id.editFullName);
                                        EditText emailEditText = findViewById(R.id.editEmail);
                                        EditText mobileEditText = findViewById(R.id.editMobile);
                                        EditText addressEditText = findViewById(R.id.editAddress);
                                        EditText aadharEditText = findViewById(R.id.editAadhar);

                                        fullNameEditText.setText(String.valueOf(server1.get("auFullname")));
                                        emailEditText.setText(String.valueOf(server1.get("auEmail")));
                                        mobileEditText.setText(String.valueOf(server1.get("auMobile")));
                                        addressEditText.setText(String.valueOf(server1.get("auAddress")));
                                        aadharEditText.setText(String.valueOf(server1.get("auAadhaar")));
                                        //String ab = server1.get();

                                    }
                                } else {
                                    Toast.makeText(EditUserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    // Implement the updateFirestoreData method to update the Firestore data with edited user data
    private void updateFirestoreData(ServerData userId, String fullName, String email, long mobile, String address, long aadhar) {
        // Use the userId to update the Firestore data
        ServerData updatePoll = new ServerData();
        updatePoll.setauAadhaar(aadhar);
        updatePoll.setauAddress(address);
        updatePoll.setauEmail(email);
        updatePoll.setauFullname(fullName);
        updatePoll.setauMobile(mobile);
        db.collection("PollData").document(userId.getAuId()).
                set(updatePoll)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this, "Successfully updated...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "Failed to update...", Toast.LENGTH_SHORT).show();
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

        if (userId != null) {
            updateFirestoreData(userId, fullName, email, mobile, address, aadhar); // Call the updateFirestoreData method to update data
        }
    }
}