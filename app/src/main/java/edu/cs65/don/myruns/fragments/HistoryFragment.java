package edu.cs65.don.myruns.fragments;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.cs65.don.myruns.MapEntryDisplayActivity;
import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.DisplayEntryActivity;
import edu.cs65.don.myruns.adapters.ActivityEntriesAdapter;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.helpers.Common;
import edu.cs65.don.myruns.helpers.DataLoader;
import edu.cs65.don.myruns.models.ExerciseEntry;


/**
 * A simple {@link Fragment} subclass for showing run history.
 */
public class HistoryFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<ExerciseEntry>> {

    private static DataController mDataController;
    private View mView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(getActivity().getApplicationContext());
        mView = view;
        return view;

    }

    /**
     * Helper method called by async task to update the master list of ExerciseEntries
     * stored in the DataController
     * @param entries New list of all exercise entries
     */
    private void fillExerciseEntriesFromDB(ArrayList<ExerciseEntry> entries) {
        mDataController.entries = entries;
    }

    @Override
    public Loader<ArrayList<ExerciseEntry>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ExerciseEntry>> loader,
                               ArrayList<ExerciseEntry> data) {
        fillExerciseEntriesFromDB(data);
        final ActivityEntriesAdapter adapter = new ActivityEntriesAdapter(mView.getContext(),
                R.layout.history_table_row, mDataController.entries);
        final ListView history = (ListView) mView.findViewById(R.id.historyList);
        history.setAdapter(adapter);
        history.setOnItemClickListener(clickListener);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ExerciseEntry>> loader) {
        // do nothing here
    }

    private final AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ExerciseEntry entry = mDataController.entries.get(position);
            if (entry.mInputType != Common.INPUT_TYPE_MANUAL) {
                Intent intent = new Intent(getActivity(), MapEntryDisplayActivity.class);
                intent.putExtra("id", entry.id);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), DisplayEntryActivity.class);
                intent.putExtra("id", entry.id);
                startActivity(intent);
            }
        }
    };
}
