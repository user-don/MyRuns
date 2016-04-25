package edu.cs65.don.myruns.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;


/**
 * A simple {@link Fragment} subclass for showing run history.
 */
public class HistoryFragment extends Fragment {

    private static DataController mDataController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        // TODO: Get the DataController and test db storage with logging or inspector
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(getActivity().getApplicationContext());
        initTable(view);
        return view;

    }

    private void initTable(View v) {
        TableLayout layout = (TableLayout) v.findViewById(R.id.runsTable);
        // for each entry in mDataController.entries, create a table entry
        // and place two rows of text
        int tableIndex = 0;
        for (ExerciseEntry entry : mDataController.entries) {
//            TableRow row = new TableRow(v.getContext());
//            TextView firstLine = new TextView(v.getContext());
//            TextView secondLine = new TextView(v.getContext());
//            firstLine.setText("Manual Entry ASDF " +
//                mDataController.getInputType(entry.mActivityType));
//            secondLine.setText("Miles");
//            row.addView(firstLine);
//            row.addView(secondLine);
            View row = initRow(v, entry);
            layout.addView(row, tableIndex);
            tableIndex++;
        }
    }

    private View initRow(View v, ExerciseEntry e) {
        TableRow tr = new TableRow(v.getContext());
        View row = LayoutInflater.from(v.getContext())
                .inflate(R.layout.history_table_row, tr, false);
        TextView firstLine = (TextView) row.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) row.findViewById(R.id.secondLine);
        StringBuilder first = new StringBuilder();
        first.append(mDataController.getInputType(e.mActivityType))
                .append(": ")
                .append(mDataController.getActivityType(e.mActivityType, getResources()))
                .append(", ")
                .append(e.mDateTime.toString());
        firstLine.setText(first.toString());
        Log.d("RUNS", first.toString());
        StringBuilder second = new StringBuilder();
        second.append(e.mDistance + " Miles, ");
        if (e.mDuration == 0) {
            second.append("0secs");
        } else {
            second.append(e.mDuration + "min 0secs");
        }
        secondLine.setText(second.toString());
        Log.d("RUNS", second.toString());
        return row;
    }

}
