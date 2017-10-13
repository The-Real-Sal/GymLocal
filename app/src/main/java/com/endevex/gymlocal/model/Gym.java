package com.endevex.gymlocal.model;

import com.orm.SugarRecord;

/**
 * Created by Leivant on 6/10/2017.
 */

public class Gym extends SugarRecord<Gym> {
    private String mName;
    private String mType;
    private String mAddress;
    private Double mLongitude;
    private Double mLatitude;

    private User mOwner;
}
