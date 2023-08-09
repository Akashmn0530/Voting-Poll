package com.example.votingadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;
public class EditUserActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    AddUserData userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = FirebaseFirestore.getInstance();
        userId = (AddUserData) getIntent().getSerializableExtra("userId1");
        Log.d("Akash","EditUserClass"+userId);
        if (userId != null) {
            // Use the user ID to fetch the user data from Firestore and display it in the activity
            fetchUserData(userId);
            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setOnClickListener(view -> {
                // Handle the Save button click to update the Firestore data
                onSaveButtonClick();
            });
        } else {
            // Handle case when user ID is not available
            Toast.makeText(this, "User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchUserData(AddUserData userId) {
        String id = userId.getAuAadhaar();
        Log.d("Akash",id+"line 59");
        db.collection("AddUser")
                .whereEqualTo("auAadhaar", id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String,Object> server1 = document.getData();
                            EditText fullNameEditText = findViewById(R.id.editFullName);
                            TextView aadharEditText = findViewById(R.id.editAadhar);

                            fullNameEditText.setText(String.valueOf(server1.get("auFullname")));
                            aadharEditText.setText(String.valueOf(server1.get("auAadhaar")));
                            //String ab = server1.get();

                        }
                    } else {
                        Toast.makeText(EditUserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Implement the updateFirestoreData method to update the Firestore data with edited user data
    private void updateFirestoreData(AddUserData userId, String fullName, String aadhar) {
        // Use the userId to update the Firestore data
        AddUserData userData = new AddUserData();
        userData.setauAadhaar(aadhar);
        userData.setauFullname(fullName);
        db.collection("AddUser").document(userId.getAuAadhaar()).
                set(userData)
                .addOnSuccessListener(unused -> Toast.makeText(EditUserActivity.this, "Successfully updated...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(EditUserActivity.this, "Failed to update...", Toast.LENGTH_SHORT).show());
    }

    // Implement the method to handle the Save button click to update the Firestore data
    public void onSaveButtonClick() {
        // Get the updated data from the edit text fields
        String fullName = ((EditText)findViewById(R.id.editFullName)).getText().toString();
        String aadhar = ((TextView)findViewById(R.id.editAadhar)).getText().toString();

        // Get other fields as needed

        if (userId != null) {
            updateFirestoreData(userId, fullName, aadhar); // Call the updateFirestoreData method to update data
        }
    }
}