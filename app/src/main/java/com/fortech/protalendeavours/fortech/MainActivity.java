package com.fortech.protalendeavours.fortech;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Splash Screen Activity Code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            redirectToLogIn();
            }
        },SPLASH_TIME);


    }


    // Go to Login Page and Log in to the Account
    private void redirectToLogIn() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}
