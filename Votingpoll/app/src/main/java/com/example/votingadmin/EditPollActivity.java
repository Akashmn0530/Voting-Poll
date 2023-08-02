package com.example.votingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.votingpoll.R;
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

public class EditPollActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    AddPollClass userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poll);
        db = FirebaseFirestore.getInstance();
        userId = (AddPollClass) getIntent().getSerializableExtra("userId");
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
    private void fetchUserData(AddPollClass userId) {
        String id = userId.getaAadhaar();
        db.collection("PollData")
                .whereEqualTo("aAadhaar", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String,Object> addPollClass1 = document.getData();
                                EditText fullNameEditText = findViewById(R.id.editFullName);
                                EditText aadharEditText = findViewById(R.id.editAadhar);

                                fullNameEditText.setText(String.valueOf(addPollClass1.get("aFullname")));
                                aadharEditText.setText(String.valueOf(addPollClass1.get("aAadhaar")));
                            }
                        } else {
                            Toast.makeText(EditPollActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    // Implement the updateFirestoreData method to update the Firestore data with edited user data
    private void updateFirestoreData(AddPollClass userId, String fullName,String aadhar) {
        // Use the userId to update the Firestore data
        AddPollClass updatePoll = new AddPollClass();
        updatePoll.setaAadhaar(aadhar);
        updatePoll.setaFullname(fullName);
        db.collection("PollData").document(userId.getaAadhaar()).
                set(updatePoll)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditPollActivity.this, "Successfully updated...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPollActivity.this, "Failed to update...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Implement the method to handle the Save button click to update the Firestore data
    public void onSaveButtonClick() {
// Get the updated data from the edit text fields
        String fullName = ((EditText)findViewById(R.id.editFullName)).getText().toString();
        String aadhar = ((EditText)findViewById(R.id.editAadhar)).getText().toString();

// Get other fields as needed

        if (userId != null) {
            updateFirestoreData(userId, fullName,aadhar); // Call the updateFirestoreData method to update data
        }
    }
}