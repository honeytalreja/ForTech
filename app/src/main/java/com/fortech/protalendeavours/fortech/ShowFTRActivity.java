package com.fortech.protalendeavours.fortech;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class ShowFTRActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Firebase Authentication : Email Registration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    private TextView ftr0Date;
    private TextView ftr1Date;
    private TextView ftr2Date;
    private TextView ftr3Date;
    private TextView ftr4Date;
    private TextView ftr5Date;
    private TextView ftr6Date;
    private TextView ProjectName;
    private String CurrentProjectID;
    private Projects CurrentProjectInfo;
    private Users CurrentUser = null;
    private boolean dateSetter[] = {
            false, false, false, false, false, false, false
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ftr);
        Intent thisIntent = getIntent();
        CurrentProjectID = thisIntent.getExtras().getString("currentProjectID");
        ftr0Date = findViewById(R.id.ftr0Date);
        ftr1Date = findViewById(R.id.ftr1Date);
        ftr2Date = findViewById(R.id.ftr2Date);
        ftr3Date = findViewById(R.id.ftr3Date);
        ftr4Date = findViewById(R.id.ftr4Date);
        ftr5Date = findViewById(R.id.ftr5Date);
        ftr6Date = findViewById(R.id.ftr6Date);
        ProjectName = findViewById(R.id.project_title2);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Projects").child(CurrentProjectID);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentProjectInfo = dataSnapshot.getValue(Projects.class);
                ftr0Date.setText(CurrentProjectInfo.getFTR0Date());
                ftr1Date.setText(CurrentProjectInfo.getFTR1Date());
                ftr2Date.setText(CurrentProjectInfo.getFTR2Date());
                ftr3Date.setText(CurrentProjectInfo.getFTR3Date());
                ftr4Date.setText(CurrentProjectInfo.getFTR4Date());
                ftr5Date.setText(CurrentProjectInfo.getFTR5Date());
                ftr6Date.setText(CurrentProjectInfo.getFTR6Date());
                ProjectName.setText(CurrentProjectInfo.getProjectName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {


            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference().child("Users").child(user_id);


            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CurrentUser = dataSnapshot.getValue(Users.class);
                    addListners();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void addListners() {
        if(!CurrentUser.getDesignation().equals("Guide")) return;

        ftr0Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[0] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 0 Date");
//                ftr0Date.setText(currentDateString);
            }
        });
        ftr1Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[1] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 1 Date");
//                ftr1Date.setText(currentDateString);
            }
        });
        ftr2Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[2] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 2 Date");
//                ftr2Date.setText(currentDateString);
            }
        });
        ftr3Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[3] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 3 Date");
//                ftr3Date.setText(currentDateString);
            }
        });
        ftr4Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[4] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 4 Date");
//                ftr4Date.setText(currentDateString);
            }
        });
        ftr5Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[5] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 5 Date");
//                ftr5Date.setText(currentDateString);
            }
        });
        ftr6Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSetter[6] = true;
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"FTR 6 Date");
//                ftr6Date.setText(currentDateString);
            }
        });

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT ).format(calendar.getTime());
        String customFormat[] = currentDateString.split("/");
        currentDateString = customFormat[1] + "/" + customFormat[0] + "/" + customFormat[2];
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Projects").child(CurrentProjectID);
        for(int i=0; i<7; i++) {
            if(dateSetter[i] == true) {

                if(i==0) {
                    ftr0Date.setText(currentDateString);
                    mReference.child("ftr0Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
                if(i==1) {
                    ftr1Date.setText(currentDateString);
                    mReference.child("ftr1Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }

                if(i==2) {
                    ftr2Date.setText(currentDateString);
                    mReference.child("ftr2Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
                if(i==3) {
                    ftr3Date.setText(currentDateString);
                    mReference.child("ftr3Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
                if(i==4) {
                    ftr4Date.setText(currentDateString);
                    mReference.child("ftr4Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
                if(i==5) {
                    ftr5Date.setText(currentDateString);
                    mReference.child("ftr5Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
                if(i==6) {
                    ftr6Date.setText(currentDateString);
                    mReference.child("ftr6Date").setValue(currentDateString);
                    dateSetter[i] = false;
                    break;
                }
            }
        }
    }
}
