package com.leonardociocan.computally;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;


public class SheetsAdapter extends ArrayAdapter<Sheet> {

    public SheetsAdapter(Context context, int resource, List<Sheet> objects) {
        super(context,resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sheet_row, parent, false);
        }

        //TextView name = (TextView)convertView.findViewById(R.id.sheet_name);
        //name.setText(getItem(position).Name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Core.selectedSheet = getItem(position);
                Intent toMainPage = new Intent();
                toMainPage.setClass(getContext() , MainActivity.class);
                (getContext()).startActivity(toMainPage);
            }
        });

        //ListView listView = (ListView) convertView.findViewById(R.id.preview_list);
        //listView.setAdapter(new LineAdapter(getContext() , getItem(position).Lines));

        //Card card = new Card(getContext());
        //card.setv
        //CardView cardView = new CardView(getContext());
        //cardView.view



        return convertView;
    }
}
