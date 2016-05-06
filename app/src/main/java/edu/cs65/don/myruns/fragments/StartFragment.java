package edu.cs65.don.myruns.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.MapDisplayActivity;
import edu.cs65.don.myruns.ManualInputActivity;

/**
 * {@link Fragment} subclass for displaying the start screen of our runs app
 */
public class StartFragment extends Fragment {

    private Spinner input_type_spinner;
    private Spinner activity_type_spinner;
    private static final String RUNS = "runs";
    public static final String INPUT_KEY = "input type";
    public static final String ACTIVITY_KEY = "activity type";

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

    /**
     * Initializes onClickListeners for save and cancel buttons
     * @param v inflated view passed from onCreateView
     */
    private void initializeButtons(View v) {
        Button start_button = (Button) v.findViewById(R.id.start_button);
        Button sync_button = (Button) v.findViewById(R.id.sync_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch activity
                String [] input_type_array = getResources().getStringArray(R.array.input_type);
                String input_type = input_type_spinner.getSelectedItem().toString();

                if (input_type.equals(input_type_array[0])) {
                    // Manual entry. Launch ManualInputActivity class, passing activity type
                    // in the bundle
                    Intent intent = new Intent(getActivity(), ManualInputActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt(ACTIVITY_KEY,
                            activity_type_spinner.getSelectedItemPosition());
                    intent.putExtras(extras);
                    startActivity(intent);

                } else {
                    // GPS or Automatic, stuff selected type into bundle
                    Intent intent = new Intent(getActivity(), MapDisplayActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt(INPUT_KEY,
                            input_type_spinner.getSelectedItemPosition());
                    extras.putInt(ACTIVITY_KEY,
                            activity_type_spinner.getSelectedItemPosition());
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

    /**
     * Initialize inputType spinner
     * @param view inflated view
     */
    private void setInputTypeSpinnerContent(View view) {
        input_type_spinner = (Spinner) view.findViewById(R.id.input_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.input_type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        input_type_spinner.setAdapter(adapter);
    }

    /**
     * Initialize activityType spinner
     * @param view inflated view
     */
    private void setActivityTypeSpinnerContent(View view) {
        activity_type_spinner = (Spinner) view.findViewById(R.id.activity_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        activity_type_spinner.setAdapter(adapter);
    }
}
