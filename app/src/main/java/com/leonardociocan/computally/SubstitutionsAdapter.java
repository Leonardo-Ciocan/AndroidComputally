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


public class SubstitutionsAdapter extends ArrayAdapter<Substitution> {
    public SubstitutionsAdapter(Context context, int resource, List<Substitution> objects) {
        super(context, R.layout.substitution_row, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Substitution substitution = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.substitution_row, parent, false);
        }

        final EditText from = (EditText)convertView.findViewById(R.id.from);
        final EditText to = (EditText)convertView.findViewById(R.id.to);

        from.setText(substitution.getFrom());
        to.setText(substitution.getTo());

        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                substitution.setFrom(from.getText().toString());
            }
        });

        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                substitution.setTo(to.getText().toString());
            }
        });

        return convertView;
    }
}
