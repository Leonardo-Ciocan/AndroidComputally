package com.leonardociocan.computally;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaEditText extends EditText {

    final Pattern number = Pattern.compile("(\\d+|\\d+.\\d+)");
    final Pattern operator = Pattern.compile("[-+/\\\\*=]");
    final Pattern variable = Pattern.compile("");

    public FormulaEditText(Context context) {
        super(context);
        init(context);
    }

    public FormulaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormulaEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    final FormulaEditText txt =this;

    public boolean flag = false;
    public void init(Context context){

        Core.setSheetChangedListener(new SheetChangedListener() {
            @Override
            public void SheetChanged() {
                if(flag || txt.getText().toString().equals("")) return;
                highlight();
            }
        });

        txt.setTextSize(getResources().getDimension(R.dimen.syntax_text));

        txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/inconsolata.otf"));



        txt.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                if(flag || txt.getText().toString().equals("")) return;
                highlight();
            }
        });
    }

    public void highlight(){

        if(txt.getText().length() == 0)return;
        int pos = txt.getSelectionStart();
        Spannable WordtoSpan = new SpannableString(txt.getText());

        WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 0, txt.getText().length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String s = txt.getText().toString();

        Matcher m = number.matcher(txt.getText().toString());
        while(m.find()){
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        Matcher n = operator.matcher(txt.getText());
        while(n.find()){
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), n.start(), n.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if(!Core.sheet.VariableColors.isEmpty()) {
            String varRegex = "\\s?(";
            for (String var : Core.sheet.VariableColors.keySet()) {
                varRegex += var + "|";
            }
            varRegex = varRegex.substring(0, varRegex.length() - 1) + ")\\s?";

            Pattern pattern = Pattern.compile(varRegex);
            Matcher vn = pattern.matcher(txt.getText());
            while (vn.find()) {
                int c = Core.sheet.VariableColors.get(txt.getText().toString().substring(vn.start(),vn.end()).trim());
                WordtoSpan.setSpan(
                        new ForegroundColorSpan(c),
                        vn.start(), vn.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        int nrGroups = m.groupCount();

        //txt.setText(Html.fromHtml(txt.getText().toString().replace("cake","<b>cake</b>")));

        for(int x =0; x < nrGroups;x++){
        }
        flag = true;
        txt.setText(WordtoSpan);
        flag = false;
        txt.setSelection(pos);
    }



}
