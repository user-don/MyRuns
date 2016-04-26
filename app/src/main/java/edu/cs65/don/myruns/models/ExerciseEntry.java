package edu.cs65.don.myruns.models;

import android.content.res.Resources;
import android.text.format.Time;

import com.google.android.gms.maps.model.LatLng;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.TimeZone;

import edu.cs65.don.myruns.R;

/**
 * Created by don on 4/22/16.
 */
public class ExerciseEntry {
    public Long id;

    public int mInputType;        // Manual, GPS or automatic
    public int mActivityType;     // Running, cycling etc.
    public DateTime mDateTime;    // When does this entry happen
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

    public static String getActivityType(int id, Resources r) {
        String[] activities = r.getStringArray(R.array.activity_type);
        if (id >= activities.length) {
            throw new IllegalArgumentException("Bad ID specified");
        }
        return activities[id];
    }

    /**
     * Get input type as string given input ID as saved in ExerciseEntry
     * @param id id of input type
     * @return input type as string
     */
    public static String getInputType(int id) {
        switch(id) {
            case 0:
                return "Manual Entry";
            case 1:
                return "GPS";
            case 2:
                // TODO: Make this depend on "Automatic" functionality
                return "Automatic";
        }
        throw new InputMismatchException("Bad ID specified");
    }

    /**
     * Get date from mDateTime formatted as hh:mm:ss MM:DD:YYYY
     * @return
     */
    public String getDate() {
        DateTime dateTime = new DateTime(mDateTime);
        DateTimeFormatter fmd = DateTimeFormat.forPattern("HH:mm:ss MMM dd yyyy");
        return fmd.print(dateTime);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss MMM dd yyyy");
//        String date = formatter.format(mDateTime.getTime());
//        return date;
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.valueOf(mDateTime.get(Calendar.HOUR_OF_DAY)))
//                .append(":")
//                .append(String.valueOf(mDateTime.get(Calendar.MINUTE)))
//                .append(":")
//                .append(String.valueOf(mDateTime.get(Calendar.SECOND)))
//                .append(" ")
//                .append()
    }

    public long storeDateTime() {
        return mDateTime.getMillis() / 1000;
    }
}