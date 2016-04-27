package edu.cs65.don.myruns.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_entry_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataController = DataController.getInstance(getApplicationContext());
        setContentView(R.layout.activity_display_entry);
        // Take row ID from one bundle and pass to the next for asynchronous load!
        Bundle extras = getIntent().getExtras();
        long id = extras.getLong("id");
        Bundle args = new Bundle();
        args.putLong("id", id);
        getLoaderManager().initLoader(0, args, this);
    }

    private void initializeData() {
        // this should be called after onLoadFinished to initialize the data
        EditText et = (EditText) findViewById(R.id.input_type);
        et.setText(ExerciseEntry.getInputType(e.mInputType));
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.activity_type);
        et.setText(ExerciseEntry.getActivityType(e.mActivityType, getResources()));
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.date_and_time);
        String dateAndTime = e.getDate();
        et.setText(dateAndTime);
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.duration);
        et.setText(durationToString(e.mDuration));
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.distance);
        et.setText(distanceToString(e.mDistance));
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.calories);
        et.setText(String.valueOf(e.mCalorie) + " cals");
        et.setKeyListener(null);
        et = (EditText) findViewById(R.id.heart_rate);
        et.setText(String.valueOf(e.mHeartRate) + " bpm");
        et.setKeyListener(null);
    }

    public void deleteEntry(MenuItem v) {
        Runnable delete = new Runnable() {
            @Override
            public void run() {
                // This isn't running on the UI thread, so we can't directly modify
                // UI objects such as View objects.
                // http://developer.android.com/training/multiple-threads/define-runnable.html
                mDataController.dbHelper.removeEntry(e.id);
            }
        };
        delete.run();
        // close out DisplayEntryActivity when deleting the exercise entry
        finish();
    }

    @Override
    public Loader<ExerciseEntry> onCreateLoader(int id, Bundle args) {
        // get exercise entry ID from bundle and use to call our loader that will
        // pull this entry from the database
        long dbId = args.getLong("id");
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

    // Private helper methods for displaying information that is pulled from the
    // exercise entries. Since some display logic depends on context, which should
    // not be stored within our ExerciseEntry object, we put the methods here
    // or copy over to anywhere else where the information stored in our ExerciseEntry
    // object needs to be displayed in a different format.

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
