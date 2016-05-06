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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import edu.cs65.don.myruns.fragments.StartFragment;
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
    private Intent trackingIntent;

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

        trackingIntent = new Intent(this, TrackingService.class);
        trackingIntent.putExtra(StartFragment.INPUT_KEY,
                getIntent().getExtras().getInt(StartFragment.INPUT_KEY));
        trackingIntent.putExtra(StartFragment.ACTIVITY_KEY,
                getIntent().getExtras().getInt(StartFragment.ACTIVITY_KEY));

        // only start service if it doesn't already exist
        if (savedInstanceState == null) {
            startService(trackingIntent);
        }
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
        try {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void updateMap() {
        LatLng currLoc = entry.getMostRecentLatLng();
        if (currLoc == null) {
            // do nothing
        }else {
            if (startMarker == null && !entry.mLocationList.isEmpty()) {
                // recovering from orientation change, re-draw everything
                plo = new PolylineOptions().addAll(entry.mLocationList);
                plo.color(Color.BLACK);
                polyline = mMap.addPolyline(plo);
                LatLng startPosition = entry.mLocationList.get(0);
                MarkerOptions startMarkerOptions = new MarkerOptions().position(startPosition)
                        .title("Start").icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_GREEN));
                LatLng currentPosition = entry.mLocationList.get(entry.mLocationList.size() - 1);
                MarkerOptions currentMarkerOptions = new MarkerOptions().position(currentPosition)
                        .title("Finish").icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_RED));
                startMarker = mMap.addMarker(startMarkerOptions);
                currentMarker = mMap.addMarker(currentMarkerOptions);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(startPosition)
                        .zoom(18)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to north
                        .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
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
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(currLoc)
                        .zoom(18)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to north
                        .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                // other entry, remove last pin and place new one
                currentMarker.setVisible(true);
                currentMarker.setPosition(currLoc);
                plo = new PolylineOptions().addAll(entry.mLocationList);
                //plo.add(currentMarker.getPosition());

                // if current map view doesn't include end marker, recenter on current position
                if (!mMap.getProjection().getVisibleRegion().latLngBounds.contains(currentMarker.getPosition())) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.getPosition(),18));
                }
            }
            plo.color(Color.BLACK);
            polyline = mMap.addPolyline(plo);
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
        Intent i = new Intent(this, TrackingService.class);
        stopService(i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        IntentFilter filter = new IntentFilter();
        filter.addAction("edu.cs65.LOCATION_CHANGED");
        checkRegisterReceiver = registerReceiver(entityUpdateReceiver, filter);

        bindService(trackingIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Handle save and cancel button presses
     * @param v the view corresponding to the pressed button
     */
    public void onSaveCancelClicked (View v) {
        if (v.getId() == R.id.gps_entry_save_button) {

            if (entry == null) {
                Toast.makeText(getApplicationContext(), "Failed to save entry", Toast.LENGTH_LONG).show();
                // stop service
                Intent i = new Intent(this, TrackingService.class);
                stopService(i);
                finish();
            } else {
                // save entry to db
                dc.saveToDbAsync(entry);
                // stop service
                Intent i = new Intent(this, TrackingService.class);
                stopService(i);
                finish();
            }
        } else if (v.getId() == R.id.gps_entry_cancel_button) {
            // stop service
            Intent i = new Intent(this, TrackingService.class);
            stopService(i);
            Toast.makeText(getApplicationContext(), "Entry discarded.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
