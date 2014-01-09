package com.example.DispatcherMobile;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class UserSettingsActivity extends PreferenceActivity {
    private Intent currentIntent;
    private static final int RESULT_SETTINGS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentIntent = getIntent();
       addPreferencesFromResource(R.xml.settings);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_settings);
        getActionBar().setTitle("Settings");
    }
    @Override
    public void finish() {
        setResult(RESULT_SETTINGS, currentIntent);

        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(_item));
    }
}