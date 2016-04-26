package edu.cs65.don.myruns.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.helpers.SingleEntryLoader;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class DisplayEntryActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ExerciseEntry> {

    ExerciseEntry e;
    private static DataController mDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataController = DataController.getInstance(getApplicationContext());
        setContentView(R.layout.activity_display_entry);
        // Take row ID from one bundle and pass to the next for asynchronous load!
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        Bundle args = new Bundle();
        args.putInt("id", id);
        getLoaderManager().initLoader(0, args, this);
    }

    private void initializeData() {
        // this should be called after onLoadFinished to initialize the data
        EditText et = (EditText) findViewById(R.id.input_type);
        et.setText(ExerciseEntry.getInputType(e.mInputType));
        et = (EditText) findViewById(R.id.activity_type);
        et.setText(ExerciseEntry.getActivityType(e.mActivityType, getResources()));
        et = (EditText) findViewById(R.id.date_and_time);
        String dateAndTime = e.getDate();
        et.setText(dateAndTime);
        et = (EditText) findViewById(R.id.duration);
        et.setText(durationToString(e.mDuration));
        et = (EditText) findViewById(R.id.distance);
        et.setText(distanceToString(e.mDistance));
        et = (EditText) findViewById(R.id.calories);
        et.setText(String.valueOf(e.mCalorie) + " cals");
        et = (EditText) findViewById(R.id.heart_rate);
        et.setText(String.valueOf(e.mHeartRate) + " bpm");
    }

    @Override
    public Loader<ExerciseEntry> onCreateLoader(int id, Bundle args) {
        int dbId = args.getInt("id");
        return new SingleEntryLoader(this, dbId);
    }

    @Override
    public void onLoadFinished(Loader<ExerciseEntry> loader, ExerciseEntry data) {
        // once load is finished, initialize the interface.
        this.e = data;
        initializeData();
    }

    @Override
    public void onLoaderReset(Loader<ExerciseEntry> loader) {
        // do nothing
    }

    private String durationToString(int duration) {
        if (duration == 0) {
            return "0secs";
        } else {
            return String.valueOf(duration) + "min 0secs";
        }
    }

    private String distanceToString(double distance) {
        String unit_preference = getUnitPreferences();
        String[] unit_prefs = this.getResources()
                .getStringArray(R.array.entryvalues_unit_preference);
        if (unit_preference.equals(unit_prefs[0])) {
            // metric
            return String.valueOf(milesToKm(distance)) + " Kilometers";
        } else {
            return String.valueOf(distance) + " Miles";
        }
    }

    private String getUnitPreferences() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        return prefs.getString("unit_preference", "Imperial"); // Imperial if not available
    }

    private double milesToKm(double miles) {
        return miles * 1.609;
    }


}
