package com.endevex.gymlocal.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.orm.SugarRecord;

/**
 * User Model for the application and uses Sugar ORM to create the entity and save to local db.
 * Created by Leivant on 6/10/2017.
 */
public class User extends SugarRecord<User> {
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;
    private boolean mGymOwner;


    /**
     * Default constructor required for Sugar ORM to create entity.
     */
    public User() {
    }

    /**
     * Constructor to initialise all fields.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param gymOwner
     */
    public User(String firstName, String lastName, String email, String password, boolean gymOwner) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mPassword = password;
        this.mGymOwner = gymOwner;
    }

    public boolean isGymOwner() {
        return mGymOwner;
    }

    public void setGymOwner(boolean gymOwner) {
        this.mGymOwner = gymOwner;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

}
