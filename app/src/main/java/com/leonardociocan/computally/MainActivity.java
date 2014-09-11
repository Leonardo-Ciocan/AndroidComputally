package com.leonardociocan.computally;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    DrawerLayout mDrawerLayout;
    LinearLayout mLineHolder;
    //LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mLineHolder = (LinearLayout)findViewById(R.id.line_holder);
        //root = (LinearLayout)findViewById(R.id.root);
        setUpDrawerToggle();
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255,255,255)));//getResources().getColor(R.color.gray)));
        getActionBar().setTitle("New sheet");

        //String input = "30 dollars x 5 people";
        //String result = Core.Solver.Sanitize(input);
        //int x = 0;
    }

    @Override
    protected void onResume() {
        for(int x =0; x< mLineHolder.getChildCount();x++){
            FormulaLine line = (FormulaLine) mLineHolder.getChildAt(x);
            line.updateTextSize();
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(id == android.R.id.home){
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        if(id == R.id.action_new_line){
            FormulaLine text = new FormulaLine(this , Core.sheet , Core.sheet.Lines.size());
            Core.sheet.Lines.add("");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            text.setLayoutParams(params);
            mLineHolder.addView(text , params);
        }
        if(id == R.id.action_substitutions){
            Intent i = new Intent();
            i.setClass(this , SubstitutionsActivity.class);
            startActivity(i);
        }
        if(id == R.id.action_settings){
            Intent i = new Intent();
            i.setClass(this , AppPrefsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpDrawerToggle(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,                             /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        // NB: required for the drawer indicator to show up!
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
