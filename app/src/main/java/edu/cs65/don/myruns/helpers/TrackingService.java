package edu.cs65.don.myruns.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.concurrent.ArrayBlockingQueue;

import edu.cs65.don.myruns.MapDisplayActivity;
import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.fragments.StartFragment;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * TrackingService.java
 * Created by don on 5/2/16.
 */
public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener, SensorEventListener {

    private static final String TAG = "TrackingService";

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    /* sensor locals */
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private SensorClassifierWorker mAsyncTask = null;         // processes sensor data
    private ArrayBlockingQueue<Double> mSensorDataBuffer;
    private int voteList[];

    public static final int ACCELEROMETER_QUEUE_LENGTH = 2048;
    public static final int ACCELEROMETER_BLOCK_CAPACITY = 64;

    protected ExerciseEntry entry;
    private final IBinder mBinder = new TrackingBinder();
    private boolean startTracking = false;
    private boolean onLocationChangedCalled = false;
    private static DataController dc;
    private Notification notification;
    private NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // why reassign?
        // entry.mActivityType = intent.getExtras().getInt("activity_type");
        Log.d("RUNS", "Tracking service bound");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // Connect the client.
        mGoogleApiClient.connect();
        dc = DataController.getInstance(getApplicationContext());

        // set up notification in bar
        String notificationTitle = "MyRuns";
        String notificationText = "Recording your path now";
        Intent i = new Intent(getApplicationContext(), MapDisplayActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                i, 0);

        notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(R.drawable.dartlogo)
                .setContentIntent(pi).build();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        nm.notify(0, notification);

        // discern between GPS and Automatic input types
        switch (intent.getIntExtra(StartFragment.INPUT_KEY, 1)) {
            case 1:
                Log.d(TAG, "starting GPS tracking");
                entry = new ExerciseEntry("GPS");
                entry.mInputType = Common.INPUT_TYPE_GPS;
                entry.mActivityType = intent.getExtras().getInt(StartFragment.ACTIVITY_KEY);
                break;

            case 2:
                Log.d(TAG, "starting automatic tracking");
                entry = new ExerciseEntry("Automatic");
                entry.mInputType = Common.INPUT_TYPE_AUTOMATIC;
                entry.mActivityType = intent.getExtras().getInt(StartFragment.ACTIVITY_KEY);   // will be overwrote

                mSensorDataBuffer = new ArrayBlockingQueue<Double>(ACCELEROMETER_QUEUE_LENGTH);

                // set up accelerometer and register callback
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                mAccelerometer = mSensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   // not TYPE_LINEAR_ACCELERATION?
                mSensorManager.registerListener(this, mAccelerometer,
                        SensorManager.SENSOR_DELAY_FASTEST);

                mAsyncTask = new SensorClassifierWorker();
                voteList = new int[4];
                break;

            default:
                Log.d(TAG,"Error getting input key value");
                break;
        }

        Log.d("RUNS", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"Connected to client");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        // start sensor data processor
        if (mAsyncTask != null) {
            mAsyncTask.execute();
        }
    }

    public ExerciseEntry getEntry() {
        return entry;
    }

    public class TrackingBinder extends Binder {
        public TrackingService getService() {
            return TrackingService.this;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // send information to the map activity
        // Tell the destination of the broadcast that there is an incoming broadcast
        updateEntry(location);
        if (!onLocationChangedCalled) {
            onLocationChangedCalled = true;
            Intent intent = new Intent();
            intent.setAction("edu.cs65.LOCATION_CHANGED");
            this.sendBroadcast(intent);
        } else if (startTracking) {
            Intent intent = new Intent();
            intent.setAction("edu.cs65.LOCATION_CHANGED");
            this.sendBroadcast(intent);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        //Log.d("RUNS", "User Removed Task");
        nm.cancelAll();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        // stop sensor updates
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this,mAccelerometer);
            stopProcessingTask();
        }

        stopSelf();
    }


    @Override
    public void onDestroy() {
        nm.cancelAll();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        // stop sensor updates
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this,mAccelerometer);
            stopProcessingTask();
        }

        Log.d("RUNS", "service destroyed");
        super.onDestroy();
    }

    /* -------------------------------------- GPS TASKS -------------------------------------- */

    protected void updateEntry(Location location) {
        double del = 0.00001;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (entry.mLocationList == null) {
            Log.d(TAG, "Entry has null location list");     // do nothing
        } else if (entry.mLocationList.size() == 0) {
            Log.d(TAG, "Adding first location");
        } else {
            // update duration
            Duration duration = new Duration(entry.mDateTime, new DateTime());
            // Round to the nearest second
            entry.mDuration = (int) duration.getStandardSeconds();

            // update distance
            LatLng last = entry.mLocationList.get(entry.mLocationList.size() - 1);
            Location lastLoc = new Location("");
            lastLoc.setLatitude(last.latitude);
            lastLoc.setLongitude(last.longitude);
            double distInMeters = (double) entry.lastLoc.distanceTo(location);
            entry.mDistance += dc.metersToMiles(distInMeters);

            // average speed in miles per hour
            double durationInHours = ((double) entry.mDuration) / (60 * 60);
            entry.mAvgSpeed = (del + entry.mDistance) / (del + durationInHours);

            // current speed in miles per hour
            DateTime c = new DateTime();
            Duration d = new Duration(entry.lastUpdated, c);
            long td = d.getStandardHours();
            long hh = d.getStandardSeconds();
            long mm = d.getMillis();
            // timeDelta in hours
            double timeDelta = (double) dc.msToHours(d.getMillis());
            double distInMiles = dc.metersToMiles(distInMeters);
            entry.mCurrentSpeed = (del + distInMiles) / (del + timeDelta);

            // climb
            boolean altitudeWorking = !(location.getAltitude()==0 || entry.lastLoc.getAltitude()==0);
            // If altitude is reporting correctly and new location is higher than previous
            if (location.getAltitude() > entry.lastLoc.getAltitude() && altitudeWorking) {
                entry.mClimb += (location.getAltitude() - entry.lastLoc.getAltitude()) * 0.000621371;
            }

            // calories
            entry.mCalorie = dc.caloriesFromMiles(entry.mDistance);
        }
        // set last modified date for computing current speed
        entry.lastUpdated = new DateTime();
        entry.lastLoc = location;
        entry.mLocationList.add(latLng);
    }

    /**
     * Called by MapDisplayActivity once initial zoom is complete. Prevents UI updates from new
     * locations until initial zoom is finished.
     */
    public void startTrackingPosition() {
        startTracking = true;
    }

    public void stopProcessingTask() {
        if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
            mAsyncTask.cancel(true);
        }
    }

    /* -------------------------------------- SENSOR TASKS -------------------------------------- */

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // get magnitude value
            double magnitude = Math.sqrt(event.values[0] * event.values[0] +
                    event.values[1] * event.values[1] +
                    event.values[2] * event.values[2]);


            // add to queue of data for async task to process
            try {
                mSensorDataBuffer.add(magnitude);
            } catch (IllegalStateException e) {
                // double buffer size because current buffer is full
                ArrayBlockingQueue<Double> newBuffer = new ArrayBlockingQueue<Double>(
                        mSensorDataBuffer.size() * 2);

                mSensorDataBuffer.drainTo(newBuffer);
                mSensorDataBuffer = newBuffer;
                mSensorDataBuffer.add(magnitude);
            }
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /* ---------------------------------- ASYNC PROCESSING ---------------------------------- */

    private class SensorClassifierWorker extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "Asyc Processor";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void ... arg0) {

            Double[] toClassify = new Double[ACCELEROMETER_BLOCK_CAPACITY + 1];

            int blocksize = 0;
            FFT fft = new FFT(ACCELEROMETER_BLOCK_CAPACITY);
            double[] accelData = new double[ACCELEROMETER_BLOCK_CAPACITY];
            double[] re = accelData; // real component
            double[] im = new double[ACCELEROMETER_BLOCK_CAPACITY];     // imaginary component -- zero out

            double maxVal = Double.MIN_VALUE;

            // process data
            while (true) {

                try {

                    if (isCancelled() == true) {
                        Log.d(TAG, "ending data processing worker");
                        return null;
                    }

                    // get data until block is full
                    accelData[blocksize++] = mSensorDataBuffer.take().doubleValue();

                    // got enough samples in block get calculate a classifier
                    if (blocksize == ACCELEROMETER_BLOCK_CAPACITY) {
                        blocksize = 0;

                        maxVal = .0;
                        for (double val : accelData) {
                            if (maxVal < val) {
                                maxVal = val;
                            }
                        }

                        // FFT on real and imaginary components
                        fft.fft(re, im);

                        // fill out FFT magnitudes
                        for (int j = 0; j < re.length; j++) {
                            double mag = Math.sqrt(re[j] * re[j] + im[j] * im[j]);
                            toClassify[j] = mag;
                            im[j] = .0; // Clear the field for next iteration
                        }

                        // add max value of acceleration
                        toClassify[ACCELEROMETER_BLOCK_CAPACITY] = maxVal;
                        int label = (int) WekaClassifier4.classify(toClassify);
                        Log.d(TAG, "labeling -> " + label);

                        // add vote and assign current label
                        voteList[label] += 1;

                        // get largest vote and assign to activity
                        int activityVotes = 0, curActivityInd = 0;
                        for (int i = 0; i < 4; i++) {
                            if (voteList[i] > activityVotes) {
                                activityVotes = voteList[i];
                                curActivityInd = i;
                            }
                        }

                        /**
                         * these are mixed up from our ActivityType string indexing as defined
                         * in strings.xml. We map as follows:
                         * label (0) Standing --> (2)
                         * label (1) Walking --> (1)
                         * label (2) Running --> (0)
                         * label (3) Other --> (13)
                         */
                        switch(curActivityInd) {
                            case 0:
                                entry.mActivityType = 2;
                                break;
                            case 1:
                                entry.mActivityType = 1;
                                break;
                            case 2:
                                entry.mActivityType = 0;
                                break;
                            case 3:
                                entry.mActivityType = 13;
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Log.d(TAG, "accelerometer processing task cancelled");
        }


    }


    /*
	                __  __    _                __         __              __
	   ____  ____  / /_/ /_  (_)___  ____ _   / /_  ___  / /___ _      __/ /
	  / __ \/ __ \/ __/ __ \/ / __ \/ __ `/  / __ \/ _ \/ / __ \ | /| / / /
	 / / / / /_/ / /_/ / / / / / / / /_/ /  / /_/ /  __/ / /_/ / |/ |/ /_/
	/_/ /_/\____/\__/_/ /_/_/_/ /_/\__, /  /_.___/\___/_/\____/|__/|__(_)
	                              /____/
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onConnectionSuspended(int i) {
    }
}
