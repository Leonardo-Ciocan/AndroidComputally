package com.leonardociocan.computally;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        Core.dataSource = new CoreDataSource(this);
        Core.dataSource.open();



        setContentView(R.layout.activity_sheets);
        getSupportFragmentManager().beginTransaction().replace(R.id.root , fragment).commit();
        getActionBar().setTitle("My sheets");
        Core.listener.clear();
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));//getResources().getColor(R.color.gray)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_sub:
                //Substitution s = new Substitution("","");
                //adapter.add(s);


                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                //alert.setTitle("New sheet name");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Enter sheet name");
                alert.setView(input);

                input.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getAction() != KeyEvent.ACTION_DOWN) return true;
                        if(keyEvent.getKeyCode() == 13) {
                            String value = input.getText().toString();
                            Sheet s = new Sheet(value);
                            Core.sheets.add(s);
                            Core.dataSource.addSheet(value);
                            Core.sheets = Core.dataSource.GetSheets();
                            fragment.update();
                            return true;
                        }
                        return false;
                    }
                });

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Sheet s = new Sheet(value);
                        Core.sheets.add(s);
                        Core.dataSource.addSheet(value);
                        Core.sheets = Core.dataSource.GetSheets();
                        //fragment.refresh();
                        fragment.update();
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
        fragment.update();
    }
}
