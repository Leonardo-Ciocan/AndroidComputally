package com.leonardociocan.computally;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

public class SheetsFragment extends Fragment {
    CardListView listView;
    CardArrayAdapter mCardArrayAdapter;
    //SheetsAdapter adapter;
    ArrayList<Card> cards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheets_fragment,
                container, false);
        //adapter = new SheetsAdapter(getActivity() , R.layout.sheet_row , Core.sheets);
        //ListView ls = (ListView)view.findViewById(R.id.sheets_holder);
        //ls.setAdapter(adapter);
        listView = (CardListView)view.findViewById(R.id.myList);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        listView.setPadding(0,(int)px , 0,0);
         cards = new ArrayList<Card>();
       mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
        mCardArrayAdapter.setInnerViewTypeCount(1);
        listView.setAdapter(mCardArrayAdapter);
        listView.setChoiceMode(CardListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Core.selectedSheet = Core.sheets.get(i);
                        Intent toMainPage = new Intent();
                        toMainPage.setClass(getActivity() , MainActivity.class);
                        (getActivity()).startActivity(toMainPage);
            }
        });


        Core.sheets = Core.dataSource.GetSheets();
        update();

        return view;
    }


    public void update(){
        cards.clear();
        for(Sheet s : Core.sheets){
            SheetCard card = new SheetCard(getActivity(),s , mCardArrayAdapter);
            cards.add(card);
            mCardArrayAdapter.notifyDataSetChanged();
        }
    }

}
