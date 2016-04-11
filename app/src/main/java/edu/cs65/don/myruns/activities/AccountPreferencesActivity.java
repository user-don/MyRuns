package edu.cs65.don.myruns.activities;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.fragments.MyRunsDialogFragment;

public class AccountPreferencesActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;


    private static final String URI_INSTANCE_STATE_KEY = "saved_uri";

    private static final String RUNS = "runs";
    private ImageView mImageView;
    private Uri mImageCaptureUri;
    private Uri tempImageUri;
    private boolean isTakenFromCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_preferences);

        // clear soft keyboard until text view tapped
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // load previously set settings
        mImageView = (ImageView) findViewById(R.id.prof_photo);
        Boolean tempImageAvailable = false;
        if (savedInstanceState != null) {
            // activity is restarting due to interruption or rotation. attempt
            // to recover unsaved photo if available
            tempImageUri = savedInstanceState.getParcelable(URI_INSTANCE_STATE_KEY);
            if (tempImageUri != null) {
                tempImageAvailable = true;
                mImageView.setImageURI(tempImageUri);
            }
        }
        if (!tempImageAvailable) { // temp image not available, load from saved data
            loadSnap();
        }
        loadProfile(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        // Will only be called if the Activity has been
        // killed by the system since it was last visible.
        Log.d(RUNS, "onRestoreInstanceState called");
    }

    /**
     * Called before subsequent visible lifetimes for an activity process
     */
    @Override
    public void onRestart() {
        super.onRestart();
        // Load changes knowing that the Activity has already
        // been visible within this process.
        Log.d(RUNS, "onRestart called");
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the Activity is visible.
        Log.d(RUNS, "onStart called");
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        // Resume any paused UI updates, threads, or processes required
        // by the Activity but suspended when it was inactive.
        Log.d(RUNS, "onResume called");
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate and
        // onRestoreInstanceState if the process is
        // killed and restarted by the run time.
        super.onSaveInstanceState(savedInstanceState);
        Log.d("app", "onSaveInstanceState called");
        savedInstanceState.putParcelable(URI_INSTANCE_STATE_KEY, tempImageUri);
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground Activity.
        Log.d(RUNS, "onPause called");
        super.onPause();
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Activity isn't visible.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        Log.d(RUNS, "onStop called");
        super.onStop();
    }

    // Sometimes called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads,
        // closing database connections etc.
        Log.d(RUNS, "onDestroy called");
        super.onDestroy();
    }

    /**
     * Displays dialog fragment. Dialog displayed depends on the specified id;
     * For now there is only one dialog (changing photo), but this generic function
     * has the capability to be expanded in future.
     * @param id ID of the dialog to be displayed
     */
    private void displayDialog(int id) {
        DialogFragment fragment = MyRunsDialogFragment.newInstance(id);
        fragment.show(getFragmentManager(), "dialog_fragment_photo_picker");
    }

    /**
     * Display photo picker dialog box
     */
    public void displayPhotoDialog(@SuppressWarnings("UnusedParameters") View v) {
        Log.d(RUNS, "display photo dialog called");
        displayDialog(MyRunsDialogFragment.DIALOG_ID_PHOTO_PICKER);
    }

    /**
     * Handle save and cancel button presses
     * @param v the view corresponding to the pressed button
     */
    public void onSaveCancelClicked (View v) {
        if (v.getId() == R.id.save_button) {
            saveProfile();
        } else if (v.getId() == R.id.cancel_button) {
            finish();
        }
    }

    /**
     * Callback function that handles results of activities started with the
     * startActivityForResult command
     * @param requestCode specified in the startActivityForResult() command, and allows
     *                    us to identify which request we are handling
     * @param resultCode indicates the success of the activity
     * @param data intent containing data from the activity's actions
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            // callback for taking photo
            case REQUEST_IMAGE_CAPTURE:
                cropImage(mImageCaptureUri);
            // callback for cropping activity
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);

                // Delete temporary image taken by camera after crop.
                if (isTakenFromCamera) {
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists())
                        //noinspection ResultOfMethodCallIgnored
                        f.delete();
                }

        }
    }

    /**
     * Actions to execute based on results of our dialog fragment
     * @param item the position (starting at 0) of the item selected in the dialog
     */
    public void onPhotoPickerItemSelected(int item){

        switch(item) {
            case MyRunsDialogFragment.ID_PHOTO_PICKER_FROM_CAMERA:
                dispatchTakePictureIntent();
            default:
                // do nothing
        }
    }

    // ****************** private helper functions ***************************//

    /**
     * Dispatch a take picture intent and start Android's photo taking application
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Construct temporary image path and name to save the taken
        // photo
        mImageCaptureUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "tmp_"
                + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                mImageCaptureUri);
        takePictureIntent.putExtra("return-data", true);
        try {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        isTakenFromCamera = true;
    }

    /**
     * Save user input data using SharedPreference object. Use toast to indicate data saved.
     */
    @SuppressWarnings("ConstantConditions")
    private void saveProfile() {
        Log.d(RUNS, "save profile");
        // Get shared preferences editor
        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.clear();

        // Save preferences: Name
        mKey = getString(R.string.preference_key_profile_name);
        EditText et = (EditText) findViewById(R.id.name_text);
        String mValue = et != null ? et.getText().toString() : "";
        mEditor.putString(mKey, mValue);

        // Email
        mKey = getString(R.string.preference_key_profile_email);
        et = (EditText) findViewById(R.id.email_text);
        mValue = et != null ? et.getText().toString() : "";
        mEditor.putString(mKey, mValue);

        // Phone Number
        mKey = getString(R.string.preference_key_profile_phone_number);
        et = (EditText) findViewById(R.id.phone_num_text);
        mValue = et != null ? et.getText().toString() : "";
        mEditor.putString(mKey, mValue);

        // Gender
        mKey = getString(R.string.preference_key_profile_gender);
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
        int mIntValue = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));
        mEditor.putInt(mKey, mIntValue);

        // Class Year
        mKey = getString(R.string.preference_key_profile_class);
        et = (EditText) findViewById(R.id.class_text);
        mValue = et != null ? et.getText().toString() : "";
        mEditor.putString(mKey, mValue);

        // Major
        mKey = getString(R.string.preference_key_profile_major);
        et = (EditText) findViewById(R.id.major_text);
        mValue = et != null ? et.getText().toString() : "";
        mEditor.putString(mKey, mValue);

        // Save profile photo
        saveSnap();

        // Apply settings and pop up toast dialog
        mEditor.apply();
        Toast.makeText(getApplicationContext(),
                R.string.profile_info_saved, Toast.LENGTH_SHORT).show();
        // quit the app
        finish();
    }

    /**
     * Help load user data that has already been saved.
     */
    @SuppressWarnings("ConstantConditions")
    private void loadProfile(@SuppressWarnings("UnusedParameters") Bundle savedInstanceState) {
        Log.d(RUNS, "load user profile");
        // get sharedPreferences
        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        // get the preferences: name
        mKey = getString(R.string.preference_key_profile_name);
        String mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.name_text)).setText(mValue);

        // email
        mKey = getString(R.string.preference_key_profile_email);
        mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.email_text)).setText(mValue);

        // phone number
        mKey = getString(R.string.preference_key_profile_phone_number);
        mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.phone_num_text)).setText(mValue);

        // gender info
        mKey = getString(R.string.preference_key_profile_gender);
        int mIntValue = mPrefs.getInt(mKey, -1);
        // in case there isn't one saved before
        if (mIntValue >= 0) {
            // Find the radio button that should be checked
            RadioButton radioBtn = (RadioButton) ((RadioGroup) findViewById(R.id.radioGender))
                    .getChildAt(mIntValue);
            // check the button
            radioBtn.setChecked(true);
        }

        // class
        mKey = getString(R.string.preference_key_profile_class);
        mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.class_text)).setText(mValue);

        // major
        mKey = getString(R.string.preference_key_profile_major);
        mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.major_text)).setText(mValue);
    }

    /**
     * Load a profile photo from internal storage
     */
    private void loadSnap() {
        try {
            FileInputStream fis = openFileInput(getString(R.string.profile_photo_file_name));
            Bitmap bmap = BitmapFactory.decodeStream(fis);
            mImageView.setImageBitmap(bmap);
            fis.close();
        } catch (IOException e) {
            // Default profile photo if no photo saved before.
            Log.d(RUNS, "Caught IOException loading profile photo from internal storage."
            + " defaulting to default profile photo");
            mImageView.setImageResource(R.drawable.default_profile);
        }
    }

    /**
     * Save profile photo to internal storage
     */
    private void saveSnap() {
        // Commit all the changes into preference file
        // Save profile image into internal storage.
        mImageView.buildDrawingCache();
        Bitmap bmap = mImageView.getDrawingCache();
        try {
            FileOutputStream fos = openFileOutput(
                    getString(R.string.profile_photo_file_name), MODE_PRIVATE);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Crop and resize the image for profile
     * @param source the URI of the source image to be cropped
     */
    private void cropImage(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(100, 100).start(this);

    }

    /**
     * Update profile photo thumbnail to match the results of crop activity
     * @param resultCode indicates success of cropping activity
     * @param result intent representing the result of the cropping activity
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            tempImageUri = Crop.getOutput(result);
            //mImageView.setImageURI(Crop.getOutput(result));
            mImageView.setImageURI(tempImageUri);
            Log.d(RUNS, "tempImageUri set to " + String.valueOf(tempImageUri));
        } else if (resultCode == Crop.RESULT_ERROR) {
            //noinspection ThrowableResultOfMethodCallIgnored
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}
