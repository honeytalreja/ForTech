package com.fortech.protalendeavours.fortech;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddProjectActivity extends AppCompatActivity {



    // Firebase Authentication : Email Registration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    private EditText newProjName;
    private EditText newProjDomain;
    private EditText member1;
    private EditText member2;
    private EditText member3;
    private EditText member4;
    private EditText member5;
    private CheckedTextView sponsored;
    private CheckedTextView inHouse;
    private CheckedTextView social;
    private CheckedTextView interDis;
    private Button AddYourProject;
//    private String ProjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        newProjName = findViewById(R.id.newProjName);
        newProjDomain = findViewById(R.id.newProjDomain);
        member1 = findViewById(R.id.member1);
        member2 = findViewById(R.id.member2);
        member3 = findViewById(R.id.member3);
        member4 = findViewById(R.id.member4);
        member5 = findViewById(R.id.member5);
        sponsored = findViewById(R.id.sponsored);
        inHouse = findViewById(R.id.inHouse);
        social = findViewById(R.id.social);
        interDis = findViewById(R.id.interDis);
        AddYourProject = findViewById(R.id.addYourProject);
        AddYourProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewProject();
            }
        });

        // Checked Text Views OnClick Listner
        sponsored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sponsored.isChecked()) {
                    sponsored.setChecked(false);
                    inHouse.setChecked(true);
                }
                else {
                    sponsored.setChecked(true);
                    inHouse.setChecked(false);
                }
            }
        });


        inHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inHouse.isChecked()) {
                    inHouse.setChecked(false);
                    sponsored.setChecked(true);
                }
                else {
                    inHouse.setChecked(true);
                    sponsored.setChecked(false);
                }
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(social.isChecked()) {
                    social.setChecked(false);
                }
                else {
                    social.setChecked(true);
                }
            }
        });

        interDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interDis.isChecked()) {
                    interDis.setChecked(false);
                }
                else {
                    interDis.setChecked(true);
                }
            }
        });

    }

    private void CreateNewProject() {
        String ProjectName = newProjName.getText().toString();
        String ProjectDomain = newProjDomain.getText().toString();
        String Member1 = member1.getText().toString();
        String Member2 = member2.getText().toString();
        String Member3 = member3.getText().toString();
        String Member4 = member4.getText().toString();
        String Member5 = member5.getText().toString();
        boolean Sponsored = sponsored.isChecked();
        boolean InHouse = inHouse.isChecked();
        boolean Social = social.isChecked();
        boolean InterDis = interDis.isChecked();

        // Validation of all the Fields


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            String ProjectID = UUID.randomUUID().toString().replaceAll("-","");
            Projects project = new Projects(ProjectID, "Admin",ProjectName,ProjectDomain,Member1,Member2,Member3,Member4,null,Sponsored,InHouse,Social,InterDis);
            String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference();
            mReference.child("Projects").child(ProjectID).setValue(project);
            startActivity(new Intent(AddProjectActivity.this,ProfileActivity.class));
            finish();
        }
    }
}
