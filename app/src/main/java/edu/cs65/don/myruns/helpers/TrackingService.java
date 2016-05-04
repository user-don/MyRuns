package edu.cs65.don.myruns.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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

import edu.cs65.don.myruns.MapDisplayActivity;
import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * TrackingService.java
 * Created by don on 5/2/16.
 */
public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected ExerciseEntry entry;
    private final IBinder mBinder = new TrackingBinder();
    private boolean startTracking = false;
    private boolean onLocationChangedCalled = false;
    private static DataController mDataController;
    private Notification notification;
    private NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        entry.mActivityType = intent.getExtras().getInt("activity_type");
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
        mDataController = DataController.getInstance(getApplicationContext());
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

        entry = new ExerciseEntry("GPS");
        Log.d("RUNS", "onStartCommand");
        entry.mInputType = Common.INPUT_TYPE_GPS;
        entry.mActivityType = intent.getExtras().getInt("activity_type");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void updateEntry(Location location) {
        double del = 0.00001;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (entry.mLocationList.isEmpty()) {
            // do nothing
        } else {
            // update duration
            Duration duration = new Duration(entry.mDateTime, new DateTime());
            // Round to the nearest second
            float durationInS =
            entry.mDuration = (int) duration.getStandardSeconds();

            // update distance
            LatLng last = entry.mLocationList.get(entry.mLocationList.size() - 1);
            Location lastLoc = new Location("");
            lastLoc.setLatitude(last.latitude);
            lastLoc.setLongitude(last.longitude);
            double distInMeters = (double) entry.lastLoc.distanceTo(location);
            entry.mDistance += distInMeters * 0.000621371;

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
            double timeDelta = (double) d.getMillis() / 3600000;
            double distInMiles = distInMeters * 0.000621371;
            entry.mCurrentSpeed = (del + distInMiles) / (del + timeDelta);

            // climb
            boolean altitudeWorking = location.getAltitude() == 0 ||
                    entry.lastLoc.getAltitude() == 0;
            if (location.getAltitude() > entry.lastLoc.getAltitude() && altitudeWorking) {
                entry.mClimb += (location.getAltitude() - entry.lastLoc.getAltitude()) * 0.000621371;
            }
            Log.d("RUNS", "mClimb: " + String.valueOf(entry.mClimb));
            Log.d("RUNS", "Current altitude: " + String.valueOf(location.getAltitude()));
            Log.d("RUNS", "Previous altitude: " + String.valueOf(entry.lastLoc.getAltitude()));

            // calories
            entry.mCalorie = (int) (entry.mDistance / 15.0);
        }
        // set last modified date for computing current speed
        entry.lastUpdated = new DateTime();
        entry.lastLoc = location;
        entry.mLocationList.add(latLng);
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
    public void onDestroy() {
        nm.cancelAll();
        Log.d("RUNS", "service destroyed");
        super.onDestroy();
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

    /**
     * Called by MapDisplayActivity once initial zoom is complete. Prevents UI updates from new
     * locations until initial zoom is finished.
     */
    public void startTrackingPosition() {
        startTracking = true;
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
