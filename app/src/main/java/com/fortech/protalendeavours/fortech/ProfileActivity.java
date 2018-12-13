package com.fortech.protalendeavours.fortech;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLister;
    private Button logOut;
    private BottomNavigationView MainNavigation;
    private FrameLayout MainFrame;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public Users user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MainNavigation = findViewById(R.id.mainNavigation);
        MainFrame = findViewById(R.id.mainFrame);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();

        mAuth = FirebaseAuth.getInstance();
        mAuthLister = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                    finish();
                }
            }
        };

        setFragment(homeFragment);

        MainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.navHome :
                        setFragment(homeFragment);
                        return true;

                    case R.id.navNotification :
                        setFragment(notificationFragment);
                        return true;

                    case R.id.navAccount :
                        setFragment(accountFragment);
                        return true;

                    default :
                        return false;
                }

            }
        });


    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();
    }

    public void loadUserData() {
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference().child("Users").child(user_id);
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Iterable<DataSnapshot> userdata = dataSnapshot.getChildren();
                    String FName = null,LName=null, Email=null, Designation=null,var="";
//                    for(DataSnapshot userdata : dataSnapshot.getChildren()) {
//                        if(userdata.getKey().equals("First Name")) FName = userdata.getValue(String.class);
//                        if(userdata.getKey().equals("Last Name")) LName = userdata.getValue(String.class);
//                        if(userdata.getKey().equals("Email Id")) Email = userdata.getValue(String.class);
//                        if(userdata.getKey().equals("Designation")) Designation = userdata.getValue(String.class);
//
//                    }
                    Users user = dataSnapshot.getValue(Users.class);
//                    user = new Users(FName,LName,Designation, Email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}
