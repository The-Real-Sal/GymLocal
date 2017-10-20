package com.endevex.gymlocal.model;

import com.orm.SugarRecord;

/**
 * Gym Model for the application and uses Sugar ORM to create the entity and save to local db.
 * Created by Leivant on 6/10/2017.
 */
public class Gym extends SugarRecord<Gym> {
    private String mName;
    private String mType;
    private String mAddress;
    private String mPhone;
    private Double mLongitude;
    private Double mLatitude;

    private User mOwner;

    /**
     * Default constructor required for Sugar ORM to create entity.
     */
    public Gym() {
    }

    /**
     * Constructor to initialise all fields.
     * @param name
     * @param type
     * @param address
     * @param phone
     * @param longitude
     * @param latitude
     * @param owner
     */
    public Gym(String name, String type, String address, String phone, Double longitude, Double latitude, User owner) {
        this.mName = name;
        this.mType = type;
        this.mAddress = address;
        this.mPhone = phone;
        this.mLongitude = longitude;
        this.mLatitude = latitude;
        this.mOwner = owner;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        this.mLongitude = longitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        this.mLatitude = latitude;
    }

    public User getOwner() {
        return mOwner;
    }

    public void setOwner(User owner) {
        this.mOwner = owner;
    }

}
