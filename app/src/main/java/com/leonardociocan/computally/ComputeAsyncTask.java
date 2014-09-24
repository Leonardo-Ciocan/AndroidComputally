package com.leonardociocan.computally;

import android.graphics.Color;
import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by leo on 16/09/14.
 */
public class ComputeAsyncTask extends AsyncTask<String, Void, Double> {
    public Double doInBackground(String... expr) {
        double answer = 0d;
        try{
            answer = Core.selectedSheet.Solver.EvaluateNested(expr[0]);
        }
        catch (Exception ex){}
        return answer;
    }


    FormulaLine line;
    public ComputeAsyncTask(FormulaLine line){
        this.line = line;
    }
    protected void onPostExecute(Double result) {
        String resultStr = "0.0";
        line.resultStr = String.valueOf(result);



        line.updateResult();

        for(String s : line.sheet.Solver.VariableTable.keySet()){
            if(!line.sheet.VariableColors.containsKey(s)){
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                line.sheet.VariableColors.put(s.trim() , color);
            }
        }
    }
}
