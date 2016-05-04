package edu.cs65.don.myruns;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import edu.cs65.don.myruns.helpers.SingleEntryLoader;
import edu.cs65.don.myruns.models.ExerciseEntry;

public class MapEntryDisplayActivity extends AppCompatActivity implements OnMapReadyCallback,
        LoaderManager.LoaderCallbacks<ExerciseEntry> {

    private GoogleMap mMap;
    ExerciseEntry e;
    private static DataController dc;
    private boolean mapReady = false;
    private String unit_preference;
    private String[] unit_prefs;
    private Marker startMarker;
    private Marker finishMarker;
    private PolylineOptions plo;
    private Polyline polyline;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_entry_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dc = DataController.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_entry_display);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        unit_preference = dc.getUnitPreferences();
        unit_prefs = this.getResources().getStringArray(R.array.entryvalues_unit_preference);
        // Take row ID from one bundle and pass to the next for asynchronous load!
        Bundle extras = getIntent().getExtras();
        long id = extras.getLong("id");
        Bundle args = new Bundle();
        args.putLong("id", id);
        getLoaderManager().initLoader(0, args, this);
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
        mapReady = true;
    }

    @Override
    public Loader<ExerciseEntry> onCreateLoader(int id, Bundle args) {
        // get exercise e ID from bundle and use to call our loader that will
        // pull this e from the database
        long dbId = args.getLong("id");
        return new SingleEntryLoader(this, dbId);
    }

    @Override
    public void onLoadFinished(Loader<ExerciseEntry> loader, ExerciseEntry data) {
        // once load is finished, initialize the interface.
        this.e = data;
        while (!mapReady) {
            // hold until map is online
        }
        // TODO: Check if no locations
        if (e.mLocationList.size() == 0) {
            updateStats();
        } else {
            updateStats();
            drawRoute();
        }
    }

    public void deleteEntry(MenuItem v) {
        Runnable delete = new Runnable() {
            @Override
            public void run() {
                // This isn't running on the UI thread, so we can't directly modify
                // UI objects such as View objects.
                // http://developer.android.com/training/multiple-threads/define-runnable.html
                dc.dbHelper.removeEntry(e.id);
            }
        };
        delete.run();
        // close out DisplayEntryActivity when deleting the exercise e
        finish();
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

        TextView tv = (TextView) findViewById(R.id.gps_entry_activity_stats);
        tv.setText(textToDisplay);

    }

    private void drawRoute() {
        plo = new PolylineOptions().addAll(e.mLocationList);
        plo.color(Color.BLACK);
        polyline = mMap.addPolyline(plo);
        LatLng startPosition = e.mLocationList.get(0);
        MarkerOptions startMarkerOptions = new MarkerOptions().position(startPosition)
                .title("Start").icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN));
        LatLng finalPosition = e.mLocationList.get(e.mLocationList.size() - 1);
        MarkerOptions finishMarkerOptions = new MarkerOptions().position(finalPosition)
                .title("Finish").icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED));
        startMarker = mMap.addMarker(startMarkerOptions);
        finishMarker = mMap.addMarker(finishMarkerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(finalPosition)
                .zoom(18)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to north
                .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private String getTypeString() {
        String type = getResources().getStringArray(R.array.activity_type)[e.mActivityType];
        return "Type: " + type;
    }

    private String getAverageSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Avg speed: " + String.valueOf(dc.round(dc.mphToKph(e.mAvgSpeed),2)) + " km/h";
        else
            return "Avg speed: " + String.valueOf(dc.round(e.mAvgSpeed,2)) + " m/h";
    }

    private String getCurrentSpeedString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Cur speed: " + String.valueOf(dc.round(dc.mphToKph(e.mCurrentSpeed),2)) + " km/h";
        else
            return "Cur speed: " + String.valueOf(dc.round(e.mCurrentSpeed,2)) + " m/h";
    }

    private String getClimbString() {
        if (unit_preference.equals(unit_prefs[0])) {
            String val = String.valueOf(dc.round(dc.milesToKm(e.mClimb), 2));
            return "Climb: " + val + " Kilometers";
        } else {
            return "Climb: " + String.valueOf(dc.round(e.mClimb, 2)) + " Miles";
        }
    }

    private String getCalorieString() {
        return "Calorie: " + String.valueOf(e.mCalorie);
    }

    private String getDistanceString() {
        if (unit_preference.equals(unit_prefs[0]))
            return "Distance: " + String.valueOf(dc.round(dc.milesToKm(e.mDistance),2)) + " Kilometers";
        else
            return "Distance: " + String.valueOf(dc.round(e.mDistance,2)) + " Miles";
    }


    @Override
    public void onLoaderReset(Loader<ExerciseEntry> loader) {

    }
}
