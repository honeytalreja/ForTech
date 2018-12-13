package com.fortech.protalendeavours.fortech;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ArrayList<String> ProjectNames;
    private ArrayList<String> ProjectDomains;
    private ArrayList<String> ProjectGuides;
    private ArrayList<String> DomainExperts;
    private ArrayList<String> ProjectID;
    private FloatingActionButton floatingActionButton;
    private Projects currentProject;


    // Firebase Authentication : Email Registration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    private RecyclerView recyclerView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.homeFragRV);
        floatingActionButton = view.findViewById(R.id.homeAddProj);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddProjectActivity.class));
            }
        });

        ProjectNames = new ArrayList<>();
        ProjectDomains = new ArrayList<>();
        ProjectGuides = new ArrayList<>();
        DomainExperts = new ArrayList<>();
        ProjectID = new ArrayList<>();

        mDatabase=FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Projects");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot s : dataSnapshot.getChildren()) {
                    currentProject = s.getValue(Projects.class);
                    ProjectNames.add(currentProject.getProjectName());
                    ProjectDomains.add(currentProject.getProjectDomain());
                    ProjectGuides.add("Yet to be Decided");
                    DomainExperts.add("No Expert");
                    ProjectID.add(currentProject.getProjectID());
                }
                initCards();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void initCards() {
//        ProjectNames.add("QA System");
//        ProjectDomains.add("Natural Language");
//        ProjectGuides.add("Pranav Shirude");
//        DomainExperts.add("Ria Mittal");
//
//        ProjectNames.add("Fake News Detection");
//        ProjectDomains.add("Machine Learning");
//        ProjectGuides.add("Honey Talreja");
//        DomainExperts.add("Ankita Narkhede");
//
//        ProjectNames.add("JoyFetch");
//        ProjectDomains.add("Supply Chain Management");
//        ProjectGuides.add("Rakhi Marathe");
//        DomainExperts.add("Shrirang Mhalgi");
//

        initRecyclerView();
    }

    private void initRecyclerView() {
        HomeFragRVAdapter adapter = new HomeFragRVAdapter(getContext(),getActivity(),ProjectNames,ProjectDomains, ProjectGuides, DomainExperts,ProjectID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
