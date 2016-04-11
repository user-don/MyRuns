package edu.cs65.don.myruns.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.activities.GPSModeActivity;
import edu.cs65.don.myruns.activities.ManualInputActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private Spinner input_type_spinner;
    private Spinner activity_type_spinner;
    private static final String RUNS = "runs";
    private Button start_button;
    private Button sync_button;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        setInputTypeSpinnerContent(view);
        setActivityTypeSpinnerContent(view);
        // set up button listeners
        initializeButtons(view);
        return view;
    }

    private void initializeButtons(View v) {
        start_button = (Button) v.findViewById(R.id.start_button);
        sync_button = (Button) v.findViewById(R.id.sync_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch activity
                Log.d(RUNS, "start tapped");
                String [] input_type_array = getResources().getStringArray(R.array.input_type);
                String input_type = input_type_spinner.getSelectedItem().toString();
                if (input_type.equals(input_type_array[0])) {
                    // Manual entry. Launch ManualInputActivity class, passing activity type
                    // in the bundle
                    Intent intent = new Intent(getActivity(), ManualInputActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("activity_type",
                            activity_type_spinner.getSelectedItem().toString());
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                // TODO: Fill out rest of IF statement
                else {
                    // GPS or Automatic, pull up GPS mode for both
                    // TODO: Eventually differentiate Automatic mode
                    Intent intent = new Intent(getActivity(), GPSModeActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("activity_type",
                            activity_type_spinner.getSelectedItem().toString());
                    intent.putExtras(extras);
                    startActivity(intent);
                }


            }
        });
        sync_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // sync
            }
        });
    }

    private void setInputTypeSpinnerContent(View view) {
        input_type_spinner = (Spinner) view.findViewById(R.id.input_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.input_type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        input_type_spinner.setAdapter(adapter);
    }

    private void setActivityTypeSpinnerContent(View view) {
        activity_type_spinner = (Spinner) view.findViewById(R.id.activity_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        activity_type_spinner.setAdapter(adapter);
    }

    // TODO: Pass activity type to new activities by putting value in intent's extras

}