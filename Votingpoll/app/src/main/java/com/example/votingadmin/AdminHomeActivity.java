package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.votingpoll.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    // Your existing button declarations
    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;
    FragmentManager fm = getSupportFragmentManager();
    ViewContest viewContest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //bottomNavigationView.setSelectedItemId(R.id.adminprofile);
        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);
        layDL.addDrawerListener(toggle);
        toggle.syncState();

        //To display the poll list
        viewContest = new ViewContest();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer1, viewContest)
                .addToBackStack(null)
                .commit();
        if (savedInstanceState == null) {
            vNV.setCheckedItem(R.id.adduser1);
        }
        NavClick();
    }

    AdminProfileFragment adminProfileFragment = new AdminProfileFragment();
    ViewFeedbackFragment viewFeedbackFragment = new ViewFeedbackFragment();
    ViewTermsAndConditionFragment viewTermsAndConditionFragment = new ViewTermsAndConditionFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int idd = item.getItemId();
            if(idd == R.id.adminprofile) {
                Toast.makeText(this, "Admin Profile", Toast.LENGTH_SHORT).show();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer1, adminProfileFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            if(idd== R.id.viewfeedback) {
                Toast.makeText(this, "view feedback", Toast.LENGTH_SHORT).show();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer1, viewFeedbackFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            if(idd== R.id.viewTC){
                Toast.makeText(this, "view terms and conditions", Toast.LENGTH_SHORT).show();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer1, viewTermsAndConditionFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return false;
    }

    private void NavClick() {
        vNV.setNavigationItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id==R.id.addpoll) {
                FragmentTransaction t1 = fm.beginTransaction();
                AddCandidates aPoll = new AddCandidates();
                t1.replace(R.id.fragmentContainer1, aPoll);
                t1.addToBackStack(null);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.adduser1) {
                FragmentTransaction t1 = fm.beginTransaction();
                AddUser aUser = new AddUser();
                t1.replace(R.id.fragmentContainer1, aUser);
                t1.addToBackStack(null);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.addInfo) {
                FragmentTransaction t1 = fm.beginTransaction();
                AddTermsAndConditionsFragment addTermsAndConditionsFragment = new AddTermsAndConditionsFragment();
                t1.replace(R.id.fragmentContainer1, addTermsAndConditionsFragment);
                t1.addToBackStack(null);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.addContest) {
                FragmentTransaction t1 = fm.beginTransaction();
                AddTermsAndConditionsFragment addTermsAndConditionsFragment = new AddTermsAndConditionsFragment();
                t1.replace(R.id.fragmentContainer1, addTermsAndConditionsFragment);
                t1.addToBackStack(null);
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer1, viewContest)
                    .addToBackStack(null)
                    .commit();
        }
    }

}

