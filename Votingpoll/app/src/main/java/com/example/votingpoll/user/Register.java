package com.example.votingpoll.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    ServerData userData;
    EditText fulln,usern,passw,cpassword,eMobile,eAddress,eAadharno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fulln = (EditText)findViewById(R.id.fullname);
        usern = (EditText)findViewById(R.id.username);
        passw = (EditText)findViewById(R.id.password);
        eMobile = (EditText)findViewById(R.id.mobile);
        eAadharno = (EditText)findViewById(R.id.aadhar);
        eAddress = (EditText)findViewById(R.id.address);
        cpassword = (EditText)findViewById(R.id.confirmpassword);
        progressBar = findViewById(R.id.progressBar);
        Button signbtn = (Button)findViewById(R.id.signbtn);

        //for firestore
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userData = new ServerData();
        signbtn.setOnClickListener(new View.OnClickListener(){
                                       @Override
                                       public void onClick(View view) {
                                           registerNewUser();
                                       }
                                   }

        );
        //for login text in reg page
        TextView logintxtbtn = findViewById(R.id.logintxtbtn);
        logintxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        //getSupportActionBar().setTitle("Akash");
    }
    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password, fullname, address, conpassword,aadhar;
        long mobile;
        email = usern.getText().toString();
        password = passw.getText().toString();
        fullname = fulln.getText().toString();
        mobile = Long.parseLong(eMobile.getText().toString());
        address = eAddress.getText().toString();
        aadhar = eAadharno.getText().toString();
        conpassword = cpassword.getText().toString();

        // Validations for input
        if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usern.setError("Invalid mail!!!");
            return;
        }
        else if (!validatePassword(password,conpassword,passw)) {
            return;
        }
        else if (TextUtils.isEmpty(fullname) || fullname.length()<3) {
            fulln.setError("Invalid name!!!");
            return;
        }
        else if (!(String.valueOf(mobile).length() == 10)) {
            eMobile.setError("Invalid mobile no!!!");
            return;
        }
        else if (TextUtils.isEmpty(address)) {
            eAddress.setError("Invalid address!!!");
            return;
        }
        else if (TextUtils.isEmpty(aadhar)) {
            eAadharno.setError("Invalid aadhaar no.!!!");
            return;
        }
        ////////////////////
        else {
            checkAadharNumberInFireStore(aadhar, new AadharCheckCallback() {
                @Override
                public void onAadharExists(boolean exists) {
                    if (exists) {

                        // create new user or register new user
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Registration successful!",
                                                    Toast.LENGTH_LONG).show();
                                            // hide the progress bar
                                            progressBar.setVisibility(View.GONE);
                                            //firestore
                                            addDatatoFireStore(fullname, email, address, mobile, aadhar);
                                            // if the user created intent to login activity
                                            Intent intent = new Intent(Register.this, Login.class);
                                            startActivity(intent);
                                        } else {

                                            // Registration failed
                                            Toast.makeText(
                                                            getApplicationContext(),
                                                            "Registration failed!!"
                                                                    + " Please try again later",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            // hide the progress bar
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                    else {
                        usern.setText("");
                        passw.setText("");
                        eAadharno.setText("");
                        eAddress.setText("");
                        eMobile.setText("");
                        fulln.setText("");
                        cpassword.setText("");
                        Toast.makeText(Register.this, "Your not eligible...", Toast.LENGTH_SHORT).show();
                        // hide the progress bar
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });
        }
        ///////////////////

    }

    private void checkAadharNumberInFireStore(String aadhar, AadharCheckCallback callback){
        CollectionReference collectionReference = db.collection("AddUser");
        Query aadharQuery = collectionReference.whereEqualTo("auAadhaar", aadhar);
        aadharQuery.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                boolean aadharExists = false;
                if(querySnapshot != null){
                    for(DocumentSnapshot document : querySnapshot.getDocuments()){
                        if(document.exists()) {
                            aadharExists = true;
                            break;
                        }
                    }
                }
                if (aadharExists) Toast.makeText(this, "Addhar no. Exists", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Aadhar no. is not exists in the database", Toast.LENGTH_SHORT).show();
                callback.onAadharExists(aadharExists);
            }
        });

    }

    public boolean validatePassword(String passwordInput,String cpass, EditText password) {
        // defining our own password pattern
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$");
        if (passwordInput.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }
        if(!passwordInput.equals(cpass)){
            password.setError("password and confirm password both should be same");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    private void addDatatoFireStore(String fname, String email, String address, long mobile,String aadhar) {

        //CollectionReference dbUserData = db.collection("UserData");
        ServerData userData1 = new ServerData();
        userData1.setauAadhaar(aadhar);
        userData1.setauAddress(address);
        userData1.setauEmail(email);
        userData1.setauFullname(fname);
        userData1.setauMobile(mobile);
        // Add a new document with a generated ID
        db.collection("UserData").document(email)
                .set(userData1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Register.this, "Success...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed...", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}