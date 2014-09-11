package com.leonardociocan.computally;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

public class AppPrefs extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(),"default_value");


        Preference email = findPreference("email");
        email.setOnPreferenceClickListener( new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "leonardo.ciocan@outlook.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Karma for android feedback");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                    updatePreference(preferenceGroup.getPreference(j));
                }
            } else {
                updatePreference(preference);
            }
        }

    }

    void updatePreference(Preference p){
        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences() , p.getKey());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        Preference pref = findPreference(key);

        if (pref instanceof EditTextPreference) {
            EditTextPreference listPref = (EditTextPreference) pref;
            pref.setSummary(listPref.getText());
        }
    }



    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
