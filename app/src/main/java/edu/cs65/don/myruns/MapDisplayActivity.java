package edu.cs65.don.myruns;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.helpers.TrackingService;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class MapDisplayActivity extends FragmentActivity implements OnMapReadyCallback {

    @SuppressWarnings("FieldCanBeLocal")
    private GoogleMap mMap;
    private ExerciseEntry entry;
    private static DataController dc;
    private String unit_preference;
    private String[] unit_prefs;
    private LocationManager lm;
    TrackingService mService;
    boolean mBound = false;
    private boolean isReceiverRegistered = false;
    private EntityUpdateReceiver entityUpdateReceiver;
    private Marker currentMarker;
    private Marker startMarker;
    private Marker finishMarker;
    private PolylineOptions plo;
    private Polyline polyline;
    private Intent checkRegisterReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dc = DataController.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmode);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getPreferences();
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
            mBound = false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

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
            ((MapDisplayActivity) context).updateStats();
            ((MapDisplayActivity) context).updateMap();
        }
    }

    private void updateStats() {
        entry = mService.getEntry();
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

        TextView tv = (TextView) findViewById(R.id.activity_stats);
        tv.setText(textToDisplay);

    }

    private void updateMap() {
        LatLng currLoc = entry.getMostRecentLatLng();
        if (currLoc == null) {
            return;
        }else {
            // draw path on map
            if (startMarker == null || currentMarker == null) {
                // first entry, place special start pin
                MarkerOptions startMarkerOptions = new MarkerOptions().position(currLoc)
                        .title("Start").icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_GREEN));
                startMarker = mMap.addMarker(startMarkerOptions);
                currentMarker = mMap.addMarker(new MarkerOptions().position(currLoc)
                        .title("Current").visible(false));
                plo = new PolylineOptions().add(startMarker.getPosition());
            } else {
                // other entry, remove last pin and place new one
                currentMarker.setVisible(true);
                currentMarker.setPosition(currLoc);
                plo.add(currentMarker.getPosition());
            }
            plo.color(Color.BLACK);
            polyline = mMap.addPolyline(plo);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currLoc)
                    .zoom(18)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to north
                    .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // initial zoom complete, start location updating
            mService.startTrackingPosition();
        }
    }

    private void getPreferences() {
        unit_preference = dc.getUnitPreferences();
        unit_prefs = this.getResources().getStringArray(R.array.entryvalues_unit_preference);
    }

    private String getTypeString() {
        String type = getResources().getStringArray(R.array.activity_type)[entry.mActivityType];
        return "Type: " + type;
    }

    private String getAverageSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Avg speed: " + String.valueOf(dc.round(dc.mphToKph(entry.mAvgSpeed),2)) + " km/h";
        else
            return "Avg speed: " + String.valueOf(dc.round(entry.mAvgSpeed,2)) + " m/h";
    }

    private String getCurrentSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Cur speed: " + String.valueOf(dc.round(dc.mphToKph(entry.mCurrentSpeed),2)) + " km/h";
        else
            return "Cur speed: " + String.valueOf(dc.round(entry.mCurrentSpeed,2)) + " m/h";
    }

    private String getClimbString() {
        if (unit_preference.equals(unit_prefs[0])) {
            String val = String.valueOf(dc.round(dc.milesToKm(entry.mClimb), 2));
            return "Climb: " + val + " Kilometers";
        } else {
            return "Climb: " + String.valueOf(dc.round(entry.mClimb, 2)) + " Miles";
        }
    }

    private String getCalorieString() {
        return "Calorie: " + String.valueOf(entry.mCalorie);
    }

    private String getDistanceString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Distance: " + String.valueOf(dc.round(dc.milesToKm(entry.mDistance),2)) + " Kilometers";
        else
            return "Distance: " + String.valueOf(dc.round(entry.mDistance,2)) + " Miles";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //doUnbindService();
        //doUnregisterReceiver();
        Intent i = new Intent(this, TrackingService.class);
        stopService(i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // if we use this activity for both displaying and recording,
        // we check if new map task, and if so then we unregister
        //doUnregisterReceiver();
        // unbind tracking service
        //doUnbindService();
//        if (checkRegisterReceiver != null) {
//            unregisterReceiver(entityUpdateReceiver);
//            checkRegisterReceiver = null;
//        }
        unregisterReceiver(entityUpdateReceiver);
        unbindService(mServiceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register receiver
        if (entityUpdateReceiver == null) {
            entityUpdateReceiver = new EntityUpdateReceiver();
        }
//        if (checkRegisterReceiver == null) {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("edu.cs65.LOCATION_CHANGED");
//            checkRegisterReceiver = registerReceiver(entityUpdateReceiver, filter);
//            //isReceiverRegistered = true;
//        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("edu.cs65.LOCATION_CHANGED");
        checkRegisterReceiver = registerReceiver(entityUpdateReceiver, filter);

        Intent i = new Intent(this, TrackingService.class);
        i.putExtra("activity_type", getIntent().getExtras().getInt("activity_type"));
        startService(i);
        bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);

//        mBound = getApplicationContext().bindService(new Intent(getApplicationContext(),
//                TrackingService.class), mServiceConnection, Context.BIND_AUTO_CREATE );
        // also re-register receiver if not already registered
//        if (!isReceiverRegistered) {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("edu.cs65.LOCATION_CHANGED");
//            registerReceiver(entityUpdateReceiver, filter);
//            isReceiverRegistered = true;
    }

    private void doUnbindService() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private void doUnregisterReceiver() {
        if (isReceiverRegistered) {
            unregisterReceiver(entityUpdateReceiver);
            isReceiverRegistered = false;
        }
    }

    // TODO: unbind on save and cancel clicked

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        doUnbindService();
//    }
}
