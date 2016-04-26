package edu.cs65.don.myruns.helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * Created by don on 4/25/16.
 */
public class SingleEntryLoader extends AsyncTaskLoader<ExerciseEntry> {
    private static DataController mDataController;
    int id;


    public SingleEntryLoader(Context context, int id) {
        super(context);
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(context.getApplicationContext());
        this.id = id;
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
