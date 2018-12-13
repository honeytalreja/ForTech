package com.fortech.protalendeavours.fortech;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Firebase Authentication Variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    // Local Views Variable Declarations
    private EditText emailId;
    private EditText password;
    private Button Login;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            finish();
        }

        // No User Account, Go to Signin Page to Create New Account
        TextView here = findViewById(R.id.signInHere);
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        // Existing Account, Log in
        Login = findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Contact the Administrator",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userLogin() {
        emailId = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        String EmailId = emailId.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if(EmailId.isEmpty()) {
            emailId.setError("E-Mail Id Required");
            emailId.requestFocus();
            return;
        }
        if(Password.isEmpty()) {
            password.setError("Password Required");
            password.requestFocus();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(EmailId,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // Logged In
                    startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(),"Error Logging In",Toast.LENGTH_SHORT).show();
                    emailId.requestFocus();
                }
            }
        });


    }
}
