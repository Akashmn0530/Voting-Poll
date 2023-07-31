package com.example.votingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLogin extends AppCompatActivity {
    EditText uname,pword, message;
    Button button1;
    private ProgressBar progressbar;
    private FirebaseFirestore db;
    int counter = 3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        uname = (EditText)findViewById(R.id.username1);
        pword = (EditText)findViewById(R.id.password1);
        message = (EditText)findViewById(R.id.msg);
        button1 = (Button)findViewById(R.id.loginbtn);
        progressbar = findViewById(R.id.progressbar);

        //for firestore
        db = FirebaseFirestore.getInstance();
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loginUserAccount();

            }
        });
        Button buttonsignup = (Button) findViewById(R.id.signupbtn);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(), AdminRegister.class);
                startActivity(inte);
            }
        });
        //getSupportActionBar().setTitle("Akash");

    }

    private void loginUserAccount()
    {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = uname.getText().toString();
        password = pword.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        else {
            checkAadharNumberInFireStore(email, new EmailCheckCallback() {
                @Override
                public void onEmailExists(boolean exists) {
                    if (exists) {
                        //Fetch the data from AdminData DB and
                        // Check the user entered data and DB data should match or not...
                        fetchTheData(email,password);
                    }
                    else {
                        Toast.makeText(AdminLogin.this, "User not exists Sign up and try again...", Toast.LENGTH_SHORT).show();
                        Intent inte = new Intent(getApplicationContext(), AdminRegister.class);
                        startActivity(inte);
                        // hide the progress bar
                        progressbar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void checkAadharNumberInFireStore(String email, EmailCheckCallback callback){
        CollectionReference collectionReference = db.collection("AdminData");
        Query aadharQuery = collectionReference.whereEqualTo("aEmail", email);
        aadharQuery.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                boolean emailExists = false;
                if(querySnapshot != null){
                    for(DocumentSnapshot document : querySnapshot.getDocuments()){
                        if(document.exists()) {
                            emailExists = true;
                            break;
                        }
                    }
                }
                callback.onEmailExists(emailExists);
            }
        });

    }
    void fetchTheData(String email, String password){
        Log.d("Akash","199 "+email);
        DocumentReference docRef = db.collection("AdminData").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        AdminAddedData c = document.toObject(AdminAddedData.class);
                        String getEmail = c.getaEmail();
                        String getPass = c.getaPassword();
                        if(email.equals(getEmail) && password.equals(getPass)){
                            message.getText().clear();
                            message.setBackgroundColor(Color.TRANSPARENT);
                            counter = 3;
                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(AdminLogin.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                            // if sign-in is successful
                            // intent to home activity
                            Intent intent
                                    = new Intent(AdminLogin.this,
                                    HomeActivity.class);
                            startActivity(intent);
                        } else {

                            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                            pword.setError("wrong password...");
                            message.setVisibility(View.VISIBLE);
                            message.setBackgroundColor(Color.RED);
                            counter--;
                            message.setText(Integer.toString(counter));
                            if (counter == 0) {
                                button1.setEnabled(false);
                                new CountDownTimer(10000, 10) { //Set Timer for 10 seconds
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        button1.setEnabled(true);
                                        message.getText().clear();
                                        message.setBackgroundColor(Color.TRANSPARENT);
                                        counter = 3;
                                    }
                                }.start();
                            }
                            progressbar.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(AdminLogin.this, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminLogin.this, "get failed with "+ task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}