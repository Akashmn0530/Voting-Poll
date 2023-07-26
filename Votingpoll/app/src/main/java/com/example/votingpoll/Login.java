package com.example.votingpoll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {
    EditText uname,pword, message;
    Button button1;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    int counter = 3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = (EditText)findViewById(R.id.username1);
        pword = (EditText)findViewById(R.id.password1);
        message = (EditText)findViewById(R.id.msg);
        button1 = (Button)findViewById(R.id.loginbtn);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);
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
                Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(getApplicationContext(),Register.class);
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

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(Login.this,
                                            HomeActivity.class);
                                    startActivity(intent);
                                }

                                else {

                                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
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
                            }
                        });
    }


}