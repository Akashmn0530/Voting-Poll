package com.example.votingadmin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpoll.user.ServerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;
import com.example.votingpoll.R;

public class AdminRegister extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseFirestore db;
    ServerData userData;
    EditText fulln,usern,passw,cpassword,eMobile,eAddress,eAadharno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
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
                startActivity(new Intent(getApplicationContext(),AdminLogin.class));
            }
        });
        //getSupportActionBar().setTitle("Akash");
    }
    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password, aadhar, fullname, address, conpassword;
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
        } else if (!validatePassword(password, conpassword, passw)) {
            return;
        } else if (TextUtils.isEmpty(fullname) || fullname.length() < 3) {
            fulln.setError("Invalid name!!!");
            return;
        } else if (!(String.valueOf(mobile).length() == 10)) {
            eMobile.setError("Invalid mobile no!!!");
            return;
        } else if (TextUtils.isEmpty(address)) {
            eAddress.setError("Invalid address!!!");
            return;
        }
        else if (TextUtils.isEmpty(aadhar)) {
            eAddress.setError("Invalid address!!!");
            return;}
        else {
            // hide the progress bar
            progressBar.setVisibility(View.GONE);
            //firestore
            addDatatoFireStore(fullname, email, address, mobile, aadhar, password);
        }
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
    private void addDatatoFireStore(String fname, String email, String address, long mobile,String aadhar,String passwd) {

        AdminAddedData adminAddedData = new AdminAddedData();
        adminAddedData.setaAadhaar(aadhar);
        adminAddedData.setaAddress(address);
        adminAddedData.setaEmail(email);
        adminAddedData.setaMobile(mobile);
        adminAddedData.setaFullname(fname);
        adminAddedData.setaPassword(passwd);
        // Add a new document with a generated ID
        db.collection("AdminData").document(aadhar)
                .set(adminAddedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // if the user created intent to login activity
                        Intent intent = new Intent(AdminRegister.this, AdminLogin.class);
                        startActivity(intent);
                        Toast.makeText(AdminRegister.this, "Signed up Successfully...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminRegister.this, "Failed please try again...", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}