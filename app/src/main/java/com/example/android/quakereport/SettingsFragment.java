package com.example.android.quakereport;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        bindSummaryToPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        if(key.equals(getString(R.string.settings_min_magnitude_key))){
            preference.setSummary(sharedPreferences.getString(key, ""));
        } else if(key.equals(getString(R.string.settings_order_by_key))){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(listPreference.getValue());
            listPreference.setSummary(listPreference.getEntries()[index].toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void bindSummaryToPreferences() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.settings_min_magnitude_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.settings_order_by_key));
    }
}
