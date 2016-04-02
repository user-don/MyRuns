package edu.cs65.don.myruns;

import android.app.DialogFragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_URI = "image_uri";
    private static final int ID_PHOTO_PICKER_FROM_CAMERA = 0;
    private static final int DIALOG_ID_PHOTO_PICKER = 1;
    private static final String RUNS = "runs";
    private ImageView mImageView;
    private Uri mImageCaptureUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(RUNS, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // clear soft keyboard until text view tapped
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        // load previously set profile photo
        loadProfilePhoto(savedInstanceState);


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

        // call this to save something.
        //savedInstanceState.putParcelable(URI_INSTANCE_STATE_KEY, mImageCaptureUri);
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
    public void displayDialog(int id) {
        switch(id) {
            case 1:
                DialogFragment fragment = SelectProfileImageDialogFragment.newInstance(id);
                fragment.show(getFragmentManager(), "dialog_fragment_photo_picker");
        }
    }

    /**
     * Display photo picker dialog box
     */
    public void displayPhotoDialog(View view) {
        Log.d(RUNS, "display photo dialog called");
        displayDialog(DIALOG_ID_PHOTO_PICKER);
    }

    // ****************** private helper functions ***************************//

    private void loadProfilePhoto(Bundle savedInstanceState) {
        // Saved state stuff
        mImageView = (ImageView) findViewById(R.id.prof_photo);
        if (savedInstanceState != null) {
            mImageCaptureUri = savedInstanceState.getParcelable(IMAGE_URI);
        }
    }

}
