package com.fortech.protalendeavours.fortech;

import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Honey on 09-Oct-18.
 */

public class Users {
    private String FirstName;
    private String LastName;
    private String Designation;
    private String Email;
    private ArrayList<String> AssociatedProjects;
    private String profileImage;

    public Users() {

    }

    public Users(String FirstName, String LastName, String Designation, String Email) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Designation = Designation;
        this.Email = Email;
        this.AssociatedProjects = new ArrayList<>();
    }

    public ArrayList<String> getAssociatedProjects() {
        return AssociatedProjects;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setAssociatedProjects(ArrayList<String> associatedProjects) {
        AssociatedProjects = associatedProjects;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
