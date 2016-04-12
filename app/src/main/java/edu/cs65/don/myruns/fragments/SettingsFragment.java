package edu.cs65.don.myruns.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.activities.AccountPreferencesActivity;


/**
 * Subclass of {@link PreferenceFragment} for handling all app settings
 */
public class SettingsFragment extends PreferenceFragment {
    // Reference tags for click handlers
    private static final int ACCOUNT_PREFERENCES = 0;
    private static final int WEBPAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load preferences from XML
        addPreferencesFromResource(R.xml.preferences);

        // accessing preferences
        PreferenceScreen ps = (PreferenceScreen) getPreferenceScreen();
        // add listener for account_preferences
        Preference account_preferences = (Preference) ps.findPreference("account_preferences");
        Preference.OnPreferenceClickListener account_preferences_listener =
                getOnPreferenceClickListener(ACCOUNT_PREFERENCES);
        account_preferences.setOnPreferenceClickListener(account_preferences_listener);
        // add listener for loading webpage
        Preference webpage = (Preference) ps.findPreference("webpage");
        Preference.OnPreferenceClickListener web_listener = getOnPreferenceClickListener(WEBPAGE);
        webpage.setOnPreferenceClickListener(web_listener);

    }

    /**
     * Construct {@link android.preference.Preference.OnPreferenceClickListener} based on the
     * specified preference_id
     * @param preference_id determines the functionality of the listener
     * @return listener that can be set on a preference using setOnPreferenceClickListener
     */
    private Preference.OnPreferenceClickListener getOnPreferenceClickListener(int preference_id) {
        if (preference_id == ACCOUNT_PREFERENCES) {
            return new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    // open intent
                    Intent intent = new Intent(getActivity(), AccountPreferencesActivity.class);
                    startActivity(intent);
                    return true;
                }
            };
        } else if (preference_id == WEBPAGE) {
            return new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    // open webpage
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.cs_dartmouth_url)));
                    startActivity(intent);
                    return true;
                }
            };
        }
        return null;
    }

}
