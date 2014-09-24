package com.leonardociocan.computally;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends Activity {


    LineAdapter adapter;
    DrawerLayout mDrawerLayout;
    ListView mLineHolder;
    //LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mLineHolder = (ListView)findViewById(R.id.line_holder);
        adapter = new LineAdapter(this , Core.selectedSheet.Lines);
        mLineHolder.setAdapter(adapter);
        //root = (LinearLayout)findViewById(R.id.root);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));//getResources().getColor(R.color.gray)));
        getActionBar().setTitle(Core.selectedSheet.Name);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        /*Core.selectedSheet.Lines.clear();
        List<Expression> ls = Core.dataSource.GetLines(Core.selectedSheet.id);
        for(Expression e : ls){
            Core.selectedSheet.Lines.add(e);
        }*/

        adapter.notifyDataSetChanged();


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

                NavUtils.navigateUpFromSameTask(MainActivity.this);
                return(true);
        }
        if(id == R.id.action_new_line){
            //Core.selectedSheet.Lines.add(new );
            Core.dataSource.addLine(Core.selectedSheet.id, "");
            Core.selectedSheet.Lines.clear();
            List<Expression> ls = Core.dataSource.GetLines(Core.selectedSheet.id);
            for(Expression e : ls){
                Core.selectedSheet.Lines.add(e);
            }
            adapter.notifyDataSetChanged();
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



}
