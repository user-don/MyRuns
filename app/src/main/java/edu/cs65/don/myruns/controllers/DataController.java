package edu.cs65.don.myruns.controllers;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

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

    public final int INPUT_TYPE_MANUAL = 0;
    public final int INPUT_TYPE_GPS = 1;
    public final int INPUT_TYPE_AUTOMATIC = 2;
    public ExerciseEntryDbHelper dbHelper;
    public ArrayList<ExerciseEntry> entries = new ArrayList<>();

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
    }

    public void saveToDbAsync(ExerciseEntry entry) {
        new SaveToDB().execute(entry);
    }

    private class SaveToDB extends AsyncTask<ExerciseEntry, Void, Void> {

        @Override
        protected Void doInBackground(ExerciseEntry... params) {
            dbHelper.insertEntry(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // do nothing...
        }
    }


}