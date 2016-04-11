package edu.cs65.don.myruns.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.cs65.don.myruns.R;
import edu.cs65.don.myruns.activities.AccountPreferencesActivity;
import edu.cs65.don.myruns.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final int ACCOUNT_PREFERENCES = 0;

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


    }

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
        }
        return null;
    }

}
