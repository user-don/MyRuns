package edu.cs65.don.myruns.helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * DataLoader class
 * Created by don on 4/25/16.
 */
public class DataLoader extends AsyncTaskLoader<ArrayList<ExerciseEntry>> {
    private static DataController mDataController;

    public DataLoader(Context context) {
        super(context);
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(context.getApplicationContext());
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override
    public ArrayList<ExerciseEntry> loadInBackground() {
        return mDataController.dbHelper.fetchEntries();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad(); // force an asynchronous load
    }


}
