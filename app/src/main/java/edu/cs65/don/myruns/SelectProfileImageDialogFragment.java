package edu.cs65.don.myruns;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by don on 3/31/16.
 *
 * Fragment for dialog box of selecting profile image
 */
public class SelectProfileImageDialogFragment extends DialogFragment {

    private static final String DIALOG_ID_KEY = "id_key";
    private static final int DIALOG_ID_PHOTO_PICKER = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get ID to figure out what dialog we want to show
        int dialog_id = getArguments().getInt(DIALOG_ID_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_profile_image);

        builder.setItems(R.array.ui_profile_photo_picker_items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // the which argument contains the index position of the selected item
                switch(which) {
                    case 0:
                        // take picture from camera
                    case 1:
                        // do something else

                }
            }
        });
        return builder.create();


        // all of our options are stored in an array in rsources

//        DialogInterface.OnClickListener dlistener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//
//            }
//        };
                //builder.setItems()


    }

    public static SelectProfileImageDialogFragment newInstance(int dialog_id) {
        SelectProfileImageDialogFragment frag = new SelectProfileImageDialogFragment();
        Bundle args = new Bundle();
        // we want to pass ID to the activity
        args.putInt(DIALOG_ID_KEY, dialog_id);
        frag.setArguments(args);
        return frag;
    }



}
