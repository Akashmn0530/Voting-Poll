package com.example.votingadmin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingpoll.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    // Your existing button declarations
    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;
    FragmentManager fm = getSupportFragmentManager();

    private ArrayList myListData;
    private AdapterContest myListAdapter;
    private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);
        layDL.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView voteRV = findViewById(R.id.your_recycler_view_id11);

        // Initializing our variable for Firestore and getting its instance
        db = FirebaseFirestore.getInstance();
        Log.d("Aka", "getting view");
        // Creating our new array list
        myListData = new ArrayList<>();

        voteRV.setHasFixedSize(true);
        voteRV.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));

        // Adding our array list
        myListAdapter = new AdapterContest(AdminHomeActivity.this, myListData);

        voteRV.setAdapter(myListAdapter); // Setting the adapter to the RecyclerView

        fetchUserDataFromFirestore();
        if (savedInstanceState == null) {
            vNV.setCheckedItem(R.id.adduser1);
        }
        NavClick();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchUserDataFromFirestore() {

        db.collection("contestData").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            ContestClass c = d.toObject(ContestClass.class);
                            assert c != null;
                            c.setConId(d.getId());
                            myListData.add(c);
                            Log.d("Aka", "getting data..");
                        }
                        myListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AdminHomeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        Log.d("Aka", "no data found");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminHomeActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    Log.d("Aka", "failure to get");
                });
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
                AddCandidates addCandidates = new AddCandidates();
                t1.replace(R.id.fragmentContainer1, addCandidates);
                t1.addToBackStack(null);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else if(id==R.id.results) {
                FragmentTransaction t1 = fm.beginTransaction();
                ViewResultsFragment viewResultsFragment = new ViewResultsFragment();
                t1.replace(R.id.fragmentContainer1, viewResultsFragment);
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
                AddContest addContest = new AddContest();
                t1.replace(R.id.fragmentContainer1, addContest);
                t1.addToBackStack(null);
                t1.commit();
                layDL.closeDrawer(GravityCompat.START);
            }
            else {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    public void showDeletePopup(final ContestClass contest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete the contest with ID: " + contest.getConId() + "?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle delete action here
                myListData.remove(contest);
                myListAdapter.notifyDataSetChanged();
                Toast.makeText(AdminHomeActivity.this, "Contest deleted: " + contest.getConId(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

