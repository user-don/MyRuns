package edu.cs65.don.myruns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class MapDisplayActivity extends AppCompatActivity implements OnMapReadyCallback {

    @SuppressWarnings("FieldCanBeLocal")
    private GoogleMap mMap;
    private ExerciseEntry entry;
    private static DataController mDataController;
    private String unit_preference;
    private String[] unit_prefs;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataController = DataController.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmode);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        entry = new ExerciseEntry("GPS");
        entry.mInputType = mDataController.INPUT_TYPE_MANUAL;
        entry.mActivityType = getIntent().getExtras().getInt("activity_type");
        getPreferences();
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
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mMap.setMyLocationEnabled(true);

        // code for getting location
//        Location location = mMap.getMyLocation();
//        LatLng myLocation;
//        if (location != null) {
//            myLocation = new LatLng(location.getLatitude(),
//                    location.getLongitude());
//            myLatLng = myLocation;
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(myLocation)      // Sets the center of the map
//                    .zoom(16)                   // Sets the zoom
//                    .bearing(0)                // Sets the orientation of the camera to north
//                    .tilt(0)                   // Sets the tilt of the camera to 0 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public class EntityUpDateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // update everything
            // draw path on the map
            if (entry.mLocationList.isEmpty()) {
                //entry.mLocationList.add(latLng);
                //entry.lastUpdated = new DateTime();
            } else {
                //updateLocation(latLng);
            }
        }
    }

    private void updateLocation(LatLng latLng) {
        // update duration
        Duration duration = new Duration(entry.mDateTime, new DateTime());
        // Round to the nearest second
        entry.mDuration = (int) (duration.getStandardSeconds() + 0.5);

        // update distance
        LatLng last = entry.mLocationList.get(entry.mLocationList.size()-1);
        Location lastLoc = new Location("");
        lastLoc.setLatitude(last.latitude);
        lastLoc.setLongitude(last.longitude);
        Location currLoc = new Location("");
        currLoc.setLatitude(latLng.latitude);
        currLoc.setLongitude(latLng.longitude);
        double distInMeters = (double) lastLoc.distanceTo(currLoc);
        entry.mDistance += distInMeters * 0.000621371;
        String distanceStr = getDistanceString(entry.mDistance);

        // average speed in miles per hour
        double durationInHours = entry.mDuration / (60*60);
        entry.mAvgSpeed = entry.mDistance / durationInHours;
        String avgSpeedStr = getAverageSpeedString();

        // current speed in miles per hour
        Duration d = new Duration(entry.lastUpdated, new DateTime());
        double timeDelta = (double) d.getStandardHours();
        double distInMiles = distInMeters * 0.000621371;
        double currSpeed = distInMiles / timeDelta;
        String currSpeedString = getCurrentSpeedString(currSpeed);

        // climb
        if (currLoc.getAltitude() > lastLoc.getAltitude()) {
            entry.mClimb += currLoc.getAltitude() - lastLoc.getAltitude();
        }
        String climbString = getClimbString(entry.mClimb);

        // calories
        entry.mCalorie = (int) (entry.mDistance/ 15.0);
        String calorieString = getCalorieString(entry.mCalorie);

        // set last modified date for computing current speed
        entry.lastUpdated = new DateTime();
        // update the location
        entry.mLocationList.add(latLng);
        // draw path on map

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(18)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to north
                .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));


    }



    private void getPreferences() {
        unit_preference = mDataController.getUnitPreferences();
        unit_prefs = this.getResources().getStringArray(R.array.entryvalues_unit_preference);
    }

    private String getCalorieString(int calories) {
        return "Calorie: " + String.valueOf(calories);
    }

    private String getClimbString(double climb) {
        if (unit_preference.equals(unit_prefs[0])) {
            String val = String.valueOf(mDataController.round(mDataController.milesToKm(climb), 2));
            return "Climb: " + val + " Kilometers";
        } else {
            return "Climb: " + String.valueOf(mDataController.round(climb, 2)) + " Miles";
        }
    }

    private String getCurrentSpeedString(double speed) {
        if (unit_preference.equals(unit_prefs[0]))
            return "Cur speed: " + String.valueOf(mDataController.mphToKph(speed)) + " km/h";
        else
            return "Cur speed: " + String.valueOf(speed) + " m/h";
    }

    private String getAverageSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Avg speed: " + String.valueOf(mDataController.mphToKph(entry.mAvgSpeed)) + " km/h";
        else
            return "Avg speed: " + String.valueOf(entry.mAvgSpeed) + " m/h";
    }

    private String getDistanceString(double distance) {
        if (unit_preference.equals(unit_prefs[0]))
            return "Distance: " + String.valueOf(mDataController.milesToKm(distance)) + " Kilometers";
        else
            return "Distance: " + String.valueOf(distance) + " Miles";
    }

    private double calculateAverageSpeed() {
        // get total distance between markers
        return 0;
    }
}
