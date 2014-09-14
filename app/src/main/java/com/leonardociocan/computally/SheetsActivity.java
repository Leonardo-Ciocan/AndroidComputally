package com.leonardociocan.computally;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.support.v4.app.Fragment;
import android.widget.ListView;

/**
 * Created by leo on 13/09/14.
 */
public class SheetsActivity extends FragmentActivity {
    SheetsFragment fragment = new SheetsFragment();
    SheetsAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheets);
        getSupportFragmentManager().beginTransaction().replace(R.id.root , fragment).commit();
        getActionBar().setTitle("My sheets");
        Core.listener.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_sub:
                //Substitution s = new Substitution("","");
                //adapter.add(s);


                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("New sheet name");

                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Sheet s = new Sheet(value);
                        Core.sheets.add(s);
                        fragment.refresh();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sheets_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment.refresh();
    }
}
