package com.fortech.protalendeavours.fortech;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {


    // Firebase Authentication : Email Registration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    // Local Activity Views Used Instances
    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private EditText newPassword;
    private EditText confirmPassword;
    private Spinner spinnner;
    private ArrayAdapter<CharSequence> adapter;
    private Button signUp;
    private String Designation;

    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Local Activity Views Used Instances
        spinnner = findViewById(R.id.spinner);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailId = findViewById(R.id.emailId);
        newPassword = findViewById(R.id.newpassword);
        confirmPassword = findViewById(R.id.confirmpassword);
        signUp = findViewById(R.id.signup);



        adapter = ArrayAdapter.createFromResource(this,R.array.designation,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnner.setAdapter(adapter);
        spinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Designation = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Firebase Database : Data Storing and Retrieving
        mDatabase = FirebaseDatabase.getInstance();

        // Firebase Authentication : Email Registration
        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null) {
                    //Intent User Account
                    startActivity(new Intent(SignupActivity.this,ProfileActivity.class));
                    finish();
                }
                else if(firebaseAuth.getCurrentUser() == null) {
                    signUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registerNewUser();
                        }
                    });
                }
            }
        };

    }


    // Input validation for Signup Form and User Registration
    private void registerNewUser() {

        // Initialising Variables from values entered in views
        final String FirstName = firstName.getText().toString();
        final String LastName = lastName.getText().toString();
        final String EmailId = emailId.getText().toString().trim();
        final String NewPassword = newPassword.getText().toString().trim();
        final String ConfirmPassword = confirmPassword.getText().toString().trim();


        // Validating First Name
        if(FirstName.isEmpty()) {
            firstName.setError("First Name is Required");
            firstName.requestFocus();
            return;
        }

        // Validating Last Name
        if(LastName.isEmpty()) {
            lastName.setError("Last Name is Required");
            lastName.requestFocus();
            return;
        }

        // Validating the Email Address
        if(EmailId.isEmpty()) {
            emailId.setError("Email Id is Required");
            emailId.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(EmailId).matches()) {
            emailId.setError("Enter a Valid Email Id");
//            emailId.setText("");
            emailId.requestFocus();
            return;
        }
        if(!viitStudent(EmailId)) {
            emailId.setError("Use College Email Id");
            emailId.requestFocus();
            return;
        }

        // Password Validation
        if(!NewPassword.equals(ConfirmPassword)) {
            newPassword.setText("");
            confirmPassword.setText("");
            newPassword.setError("Passwords didn't Match");
            newPassword.requestFocus();
            return;
        }
        if(NewPassword.length() < 8) {
            newPassword.setText("");
            confirmPassword.setText("");
            newPassword.setError("Password length should be Minimum 8 Characters");
            newPassword.requestFocus();
            return;
        }

        // Designation Validation
        if(Designation.equals("Designation")) {
            Toast.makeText(getBaseContext(),"Select a Valid Designation",Toast.LENGTH_SHORT).show();
            return;
        }



        //Final Registration of User
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(EmailId,NewPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    //Creating User Profile in Database
                    createUserProfile(FirstName,LastName,EmailId);

                }
                else {
                    Toast.makeText(getBaseContext(),"Error while Signing In",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean viitStudent(String EmailId) {
        String[] domainName = EmailId.split("@");
        if(domainName[1].equals("viit.ac.in")) return true;
        return false;
    }

    private void createUserProfile(String FirstName, String LastName, String EmailId) {
        Users user = new Users(FirstName,LastName,Designation,EmailId);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mReference.child("Users").child(user_id).setValue(user);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
}
