package edu.cs65.don.myruns.helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * SingleEntryLoader: Class for loading a single exercise entry from the database
 * asynchronously.
 * Created by don on 4/25/16.
 */
public class SingleEntryLoader extends AsyncTaskLoader<ExerciseEntry> {
    private static DataController mDataController;
    long id;

    public SingleEntryLoader(Context context, long id) {
        super(context);
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(context.getApplicationContext());
        this.id = id;
        Log.d("RUNS", "ID passed to SingleEntryLoader: " + id);
    }

    @Override
    public ExerciseEntry loadInBackground() {
        return mDataController.dbHelper.fetchEntryByIndex(id);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
