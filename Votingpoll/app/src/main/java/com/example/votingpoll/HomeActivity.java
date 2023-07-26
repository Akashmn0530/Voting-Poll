package com.example.votingpoll;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                layDL.closeDrawer(GravityCompat.START);
            } else if (id==R.id.status) {
                Toast.makeText(this, "status", Toast.LENGTH_SHORT).show();
                // layDL.closeDrawer(GravityCompat.START);
                //  Intent intent=new Intent(this,MainActivity2.class);
                //  startActivity(intent);

            } else if (id==R.id.feedback) {

                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
                layDL.closeDrawer(GravityCompat.START);
            }


            else {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                layDL.closeDrawer(GravityCompat.START);

            }





            layDL.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.layFL);
        if (layDL.isDrawerOpen(GravityCompat.START)){
            layDL.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}