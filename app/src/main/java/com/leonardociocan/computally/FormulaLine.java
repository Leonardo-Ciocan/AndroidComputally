package com.leonardociocan.computally;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaLine extends LinearLayout {

    int x = 0;
    Sheet sheet;
    public FormulaLine(Context context , Sheet t , int x ) {
        super(context);
        this.x = x;
        this.sheet = t;
        /*FormulaEditText text = new FormulaEditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.weight = 1;
        text.setGravity(Gravity.LEFT);
        ((LinearLayout.LayoutParams)text.getLayoutParams()).setMargins(0, (int) getResources().getDimension(R.dimen.syntax_text_margin), 0, 0);
        text.setLayoutParams(params);

        addView(text, params);

        TextView result = new TextView(context);
        result.setText("0");
        LinearLayout.LayoutParams result_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.MATCH_PARENT);
        result.setMaxWidth(  (int)getResources().getDimension(R.dimen.syntax_text_result));
        //result.wei;
        result.setLayoutParams(result_params);
        addView(result, result_params);*/

        init(context);
        //addView(layout);
    }

    public FormulaLine(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context);


    }

    public FormulaLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    TextView resultView;
    String resultStr = "0";
    static Integer num = 1;
    void init(final Context context){
        inflate(context, R.layout.line_layout, this);
        final FormulaEditText input = (FormulaEditText)findViewById(R.id.input);
        input.setSheet(sheet);

        final TextView result= (TextView)findViewById(R.id.result);
        input.setText(sheet.Lines.get(x));
resultView = result;
        Core.setSheetChangedListener(new SheetChangedListener() {
            @Override
            public void SheetChanged() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String resultstr = "0.0";
                        try{
                            resultStr = String.valueOf(sheet.Solver.EvaluateNested(input.getText().toString()));
                        } catch (Exception ex){}

                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                updateResult();
                            }
                        });

                        for(String s : sheet.Solver.VariableTable.keySet()){
                            if(!sheet.VariableColors.containsKey(s)){
                                Random rnd = new Random();
                                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                                sheet.VariableColors.put(s.trim() , color);
                            }
                        }
                    }
                }).start();

            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                sheet.Lines.set(x , input.getText().toString());
                if(!input.flag)Core.reloadAll();
                if(!input.flag){
                    if(sheet.Substitutions.size() == 0 )return;
                    int pos = input.getSelectionStart();
                    for(Substitution sub : sheet.Substitutions){
                        if(sub.getFrom().equals("") || sub.getTo().equals("")) continue;
                        Pattern pattern = Pattern.compile("("+sub.from+")");
                        Matcher m = pattern.matcher(input.getText());
                        sheet.Lines.set(x , m.replaceAll(sub.getTo()));
                    }
                    input.flag = true;
                    input.setText(sheet.Lines.get(x));
                    input.flag = false;
                    input.setSelection( input.getText().length() );
                    input.highlight();
                }
            }
        });
        final TextView number= (TextView)findViewById(R.id.number);
        number.setText(num.toString() +".");
        num++;
    }

    void updateResult(){
        resultView.setText(resultStr);
    }
    void updateTextSize(){
        final FormulaEditText input = (FormulaEditText)findViewById(R.id.input);

        String str = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("font_size","10");
        float f = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(str) , getResources()
                        .getDisplayMetrics());
        input.setTextSize(f);
        resultView.setTextSize(f);
    }
}
