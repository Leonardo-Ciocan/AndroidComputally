package com.leonardociocan.computally;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SheetsFragment extends Fragment {
    SheetsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheets_fragment,
                container, false);
        adapter = new SheetsAdapter(getActivity() , R.layout.sheet_row , Core.sheets);
        ListView ls = (ListView)view.findViewById(R.id.sheets_holder);
        ls.setAdapter(adapter);
        return view;
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }
}
