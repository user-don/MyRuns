package edu.cs65.don.myruns.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return view;

    }

}
