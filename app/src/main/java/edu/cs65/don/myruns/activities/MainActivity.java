package edu.cs65.don.myruns.activities;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.adapters.ActionTabsViewPagerAdapter;
import edu.cs65.don.myruns.fragments.HistoryFragment;
import edu.cs65.don.myruns.fragments.SettingsFragment;
import edu.cs65.don.myruns.fragments.StartFragment;
import edu.cs65.don.myruns.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    // for permission requests
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_DATA = 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_DATA = 223;

    private SlidingTabLayout slidingTabLayout;
    // use view pager to show the list of fragments
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    // we supply an implementation of a PagerAdapter to generate the pages that the view shows.
    private ActionTabsViewPagerAdapter myViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for permissions
        checkForPermissions();

        // Define SlidingTabLayout (shown at top) and ViewPager (shown at bottom) in the layout.
        // Get their instances.
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new StartFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new SettingsFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter =new ActionTabsViewPagerAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myViewPageAdapter);

        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Check for app permissions. Not needed anymore since we have dropped targetSdkVersion
     * to 21, but keeping around in case we need to bring back up for any reason.
     */
    private void checkForPermissions() {
        int checkWriteExternalStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkReadExternalStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (checkWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_DATA);
        }
        if (checkReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_DATA);
        }
    }
}
