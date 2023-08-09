package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.votingpoll.R;
import com.example.votingpoll.user.Register;
import com.example.votingpoll.user.ServerData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Pattern;

public class AdminRegister extends AppCompatActivity {
    ImageView imageView;
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference storageReference;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    ServerData userData;
    EditText fulln,usern,passw,cpassword,eMobile,eAddress,eAadharno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        fulln = findViewById(R.id.fullname);
        usern = findViewById(R.id.username);
        passw = findViewById(R.id.password);
        eMobile = findViewById(R.id.mobile);
        eAadharno = findViewById(R.id.aadhar);
        eAddress = findViewById(R.id.address);
        cpassword = findViewById(R.id.confirmpassword);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        Button signbtn = findViewById(R.id.signbtn);

        //for firestore
        db = FirebaseFirestore.getInstance();

        userData = new ServerData();
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadIamage();
                registerNewUser();
            }
        });
        //for login text in reg page
        TextView logintxtbtn = findViewById(R.id.logintxtbtn);
        logintxtbtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AdminLogin.class)));
        imageView.setOnClickListener(v -> selectImage());
        //getSupportActionBar().setTitle("Akash");
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            assert data != null;
            if (data.getData() != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
    private void uploadIamage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();
        String fileName = eAadharno.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

            imageView.setImageURI(null);
            Toast.makeText(AdminRegister.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }).addOnFailureListener(e -> {

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Toast.makeText(AdminRegister.this, "Failed to upload", Toast.LENGTH_SHORT).show();
        });

    }
    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password, aadhar, fullname, address, conpassword;
        long mobile = 0;
        email = usern.getText().toString();
        password = passw.getText().toString();
        fullname = fulln.getText().toString();
        try {
            mobile = Long.parseLong(eMobile.getText().toString());
        }catch (Exception ignored){}
        address = eAddress.getText().toString();
        aadhar = eAadharno.getText().toString();
        conpassword = cpassword.getText().toString();

        // Validations for input
        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(fullname)&&TextUtils.isEmpty(address)&&TextUtils.isEmpty(aadhar)){
            progressBar.setVisibility(View.GONE);
            usern.setError("Invalid mail!!!");
            fulln.setError("Invalid name!!!");
            eMobile.setError("Invalid mobile no!!!");
            eAddress.setError("Invalid address!!!");
            eAadharno.setError("Invalid aadhaar!!!");
            cpassword.setError("Invalid Confirm password!!!");
        }
        else if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usern.setError("Invalid mail!!!");progressBar.setVisibility(View.GONE);
        } else if (!validatePassword(password, conpassword, passw)) {
            progressBar.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(fullname) || fullname.length() < 3) {
            fulln.setError("Invalid name!!!");progressBar.setVisibility(View.GONE);
        } else if (!(String.valueOf(mobile).length() == 10)) {
            eMobile.setError("Invalid mobile no!!!");progressBar.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(address)) {
            eAddress.setError("Invalid address!!!");progressBar.setVisibility(View.GONE);
        }
        else if (TextUtils.isEmpty(aadhar)) {
            eAadharno.setError("Invalid address!!!");progressBar.setVisibility(View.GONE);
        }
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
                .set(adminAddedData).addOnSuccessListener(unused -> {
                    // if the user created intent to login activity
                    Intent intent = new Intent(AdminRegister.this, AdminLogin.class);
                    startActivity(intent);
                    Toast.makeText(AdminRegister.this, "Signed up Successfully...", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(AdminRegister.this, "Failed please try again...", Toast.LENGTH_SHORT).show());
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}