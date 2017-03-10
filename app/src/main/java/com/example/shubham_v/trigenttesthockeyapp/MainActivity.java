package com.example.shubham_v.trigenttesthockeyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.LoginManager;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Add authentication
        LoginManager.register(this, "fb2ab20b35436cf95f282e75f6e829fb" , LoginManager.LOGIN_MODE_EMAIL_PASSWORD);
        LoginManager.verifyLogin(this, getIntent());


       ////Add update distribution
        checkForUpdates();

        // Add User Metrics
        MetricsManager.register(this, getApplication());


        //Add in-app feedback
        FeedbackManager.register(this);
        Button feedbackButton = (Button) findViewById(R.id.feedbackbutton_id);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackManager.showFeedbackActivity(MainActivity.this);
            }
        });

        Button crashtest = (Button) findViewById(R.id.crashtest_id);
        crashtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 2;
                int b = a/0;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume State", Toast.LENGTH_SHORT).show();
        // ... your own onResume implementation
        checkForCrashes();

        //Changes for Usage Time
        Tracking.startUsage(this);

    }

    @Override
    public void onPause() {
        //Changes for Usage Time
        Tracking.stopUsage(this);

        super.onPause();

        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    //Add update distribution
    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    //Add update distribution
    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
