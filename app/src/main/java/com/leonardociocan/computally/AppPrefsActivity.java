package com.leonardociocan.computally;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * Created by leo on 28/08/14.
 */
public class AppPrefsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new AppPrefs()).commit();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Settings");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(AppPrefsActivity.this);
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }
}
