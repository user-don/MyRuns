package edu.cs65.don.myruns.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.adapters.ActivityEntriesAdapter;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;


/**
 * A simple {@link Fragment} subclass for showing run history.
 */
public class HistoryFragment extends Fragment {

    private static DataController mDataController;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(getActivity().getApplicationContext());
        getExerciseEntriesFromDB();
        mView = view;
        return view;

    }

    /**
     * Asynchronously refresh the ListView with new data from database.
     */
    public void getExerciseEntriesFromDB() {
        new GetExerciseEntriesFromDB().execute();
    }

    /**
     * Helper method called by async task to update the master list of ExerciseEntries
     * stored in the DataController
     * @param entries New list of all exercise entries
     */
    private void fillExerciseEntriesFromDB(ArrayList<ExerciseEntry> entries) {
        mDataController.entries = entries;
    }

    /**
     * Asynchronous task for getting exercise entries from database and subsequently
     * updating the UI
     */
    private class GetExerciseEntriesFromDB extends AsyncTask<Void, Void, ArrayList<ExerciseEntry>> {

        @Override
        protected ArrayList<ExerciseEntry> doInBackground(Void... params) {
            return mDataController.dbHelper.fetchEntries();
        }

        @Override
        protected void onPostExecute(ArrayList<ExerciseEntry> exerciseEntries) {
            fillExerciseEntriesFromDB(exerciseEntries);
            final ActivityEntriesAdapter adapter = new ActivityEntriesAdapter(mView.getContext(),
                    R.layout.history_table_row, mDataController.entries);
            final ListView history = (ListView) mView.findViewById(R.id.historyList);
            history.setAdapter(adapter);
            super.onPostExecute(exerciseEntries);
        }
    }

    //    private void initTable(View v) {
//        TableLayout layout = (TableLayout) v.findViewById(R.id.runsTable);
//        // for each entry in mDataController.entries, create a table entry
//        // and place two rows of text
//        int tableIndex = 0;
//        for (ExerciseEntry entry : mDataController.entries) {
//            View row = initRow(v, entry);
//            layout.addView(row, tableIndex);
//            tableIndex++;
//        }
//    }
//
//    private View initRow(View v, ExerciseEntry e) {
//        TableRow tr = new TableRow(v.getContext());
//        View row = LayoutInflater.from(v.getContext())
//                .inflate(R.layout.history_table_row, tr, false);
//        TextView firstLine = (TextView) row.findViewById(R.id.firstLine);
//        TextView secondLine = (TextView) row.findViewById(R.id.secondLine);
//        StringBuilder first = new StringBuilder();
//        first.append(mDataController.getInputType(e.mActivityType))
//                .append(": ")
//                .append(mDataController.getActivityType(e.mActivityType, getResources()))
//                .append(", ")
//                .append(e.mDateTime.toString());
//        firstLine.setText(first.toString());
//        Log.d("RUNS", first.toString());
//        StringBuilder second = new StringBuilder();
//        second.append(e.mDistance + " Miles, ");
//        if (e.mDuration == 0) {
//            second.append("0secs");
//        } else {
//            second.append(e.mDuration + "min 0secs");
//        }
//        secondLine.setText(second.toString());
//        Log.d("RUNS", second.toString());
//        return row;
//    }

}
