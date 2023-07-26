package com.example.votingpoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=findViewById(R.id.textviewsplash);
        t.animate().translationX(1000).setDuration(1000).setStartDelay(2500);

        Thread thread=new Thread()
        {
            public void run()
            {
                try{
                    Thread.sleep(4000);
                }
                catch (Exception e){
                    e.printStackTrace();;
                }
                finally {

                    Intent intent=new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}