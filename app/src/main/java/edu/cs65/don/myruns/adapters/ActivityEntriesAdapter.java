package edu.cs65.don.myruns.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * Created by don on 4/25/16.
 */
public class ActivityEntriesAdapter extends ArrayAdapter<ExerciseEntry> {
    private static DataController mDataController;

    Context context;
    ArrayList<ExerciseEntry> entries;
    int resource;

    public ActivityEntriesAdapter(Context context, int resource, ArrayList<ExerciseEntry> entries) {
        super(context, resource, entries);
        this.context = context;
        this.entries = entries;
        this.resource = resource;
        mDataController = DataController.getInstance(context.getApplicationContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView firstLine = (TextView) convertView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) convertView.findViewById(R.id.secondLine);
        ExerciseEntry e = mDataController.entries.get(position);
        StringBuilder first = new StringBuilder();
        first.append(ExerciseEntry.getInputType(e.mInputType))
                .append(": ")
                .append(ExerciseEntry.getActivityType(e.mActivityType, parent.getResources()))
                .append(", ")
                .append(e.getDate());
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
        return convertView;
    }
}
