package edu.cs65.don.myruns.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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
}