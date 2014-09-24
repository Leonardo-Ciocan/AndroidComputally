package com.leonardociocan.computally;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;


public class SheetCard extends Card {

    CardArrayAdapter adapter;
    Sheet s;
    public SheetCard(Context context , final Sheet s , final CardArrayAdapter adapter) {
        super(context);
        this.s = s;
        this.adapter = adapter;
        this.setOnClickListener( new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                        Core.selectedSheet = s;
                        Intent toMainPage = new Intent();
                        toMainPage.setClass(getContext() , MainActivity.class);
                        (getContext()).startActivity(toMainPage);
            }
        });

        Card card = this;

        card.setType(0);
        card.setInnerLayout(R.layout.sheet_row);
        CardHeader header = new CardHeader(context);
        header.setTitle(s.Name);

        header.setPopupMenu(R.menu.sheet_menu , new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                Core.dataSource.deleteSheet(s.id);
                Core.sheets = Core.dataSource.GetSheets();
                adapter.notifyDataSetChanged();
            }
        });

        card.addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        //getCardView().setPadding(0,0,0,0);
        LinearLayout holder = (LinearLayout)getCardView().findViewById(R.id.preview_list);
        holder.removeAllViews();
        int count = (s.Lines.size() > 3 ) ? 3 :s.Lines.size();
        for(int x =0;x<count;x++){
            FormulaLine line = new FormulaLine(getContext() , s , x , false);
            holder.addView(line);
        }
    }
}
