package com.example.votingadmin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.votingpoll.R;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminHomeActivity extends AppCompatActivity{
    Button addUserData, addPollData, addInfoData;
    // Your existing button declarations
    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_home);
            addInfoData = findViewById(R.id.addInfo);
            addPollData = findViewById(R.id.addpoll);
            addUserData = findViewById(R.id.adduser1);
            linearLayout = findViewById(R.id.linear123);
            // Your existing button setup
            addPollData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction t1 = fm.beginTransaction();
                    AddCandidates aPoll = new AddCandidates();
                    t1.replace(R.id.fragmentContainer1, aPoll);
                    t1.commit();
                }
            });

            addUserData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction t1 = fm.beginTransaction();
                    AddUser aUser = new AddUser();
                    t1.replace(R.id.fragmentContainer1, aUser);
                    t1.commit();
                }
            });
            addInfoData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction t1 = fm.beginTransaction();
                    AddTermsAndConditionsFragment addTermsAndConditionsFragment = new AddTermsAndConditionsFragment();
                    t1.replace(R.id.fragmentContainer1, addTermsAndConditionsFragment);
                    t1.commit();
                }
            });

        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);
        layDL.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            vNV.setCheckedItem(R.id.adminprofile);
        }
        NavClick();
    }

    private void NavClick() {
        vNV.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            int id=item.getItemId();
            if(id==R.id.adminprofile) {
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "Admin Profile", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                AdminProfileFragment adminProfileFragment = new AdminProfileFragment();
                t1.replace(R.id.fragmentContainer1, adminProfileFragment);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.viewfeedback) {
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "view feedback", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                ViewFeedbackFragment viewFeedbackFragment = new ViewFeedbackFragment();
                t1.replace(R.id.fragmentContainer1, viewFeedbackFragment);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.viewTC) {
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "view terms and conditions", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t1 = fm.beginTransaction();
                ViewTermsAndConditionFragment viewTermsAndConditionFragment = new ViewTermsAndConditionFragment();
                t1.replace(R.id.fragmentContainer1, viewTermsAndConditionFragment);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(intent);
                layDL.closeDrawer(GravityCompat.START);
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

