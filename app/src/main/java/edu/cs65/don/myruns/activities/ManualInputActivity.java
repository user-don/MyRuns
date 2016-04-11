package edu.cs65.don.myruns.activities;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.fragments.MyRunsDialogFragment;

public class ManualInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        ListView lv = (ListView) findViewById(R.id.listView);
        assert lv != null;
        lv.setOnItemClickListener(clickListener);
    }

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position) {
                case 0:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.DATE, "date_dialog_fragment");
                    return;
                case 1:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.TIME, "time_dialog_fragment");
                    return;
                case 2:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.DURATION, "duration_dialog_fragment");
                    return;
                case 3:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.DISTANCE, "distance_dialog_fragment");
                    return;
                case 4:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.CALORIES, "calories_dialog_fragment");
                    return;
                case 5:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.HEART_RATE, "heart_rate_dialog_fragment");
                    return;
                case 6:
                    Log.d("RUNS", "Position " + String.valueOf(position));
                    displayDialog(MyRunsDialogFragment.COMMENT, "comment_dialog_fragment");
                    return;
                default:
                    // do nothing
            }
        }
    };

    private void displayDialog(int id, String tag) {
        DialogFragment fragment = MyRunsDialogFragment.newInstance(id);
        fragment.show(getFragmentManager(), tag);
    }


}
