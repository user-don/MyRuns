package edu.cs65.don.myruns;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.helpers.TrackingService;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class MapDisplayActivity extends FragmentActivity implements OnMapReadyCallback {

    @SuppressWarnings("FieldCanBeLocal")
    private GoogleMap mMap;
    private ExerciseEntry entry;
    private static DataController mDataController;
    private String unit_preference;
    private String[] unit_prefs;
    private LocationManager lm;
    TrackingService mService;
    boolean mBound = false;
    private EntityUpdateReceiver entityUpdateReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataController = DataController.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmode);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getPreferences();
        // register receiver
        entityUpdateReceiver = new EntityUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("edu.cs65.LOCATION_CHANGED");
        registerReceiver(entityUpdateReceiver, filter);

        Intent i = new Intent(this, TrackingService.class);
        i.putExtra("activity_type", getIntent().getExtras().getInt("activity_type"));
        startService(i);
        bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    public ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // initialize the service and bind to the tracking service
            Log.d(getClass().getName(), "onServiceConnected");
            TrackingService.TrackingBinder binder = (TrackingService.TrackingBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(getClass().getName(), "onServiceDisconnected");
            disconnect();
        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMyLocationEnabled(true);
    }


    public class EntityUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // update everything
            // draw path on the map
            entry = mService.getEntry();
            updateStats();
            updateMap();
        }
    }

    private void updateStats() {
        StringBuilder sb = new StringBuilder();
        String typeString = getTypeString();
        String avgSpeedStr = getAverageSpeedString();
        String currSpeedString = getCurrentSpeedString();
        String climbString = getClimbString();
        String calorieString = getCalorieString();
        String distanceString = getDistanceString();
        sb.append(typeString).append("\n").append(avgSpeedStr).append("\n")
                .append(currSpeedString).append("\n")
                .append(climbString).append("\n")
                .append(calorieString).append("\n")
                .append(distanceString).append("\n");
        String textToDisplay = sb.toString();

    }

    private void updateMap() {
        LatLng currLoc = entry.getMostRecentLatLng();
        // draw path on map
        mMap.addMarker(new MarkerOptions().position(currLoc).title("Test Marker"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currLoc)
                .zoom(18)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to north
                .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void getPreferences() {
        unit_preference = mDataController.getUnitPreferences();
        unit_prefs = this.getResources().getStringArray(R.array.entryvalues_unit_preference);
    }

    private String getTypeString() {
        String type = getResources().getStringArray(R.array.activity_type)[entry.mActivityType];
        return "Type: " + type;
    }

    private String getAverageSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Avg speed: " + String.valueOf(mDataController.mphToKph(entry.mAvgSpeed)) + " km/h";
        else
            return "Avg speed: " + String.valueOf(entry.mAvgSpeed) + " m/h";
    }

    private String getCurrentSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Cur speed: " + String.valueOf(mDataController.mphToKph(entry.mCurrentSpeed)) + " km/h";
        else
            return "Cur speed: " + String.valueOf(entry.mCurrentSpeed) + " m/h";
    }

    private String getClimbString() {
        if (unit_preference.equals(unit_prefs[0])) {
            String val = String.valueOf(mDataController.round(mDataController.milesToKm(entry.mClimb), 2));
            return "Climb: " + val + " Kilometers";
        } else {
            return "Climb: " + String.valueOf(mDataController.round(entry.mClimb, 2)) + " Miles";
        }
    }

    private String getCalorieString() {
        return "Calorie: " + String.valueOf(entry.mCalorie);
    }

    private String getDistanceString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Distance: " + String.valueOf(mDataController.milesToKm(entry.mDistance)) + " Kilometers";
        else
            return "Distance: " + String.valueOf(entry.mDistance) + " Miles";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doUnbindService();
    }

    private void doUnbindService() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    // TODO: unbind on save and cancel clicked

    @Override
    protected void onPause() {
        super.onPause();
        // if we use this activity for both displaying and recording,
        // we check if new map task, and if so then we unregister
        unregisterReceiver(entityUpdateReceiver);
        // unbind tracking service
        doUnbindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBound = getApplicationContext().bindService(new Intent(getApplicationContext(),
                TrackingService.class), mServiceConnection, Context.BIND_AUTO_CREATE );
    }

    /**
     * Disconnect from service
     */
    private void disconnect() {
        mBound = false;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        doUnbindService();
//    }
}
