package edu.cs65.don.myruns.helpers;

import android.app.Service;
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
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * TrackingService.java
 * Created by don on 5/2/16.
 */
public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ExerciseEntry entry;
    private final IBinder mBinder = new TrackingBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        startTracking(intent);
        Log.d("RUNS", "Tracking service bound");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTracking(intent);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // Connect the client.
        mGoogleApiClient.connect();
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



    private void startTracking(Intent intent) {
        // if started do nothing
        // if not started then mGoogleApiClient.connect()
        // setUpNotification()
        // create new entry
        entry = new ExerciseEntry("GPS");
        entry.mInputType = Common.INPUT_TYPE_GPS;
        // is this the same intent that gets passed in MapDisplayActivity?
        entry.mActivityType = intent.getExtras().getInt("activity_type");
        // set activity type
    }

    private void updateEntry(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (entry.mLocationList.isEmpty()) {
            // do nothing
        } else {
            // update duration
            Duration duration = new Duration(entry.mDateTime, new DateTime());
            // Round to the nearest second
            entry.mDuration = (int) (duration.getStandardSeconds() + 0.5);

            // update distance
            LatLng last = entry.mLocationList.get(entry.mLocationList.size() - 1);
            Location lastLoc = new Location("");
            lastLoc.setLatitude(last.latitude);
            lastLoc.setLongitude(last.longitude);
            Location currLoc = new Location("");
            currLoc.setLatitude(latLng.latitude);
            currLoc.setLongitude(latLng.longitude);
            double distInMeters = (double) lastLoc.distanceTo(currLoc);
            entry.mDistance += distInMeters * 0.000621371;

            // average speed in miles per hour
            double durationInHours = entry.mDuration / (60 * 60);
            entry.mAvgSpeed = entry.mDistance / durationInHours;

            // current speed in miles per hour
            Duration d = new Duration(entry.lastUpdated, new DateTime());
            double timeDelta = (double) d.getStandardHours();
            double distInMiles = distInMeters * 0.000621371;
            entry.mCurrentSpeed = distInMiles / timeDelta;

            // climb
            if (currLoc.getAltitude() > lastLoc.getAltitude()) {
                entry.mClimb += currLoc.getAltitude() - lastLoc.getAltitude();
            }

            // calories
            entry.mCalorie = (int) (entry.mDistance / 15.0);
        }
        // set last modified date for computing current speed
        entry.lastUpdated = new DateTime();
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
    public void onLocationChanged(Location location) {
        // send information to the map activity
        // Tell the destination of the broadcast that there is an incoming broadcast
        updateEntry(location);
        Intent intent = new Intent();
        intent.setAction("edu.cs65.LOCATION_CHANGED");
        //Intent intent = new Intent(MapDisplayActivity.EntityUpdateReceiver.class.getName());
        this.sendBroadcast(intent);
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
