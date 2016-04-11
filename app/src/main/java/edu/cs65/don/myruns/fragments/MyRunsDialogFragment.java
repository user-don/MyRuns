package edu.cs65.don.myruns.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.cs65.don.myruns.activities.AccountPreferencesActivity;
import edu.cs65.don.myruns.R;

/**
 * Created by don on 3/31/16.
 * <p>
 * Fragment for dialog box of selecting profile image
 */
public class MyRunsDialogFragment extends DialogFragment {
    private static final String DIALOG_ID_KEY = "id_key";
    public static final int DIALOG_ID_PHOTO_PICKER = 0;

    // manual entries
    public static final int DATE       = 1;
    public static final int TIME       = 2;
    public static final int DURATION   = 3;
    public static final int DISTANCE   = 4;
    public static final int CALORIES   = 5;
    public static final int HEART_RATE = 6;
    public static final int COMMENT    = 7;


    // For photo picker selection:
    public static final int ID_PHOTO_PICKER_FROM_CAMERA = 0;

    // instantiate builder
    AlertDialog.Builder builder;
    Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get ID to figure out what dialog we want to show
        int dialog_id = getArguments().getInt(DIALOG_ID_KEY);
        final Activity parent = getActivity();
        switch (dialog_id) {
            case DIALOG_ID_PHOTO_PICKER:
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.select_profile_image);

                builder.setItems(R.array.ui_profile_photo_picker_items,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // the which argument contains the index position of the selected item
                        switch (which) {
                            case 0:
                                // Item is ID_PHOTO_PICKER_FROM_CAMERA
                                // Call the onPhotoPickerItemSelected in the parent
                                // activity, i.e., cameraControlActivity in this case
                                ((AccountPreferencesActivity) parent).onPhotoPickerItemSelected(which);

                            case 1:
                                // TODO: Load photo from phone library
                        }
                    }
                });
                return builder.create();

            case DATE:
                DatePickerDialog.OnDateSetListener date_listener =
                        new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // grab data somehow
                    }
                };
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), date_listener, year, month, day);

            case TIME:
                TimePickerDialog.OnTimeSetListener time_listener =
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // grab data
                    }
                };
                calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(getActivity(), time_listener, hourOfDay, minute, false);
            case DURATION:
                builder = new AlertDialog.Builder(getActivity());
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText("", TextView.BufferType.EDITABLE);
                builder.setView(input);
                builder.setTitle(R.string.manual_entry_duration);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();
                        Log.d("RUNS", "text input is: " + text);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                return builder.create();
            case DISTANCE:
                builder = constructSimpleDialogWithStringInput(R.string.manual_entry_distance);
                return builder.create();
            case CALORIES:
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.manual_entry_calories);
                return builder.create();
            case HEART_RATE:
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.manual_entry_heart_rate);
                return builder.create();
            case COMMENT:
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.manual_entry_comment);
                return builder.create();
        }
        // If dialog ID does not match with one of the above cases, throw exception
        throw new IllegalArgumentException("Bad dialog ID specified");
    }

    public static MyRunsDialogFragment newInstance(int dialog_id) {
        MyRunsDialogFragment frag = new MyRunsDialogFragment();
        Bundle args = new Bundle();
        // we want to pass ID to the activity
        args.putInt(DIALOG_ID_KEY, dialog_id);
        frag.setArguments(args);
        return frag;
    }

    private AlertDialog.Builder constructSimpleDialogWithStringInput(int title) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText("", TextView.BufferType.EDITABLE);
        b.setView(input);
        b.setTitle(title);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                Log.d("RUNS", "text input is: " + text);
            }
        });
        b.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return b;
    }
}
