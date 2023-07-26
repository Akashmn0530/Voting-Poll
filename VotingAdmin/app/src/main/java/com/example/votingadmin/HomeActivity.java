package com.example.votingadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class HomeActivity extends AppCompatActivity{
    Button addUserData, addPollData, addInfoData;
    // Your existing button declarations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addInfoData = findViewById(R.id.addInfo);
        addPollData = findViewById(R.id.addpoll);
        addUserData = findViewById(R.id.adduser1);


        // Your existing button setup
        addPollData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                AddPoll aPoll = new AddPoll();
                t1.replace(R.id.fragmentContainer1, aPoll);
                t1.commit();
            }
        });

        addUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                AddUser aUser = new AddUser();
                t1.replace(R.id.fragmentContainer2, aUser);
                t1.commit();
            }
        });
    }

}