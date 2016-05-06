package edu.cs65.don.myruns.helpers;

import android.app.Application;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.junit.matchers.JUnitMatchers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;

import com.google.android.gms.maps.model.LatLng;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboSharedPreferences;

import edu.cs65.don.myruns.BuildConfig;
import edu.cs65.don.myruns.models.ExerciseEntry;

import static org.junit.Assert.*;

/**
 * Created by don on 5/4/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class TrackingServiceUnitTest {

    @Test
    public void testUpdateEntry() throws Exception {
        Application application = RuntimeEnvironment.application;
        JodaTimeAndroid.init(application.getApplicationContext());
        ExerciseEntry entry = new ExerciseEntry();
        Location loc = new Location("");
        loc.setLatitude(-72.2949);
        loc.setLongitude(43.7048);
        LatLng old = new LatLng(-72.2717,42.7001);
        entry.mLocationList.add(old);
        entry.mDistance = 1;
        entry.lastUpdated = new DateTime().minusHours(2);
        TrackingService service = new TrackingService();
        service.entry = entry;
        service.updateEntry(loc);


        assertThat("calories", entry.mCalorie, is(not(0)));
        assertThat("distance", entry.mDistance != 0);
        //Log.d("RUNS", String.valueOf(entry.mCalorie));
    }
}