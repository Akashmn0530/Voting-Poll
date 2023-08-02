package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.votingadmin.AdminLogin;
import com.example.votingpoll.R;
import com.example.votingpoll.candidate.CandiHomeActivity;
import com.example.votingpoll.candidate.CandiLogin;
import com.example.votingpoll.user.Login;

public class Welcome extends AppCompatActivity {
    LinearLayout linearLayout_voter,linearLayout_candi,linearLayout_admin;
    String type_of_user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        linearLayout_voter = findViewById(R.id.linearLayout_voter);
        linearLayout_admin = findViewById(R.id.linearLayout_admin);
        linearLayout_candi = findViewById(R.id.linearLayout_candi);
        linearLayout_voter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_voter.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
                linearLayout_admin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                linearLayout_candi.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        linearLayout_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_candi.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
                linearLayout_admin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                linearLayout_voter.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                Intent intent = new Intent(getApplicationContext(), CandiLogin.class);
                startActivity(intent);
            }
        });
        linearLayout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_admin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
                linearLayout_candi.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                linearLayout_voter.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
                Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}