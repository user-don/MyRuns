package edu.cs65.don.myruns.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
        String first = buildFirstString(e);
        firstLine.setText(first);
        Log.d("RUNS", first);
        String second = buildSecondString(e);
        secondLine.setText(second);
        Log.d("RUNS", second);
        return convertView;
    }

    private String buildFirstString(ExerciseEntry e) {
        StringBuilder first = new StringBuilder();
        first.append(ExerciseEntry.getInputType(e.mInputType))
                .append(": ")
                .append(ExerciseEntry.getActivityType(e.mActivityType, context.getResources()))
                .append(", ")
                .append(e.getDate());
        return first.toString();
    }

    private String buildSecondString(ExerciseEntry e) {
        StringBuilder second = new StringBuilder();
        String unit_preference = getUnitPreferences();
        String[] unit_prefs = context.getResources()
                .getStringArray(R.array.entryvalues_unit_preference);
        if (unit_preference.equals(unit_prefs[0])) {
            // metric
            second.append(String.valueOf(milesToKm(e.mDistance)) + " Kilometers, ");
        } else {
            // imperial
            second.append(String.valueOf(e.mDistance) + " Miles, ");
        }
        if (e.mDuration == 0) {
            second.append("0secs");
        } else {
            second.append(e.mDuration + "min 0secs");
        }
        return second.toString();
    }

    private String getUnitPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("unit_preference", "Imperial"); // Imperial if not available
    }

    private double milesToKm(double miles) {
        return miles * 1.609;
    }
}
