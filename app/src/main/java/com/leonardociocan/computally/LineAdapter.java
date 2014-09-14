package com.leonardociocan.computally;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;


public class LineAdapter extends ArrayAdapter<String> {
    public LineAdapter(Context context, List<String> objects) {
        super(context, R.layout.line_layout, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new FormulaLine(getContext() , Core.selectedSheet , position );
        }

        return convertView;

    }
}
