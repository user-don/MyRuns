package edu.cs65.don.myruns.models;

import android.text.format.Time;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by don on 4/22/16.
 */
public class ExerciseEntry {
    public Long id;

    public int mInputType;        // Manual, GPS or automatic
    public int mActivityType;     // Running, cycling etc.
    public Calendar mDateTime;    // When does this entry happen
    public int mDuration;         // Exercise duration in seconds
    public double mDistance;      // Distance traveled. Either in meters or feet.
    public double mAvgPace;       // Average pace
    public double mAvgSpeed;      // Average speed
    public int mCalorie;          // Calories burnt
    public double mClimb;         // Climb. Either in meters or feet.
    public int mHeartRate;        // Heart rate
    public String mComment;       // Comments
    public ArrayList<LatLng> mLocationList; // Location list

    public TimeZone timeZone;

    public ExerciseEntry() {
        timeZone = TimeZone.getDefault();
        // initialize default values
        mActivityType = 0;
        mDuration = 0;
        mDistance = 0;
        mAvgPace = 0;
        mAvgSpeed = 0;
        mCalorie = 0;
        mClimb = 0;
        mHeartRate = 0;
        mComment = "";

    }

    public String serializeDateTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(mDateTime.get(Calendar.YEAR));
        sb.append(',');
        sb.append(mDateTime.get(Calendar.MONTH));
        sb.append(',');
        sb.append(mDateTime.get(Calendar.DAY_OF_MONTH));
        sb.append(',');
        sb.append(mDateTime.get(Calendar.HOUR_OF_DAY));
        sb.append(',');
        sb.append(mDateTime.get(Calendar.MINUTE));
        sb.append(',');
        sb.append(mDateTime.get(Calendar.SECOND));
        return "";
    }

    public long storeDateTime() {
        return mDateTime.getTimeInMillis();
    }
}