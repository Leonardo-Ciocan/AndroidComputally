package com.leonardociocan.computally;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SubstitutionsActivity extends Activity {
    SubstitutionsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.substitutions);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));//getResources().getColor(R.color.gray)));
        getActionBar().setTitle("Substitutions");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new SubstitutionsAdapter(this , R.layout.substitution_row , Core.selectedSheet.Substitutions);

        ListView view = (ListView)findViewById(R.id.sub_list);
        view.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(SubstitutionsActivity.this);
                return(true);
            case R.id.action_add_sub:
                Substitution s = new Substitution("","");
                adapter.add(s);
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.substitution_menu, menu);
        return true;
    }
}
