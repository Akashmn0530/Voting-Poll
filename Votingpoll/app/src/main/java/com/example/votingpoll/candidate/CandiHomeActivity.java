package com.example.votingpoll.candidate;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.example.votingpoll.user.FeedbackFragment;
import com.example.votingpoll.user.Login;
import com.example.votingpoll.user.ProfileFragment;
import com.example.votingpoll.user.StatusFragment;
import com.google.android.material.navigation.NavigationView;

public class CandiHomeActivity extends AppCompatActivity {

    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.welcomeTxt);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);

        layDL.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            vNV.setCheckedItem(R.id.profile);
        }
        NavClick();
    }

    private void NavClick() {
        vNV.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            int id=item.getItemId();
            if(id==R.id.profile) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                CandiProfile candiProfile = new CandiProfile();
                t1.replace(R.id.fragmentContainer1, candiProfile);
                t1.commit();
                Log.d("Akash","HomeActivity button clicked");
                textView.setText("");
                layDL.closeDrawer(GravityCompat.START);
            } else if (id==R.id.status) {
                Toast.makeText(this, "status", Toast.LENGTH_SHORT).show();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction t1 = fm.beginTransaction();
//                StatusFragment statusFragment = new StatusFragment();
//                t1.replace(R.id.fragmentContainer1, statusFragment);
//                t1.commit();
//                Log.d("Akash","HomeActivity button clicked");
                textView.setText("");
                layDL.closeDrawer(GravityCompat.START);

            } else if (id==R.id.feedback) {

                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                CandiFeedbackFragment candiFeedbackFragment = new CandiFeedbackFragment();
                t1.replace(R.id.fragmentContainer1, candiFeedbackFragment);
                t1.commit();
                Log.d("Akash","HomeActivity button clicked");
                textView.setText("");
                layDL.closeDrawer(GravityCompat.START);
            }


            else {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CandiLogin.class);
                startActivity(intent);
                layDL.closeDrawer(GravityCompat.START);
                finish();
            }
            layDL.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer1);
        if (layDL.isDrawerOpen(GravityCompat.START)){
            layDL.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}