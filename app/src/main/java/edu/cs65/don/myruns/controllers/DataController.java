package edu.cs65.don.myruns.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.TimeZone;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.helpers.ExerciseEntryDbHelper;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * DataController.java
 * Created by don on 4/24/16.
 */
public class DataController {

    public ExerciseEntryDbHelper dbHelper;
    public ArrayList<ExerciseEntry> entries = new ArrayList<>();

    private Context context;

    // For instantiating as a singleton
    private static DataController sDataController;
    public static DataController getInstance(@SuppressWarnings("UnusedParameters") Context c) {
        if(sDataController == null) {
            sDataController = new DataController();
        }
        return sDataController;
    }

    public void initializeData(Context context) {
        // attach dbHelper to the application context. prevent memory leaks!
        dbHelper = new ExerciseEntryDbHelper(context.getApplicationContext());
        this.context = context.getApplicationContext();
    }

    public void saveToDbAsync(ExerciseEntry entry) {
        new SaveToDB().execute(entry);
    }

    private class SaveToDB extends AsyncTask<ExerciseEntry, Void, Long> {

        @Override
        protected Long doInBackground(ExerciseEntry... params) {
            return dbHelper.insertEntry(params[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            Toast.makeText(context.getApplicationContext(),
                    "Entry #" + String.valueOf(result) + " saved.", Toast.LENGTH_LONG).show();
        }
    }

    public String getUnitPreferences() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString("unit_preference", "Imperial"); // Imperial if not available
    }

    public double milesToKm(double miles) {
        return miles * 1.609;
    }

    public double mphToKph(double mph) {
        return mph * 1.60934;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
