package com.example.don.myapplication.backend.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.sun.corba.se.spi.activation.Server;

/**
 * Created by McFarland on 5/13/16.
 */

@Entity
public class ServerEE {

    public static final String EE_PARENT_ENTITY_NAME = "EEparent";
    public static final String EE_PARENT_KEY_NAME = "EEparent";

    public static final String EE_ENTITY_NAME = "Exercise Entry";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_INPUT_TYPE = "input type";
    public static final String FIELD_NAME_ACTIVITY_TYPE = "activity type";
    public static final String FIELD_NAME_DATE_TIME = "date time";
    public static final String FIELD_NAME_DURATION = "duration";
    public static final String FIELD_NAME_DISTANCE = "distance";
    public static final String FIELD_NAME_AVG_SPEED = "avg speed";
    public static final String FIELD_NAME_CALORIES = "calories";
    public static final String FIELD_NAME_CLIMB = "climb";
    public static final String FIELD_NAME_HEART_RATE = "heart rate";
    public static final String FIELD_NAME_COMMENT = "comment";

    public static final String KEY_NAME = FIELD_NAME_ID;

    @Id
    Long id;

    @Index
    public Long mID;
    public String mInputType;
    public String mActivityType;
    public String mDateTime;
    public String mDuration;
    public String mDistance;
    public String mAvgSpeed;
    public String mCalories;
    public String mClimb;
    public String mHeartRate;
    public String mComment;

    public ServerEE() {
        mID = (long)0;
        mInputType = mActivityType = mDateTime = mDistance = mDuration = mAvgSpeed = mCalories = mClimb = mHeartRate = mComment = "";
    }

    public ServerEE(Long _id, String _InputType, String _ActivityType, String _DateTime, String _Duration,
                    String _Distance, String _AvgSpeed, String _Calories, String _Climb, String _HeartRate,
                    String _Comment) {

        mID = _id;
        mInputType = _InputType;
        mActivityType = _ActivityType;
        mDateTime = _DateTime;
        mDuration = _Duration;
        mDistance = _Distance;
        mAvgSpeed = _AvgSpeed;
        mCalories = _Calories;
        mClimb = _Climb;
        mHeartRate = _HeartRate;
        mComment = _Comment;
    }


}
