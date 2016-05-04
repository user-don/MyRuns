package edu.cs65.don.myruns;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.MutableDateTime;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.fragments.MyRunsDialogFragment;
import edu.cs65.don.myruns.helpers.Common;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class ManualInputActivity extends AppCompatActivity
        implements MyRunsDialogFragment.MyRunsDialogListener {

    private ExerciseEntry entry;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    private boolean dateSelected = false;
    private boolean timeSelected = false;
    private static DataController mDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // instantiate the data controller as a singleton
        mDataController = DataController.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        ListView lv = (ListView) findViewById(R.id.listView);
        assert lv != null;
        lv.setOnItemClickListener(clickListener);
        entry = new ExerciseEntry();
        entry.mInputType = Common.INPUT_TYPE_MANUAL;
        // get activity type from bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // there is absolutely no reason this should be throwing an error. It's stupid.
            entry.mActivityType = extras.getInt("activity_type");
        }
    }

    /**
     * Create OnItemClickListener for the ListView defined in onCreate()
     */
    private final AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position) {
                case 0:
                    displayDialog(MyRunsDialogFragment.DATE, "date_dialog_fragment");
                    return;
                case 1:
                    displayDialog(MyRunsDialogFragment.TIME, "time_dialog_fragment");
                    return;
                case 2:
                    displayDialog(MyRunsDialogFragment.DURATION, "duration_dialog_fragment");
                    return;
                case 3:
                    displayDialog(MyRunsDialogFragment.DISTANCE, "distance_dialog_fragment");
                    return;
                case 4:
                    displayDialog(MyRunsDialogFragment.CALORIES, "calories_dialog_fragment");
                    return;
                case 5:
                    displayDialog(MyRunsDialogFragment.HEART_RATE, "heart_rate_dialog_fragment");
                    return;
                case 6:
                    displayDialog(MyRunsDialogFragment.COMMENT, "comment_dialog_fragment");
                    return;
                default:
            }
        }
    };

    /**
     * Create DialogFragment with specified ID
     * @param id ID that corresponds to the desired dialog fragment to be constructed. Must be
     *           specified using one of the static ints in {@link MyRunsDialogFragment}.
     * @param tag The tag for this fragment, as per {@link android.app.FragmentTransaction}
     */
    private void displayDialog(int id, String tag) {
        DialogFragment fragment = MyRunsDialogFragment.newInstance(id);
        fragment.show(getFragmentManager(), tag);
    }

    /**
     * Function handler for save and cancel buttons
     * @param v specifies which button was pressed
     */
    public void onSaveCancelClicked(View v) {
        if (v.getId() == R.id.manual_entry_save_button) {
            // build calendar object if available
            entry.mDateTime = buildCalendar();
            mDataController.saveToDbAsync(entry);
            finish();
        } else { // cancel pressed
            finish();
            Toast.makeText(getApplicationContext(), "Entry discarded.", Toast.LENGTH_SHORT).show();
        }
    }

    private DateTime buildCalendar() {
        // build calendar using given information.
        // if neither date nor time is specified, default to current date and time
        // if only date is specified, show that date but with current time
        // if only time is specified, use that time with current date
        MutableDateTime dateTime = new MutableDateTime(); // initialized to current time/date
        if (dateSelected && timeSelected) {
            // JodaTime has one-indexed months, so we need to add one to the results that we get
            // from the DatePickerDialog (which indexes at zero). Not fun.
            dateTime.setDateTime(year, monthOfYear + 1, dayOfMonth, hourOfDay, minute, 0, 0);
            return dateTime.toDateTime();
        } else if (dateSelected) {
            // only date specified. show date with current time (which we get by default
            // with the getInstance() method)
            dateTime.setDateTime(year, monthOfYear + 1, dayOfMonth, dateTime.getHourOfDay(),
                    dateTime.getMinuteOfHour(), 0, 0);
            return dateTime.toDateTime();
        } else if (timeSelected) {
            dateTime.set(DateTimeFieldType.secondOfMinute(), 0);
            return dateTime.toDateTime();
        } else {
            return dateTime.toDateTime();
        }
    }

    @Override
    public void onDateSelected(int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        dateSelected = true;
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        timeSelected = true;
    }

    @Override
    public void onDurationSelected(int duration) {
        entry.mDuration = duration * 60;
    }

    @Override
    public void onDistanceSelected(double distance) {
        entry.mDistance = distance;
    }

    @Override
    public void onCaloriesSelected(int calories) {
        entry.mCalorie = calories;
    }

    @Override
    public void onHeartRateSelected(int heartRate) {
        entry.mHeartRate = heartRate;
    }

    @Override
    public void onCommentSelected(String comment) {
        entry.mComment = comment;
    }
}
