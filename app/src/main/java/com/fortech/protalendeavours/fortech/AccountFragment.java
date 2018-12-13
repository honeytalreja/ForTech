package com.fortech.protalendeavours.fortech;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    // Firebase Variables
    private FirebaseAuth mAuth;
    private StorageReference mStorage;

    // Local Views for this Fragment
    private ImageView ProfilePhoto;
    private FloatingActionButton AddImage;
    private Button LogOut;
    private Uri uri;
    private ProgressBar progressBar;
    private static final int GALLERY_INTENT_CODE=1;
    private TextView accName;
    private TextView accEmail;
    private TextView accDesg;
    private Users user;

    // Firebase Database : Storing and Retrieving Data
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public AccountFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        // Finding the Views
        LogOut = view.findViewById(R.id.acclogOut);
//        progressBar = view.findViewById(R.id.progressBar);
        ProfilePhoto = view.findViewById(R.id.profilePhoto);
        accName = view.findViewById(R.id.accName);
        accEmail = view.findViewById(R.id.accEmail);
        accDesg = view.findViewById(R.id.accDesg);


        // LogOut Button; Back to Login Activity
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {


            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference().child("Users").child(user_id);


            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(Users.class);
                    accName.setText(user.getFirstName()+" "+user.getLastName());
                    accEmail.setText(user.getEmail());
                    accDesg.setText(user.getDesignation());
                    //LoadProfilePhoto();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

//        uri = mStorage.child("Profile Pictures").child(user_id).getFile();



        // Loading the Profile Photo

        // Uploading a New Image
        AddImage = view.findViewById(R.id.addImage);
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GALLERY_INTENT_CODE);
            }
        });

        return view;
    }

    private void LoadProfilePhoto() {
        //ProfilePhoto.setImageURI(user.getProfileImage());

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference("Profile Pictures").child(user_id);
        mStorage.getFile(uri).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                ProfilePhoto.setImageURI(uri);
            }
        });
        //        mStorage.child("Profile Pictures").child(user_id).getFile(LoadedImage);
//        ProfilePhoto.setImageURI(LoadedImage);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            // Initialising the Instances of Firebase Auth and Storage
            mStorage = FirebaseStorage.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();

            // Uploading the Image Data To the Cloud
            uri = data.getData();
            ProfilePhoto.setImageURI(uri);
            String user_id = mAuth.getCurrentUser().getUid();

            mStorage.child("Profile Pictures").child(user_id).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Uploading Complete",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
        }

    }
}
