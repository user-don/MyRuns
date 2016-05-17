package edu.cs65.don.myruns;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cs65.don.myruns.controllers.DataController;

/**
 * Created by Varun on 2/18/16.
 *
 * used GCMIntentService as model from GCM demo
 */
public class GcmIntentService extends IntentService {


    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                //String[] messages = ((String) extras.get("message")).split(":");
                // requestType = messages[0] ; i.e. the delete
                // long id = Long.parseLong(message[1]);
                // if delete then remove

                String key = extras.getString("message");
                long index = Long.valueOf(key);
                // delete the key
                DataController.getInstance(getApplicationContext()).dbHelper.removeEntry(index);
                // TODO: Remove from the history list


                showToast(extras.getString("message"));
            }

        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}