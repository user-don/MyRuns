package edu.cs65.don.myruns.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.don.myapplication.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cs65.don.myruns.MainActivity;
import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.MapDisplayActivity;
import edu.cs65.don.myruns.ManualInputActivity;
import edu.cs65.don.myruns.ServerUtilities;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.helpers.ExerciseEntryDbHelper;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * {@link Fragment} subclass for displaying the start screen of our runs app
 */
public class StartFragment extends Fragment {
    private final static String TAG = "Start Fragment";

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
                Log.d(TAG, "initiating history uploader");
                new HistoryUploaderAsyncTask(getActivity()).execute();
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

    class HistoryUploaderAsyncTask extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "History Uploader";

        private Context context;
        private DataController mDataController;
        private ExerciseEntryDbHelper mHelper;
        private GoogleCloudMessaging gcm;


        public HistoryUploaderAsyncTask(Context context) {
            this.context = context;
            mDataController = DataController.getInstance(context);
            mHelper = new ExerciseEntryDbHelper(context);
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<String> data = new ArrayList<>();
            ArrayList<ExerciseEntry> entries = mHelper.fetchEntries();

            if (entries != null) {
                for (ExerciseEntry entry : entries) {
                    data.add(mDataController.serializeEntry(entry));
                }

                Gson gson = new Gson();
                String list = gson.toJson(data);

                Map<String, String> map = new HashMap<>();
                map.put("DATA",list);

                Boolean success = false;
                try {
                    String response = ServerUtilities.post(MainActivity.SERVER_ADDR + "/postData.do", map);
                    Log.d(TAG,"Response message: " + response);
                    success = true;
                } catch (IOException e) {
                    Log.d(TAG, "error uploading data");
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Toast.makeText(context, "Upload Finished", Toast.LENGTH_LONG).show();
        }
    }
}
