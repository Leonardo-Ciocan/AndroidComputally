package com.leonardociocan.computally;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreSolver
{
    public Map<String, Double> VariableTable = new HashMap<String, Double>();


    public double EvaluateAssignment(String expr)
    {
        //expr = expr.replace(" ", "");
        String[] parts = expr.split("=");
        //add sanitize
        VariableTable.put(parts[0].trim() , EvaluateNested(parts[1]));
        return VariableTable.get(parts[0].trim());
    }


    Map<String, Integer> Precedence = new HashMap<String, Integer>()
    {
        {
            put("+",0);
            put("-",0);
            put("*",1);
            put("/",1);
            put("^",1);
        }

    };

    public ArrayList<Character> Operators = new ArrayList<Character>()
    {
        {
            add('+') ; add('-') ; add('*') ; add('/') ; add('^');
        }
    };

    public ArrayList<String> Tokenize(String expression)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        expression = expression.replace(" ", "");
        String current = "";

        for (int x = 0; x < expression.length(); x++)
        {

            if (
                    (   Operators.contains(expression.charAt(x))
                            && x != 0
                            && !Operators.contains(expression.charAt(x-1))
                    )
                            ||
                            expression.charAt(x) == '(' ||
                            expression.charAt(x) == ')')
            {
                if(!current.equals("")) tokens.add(current);

                tokens.add(String.valueOf(expression.charAt(x)));
                current = "";
            }
            else
            {
                current += expression.charAt(x);
            }
        }
        if(!current.equals("")) tokens.add(current);
        return tokens;
    }


    /*public String ConvertSyntaxSugar(String expr)
    {
        expr.replace(" ", "");
        ArrayList<String> tokens = Tokenize(expr);
        for (int x = 0; x < tokens.size(); x++)
        {
            if (tokens.get(x).contains("%"))
            {
                for (int y = x-1; y >= 0; y--)
                {
                    if (!Operators.contains(tokens.get(y)) && tokens.get(y) != "(" && tokens.get(y) != ")")
                    {
                        tokens[x] = tokens.get(y) + "*" + tokens.get(y).replace("%", "") +"/100";
                        break;
                    }
                }
            }
        }
        return tokens.Aggregate((a, b) => a + b);
    }*/




    public double Evaluate(String expression)
    {
        ArrayList<String> tokens = Tokenize(expression);

        int head = 0;
        while (tokens.size() > 1)
        {


            if ((Operators.contains(tokens.get(head+1).toCharArray()[0]) && Precedence.get(tokens.get(head+1)) == 1)

                    || ((tokens.size()>3) && Precedence.get(tokens.get(head+3)) == 0)
                    || (tokens.size() == 3)
                    )
            {
                double i1 = 0;
                try{
                    i1 = Double.parseDouble(tokens.get(head));
                }
                catch (Exception ex){
                    boolean negative = tokens.get(head).startsWith("-");
                    String val = (negative)? tokens.get(head).replace("-","") : tokens.get(head);
                    if (VariableTable.containsKey(val))
                    {
                        i1 = VariableTable.get(val);
                    }

                    if (negative) i1 *= -1;
                }



                double i2 = 0;

                try {
                    i2 = Double.parseDouble(tokens.get(head + 2));
                }
                catch (Exception ex){
                    boolean negative = tokens.get(head+2).startsWith("-");
                    String val = (negative)? tokens.get(head+2).replace("-", "") :tokens.get(head+2);
                    if (VariableTable.containsKey((val)))
                    {
                        i2 = VariableTable.get(val);
                    }
                    if (negative) i2 *= -1;
                }

                tokens.set(0 , String.valueOf(EvaluateSign(tokens.get(head+1) , i1 , i2)));
                tokens.remove(head + 1);
                tokens.remove(head + 1);
            }
            else
            {
                double i1 = 0;
                try{
                    i1 = Double.parseDouble(tokens.get(head+2));
                }
                catch (Exception ex){
                    boolean negative = tokens.get(head + 2).startsWith("-");

                    String val = (negative) ? tokens.get(head + 2).replace("-", "") : tokens.get(head + 2);
                    if (VariableTable.containsKey(val))
                    {
                        i1 = VariableTable.get(tokens.get(head + 2));
                    }
                    if (negative) i1 *= -1;
                }


                double i2 = 0;
                try{
                    i2 =Double.parseDouble(tokens.get(head+4));
                }
                catch (Exception ex){
                    boolean negative = tokens.get(head + 4).startsWith("-");
                    String val = (negative) ? tokens.get(head + 4).replace("-", "") : tokens.get(head + 4);
                    if (VariableTable.containsKey(val))
                    {
                        i2 = VariableTable.get(tokens.get(head + 4));
                    }
                    if (negative) i2 *= -1;
                }

                tokens.set(2, String.valueOf(EvaluateSign(tokens.get(3), i1, i2)));
                tokens.remove(3);
                tokens.remove(3);
            }
        }

        double ret;
        try{
            return  Double.parseDouble(tokens.get(0));
        }
        catch (Exception ex){
            return 0.0d;
        }
    }


    public double EvaluateSign(String op, double v1, double v2)
    {
        if (op.equals("+")) return v1 + v2;
        if (op.equals("-")) return v1 - v2;
        if (op.equals("/")) return v1 / v2;
        if (op.equals("*")) return v1 * v2;

        if (op.equals("^")) return Math.pow(v1,v2);
        return 0.0;
    }


    public static Pattern PercentageNotation = Pattern.compile("(-|\\+)+\\s?(\\d+|\\d+\\.\\d+)%");
    public static Pattern kNotation = Pattern.compile("(\\d+|\\d+\\.\\d+)k");
    public static Pattern FunctionNotation = Pattern.compile("(sqrt|sin|cos|exp|tan|ln)(\\d+|\\d+.\\d+)");


    //public static Regex CurrencyOperation = new Regex(@"\d+");
    /*public static Regex currencyConversionNotation = new Regex(@"(\d+|\d+\.\d+)\W?(USD|JPY|BGN|CZK|DKK|GBP|HUF|LTN|PLN|RON|SEK|CHF|NOK|HRK|RUB)\W?(to|into|as)\W?(USD|JPY|BGN|CZK|DKK|GBP|HUF|LTN|PLN|RON|SEK|CHF|NOK|HRK|RUB)" , RegexOptions.IgnoreCase);
    public static Regex kNotation = new Regex(@"(\d+|\d+.\d+)k");
    public static Regex FunctionNotation = new Regex(@"(sqrt|sin|cos|exp|tan|ln)(\d+|\d+.\d+)");

    public static Regex PercentageNotation = new Regex(@"(-|\+)\s?(\d+|\d+.\d+)\s?%");*/
    public double EvaluateNested(String expr)
    {
        /*expr = currencyConversionNotation.replace(expr, (match) =>
                {
                        String[] data = match.Value.Split(' ');
        double value = double.Parse(data[0]);
        String from = data[1].ToUpper();
        String to = data[3].ToUpper();

                /*if (!Core.data.containsKey(from))
                {
                    var alias = (Core.Aliases.Where((a, b) => a.Key.contains(from)).FirstOrDefault()).Key;
                    if (alias != null) from = (String)alias;
                }

                if (!Core.data.containsKey(to))
                {
                    var alias = (Core.Aliases.Where((a, b) => a.Key.contains(to)).FirstOrDefault()).Key;
                    if (alias != null) to = (String)alias;
                }


        double answer = Core.data[to]/Core.data[from] * value;
        return answer.toString();
        });

        expr = PercentageNotation.replace(expr, (match) =>
                {
                        String v = match.Value.replace(" ","");
        v = v.replace("%", "");
        double i = double.Parse(v);
        i /= 100;
        if (i > 0)
        {
            v = "* " + (i+1).toString();
        }
        else
        {
            v = v.replace("-", "");
            v = "* " + (1 - i).toString();
        }
        return v;
        });

        expr = kNotation.replace(expr , (match) =>
                {
                        String v = match.Value;
        v = v.replace("k", "");
        return (double.Parse(v) * 1000).toString();
        });

        expr = FunctionNotation.replace(expr, (match) =>
                {
                        String v = match.Value;
        v = v.replace(")", "");
        String[] parts = new String[2];
        int index = 0;
        foreach (char i in v)
        {
            if (char.IsNumber(i))
            {
                parts[1] += i;
            }
            else
            {
                parts[0] += i;
            }
        }
        double number = double.Parse(parts[1]);
        switch (parts[0])
        {
            case "sin":
                number = Math.Sin(number * (Degrees ? (Math.PI / 180) : 1));
                break;
            case "cos":
                number = Math.Cos(number * (Degrees ? (Math.PI / 180) : 1));
                break;
            case "tan":
                number = Math.Tan(number * (Degrees ? (Math.PI / 180) : 1));
                break;
            case "ln":
                number = Math.Log(number);
                break;
            case "exp":
                number = Math.Exp(number);
                break;
            case "sqrt":
                number = Math.Sqrt(number);
                break;
        }
        return number.toString();
        });

*/

        Matcher perc = PercentageNotation.matcher(expr);
        while (perc.find()){
            Log.e("xappr0" , expr);
            String s = expr.substring(perc.start() , perc.end()).replace("%","").replace(" ","");//.replace("+","").replace("-","");

            boolean plus = s.contains("+");
            s = s.replace("+" , "").replace("-","");
            Float f = Float.parseFloat(s);
            s ="*" +  ((plus) ? (1+f/100) : (1-f/100) );
            expr = expr.substring(0,perc.start()) + s + expr.substring(perc.end(),expr.length());
            perc = PercentageNotation.matcher(expr);
            Log.e("xappr1" , s);
        }

        Matcher knotation = kNotation.matcher(expr);
        while (knotation.find()){
            String s = expr.substring(knotation.start() , knotation.end());
            s = s.replace("k","");
            Float f = Float.parseFloat(s);
            s = String.valueOf(f*1000.0);
            expr = expr.substring(0,knotation.start()) + s + expr.substring(knotation.end(),expr.length());
            knotation = kNotation.matcher(expr);

        }

        Matcher functionnotation = FunctionNotation.matcher(expr);
        while (functionnotation.find()){
            String s = expr.substring(functionnotation.start() , functionnotation.end()).replace(" " , "");
            String sx = functionnotation.group(2);
            Double number = Double.parseDouble(sx);
            if (functionnotation.group(1).equals("sin")) {
                number = Math.sin(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            if (functionnotation.group(1).equals("sqrt")) {
                number = Math.sqrt(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            if (functionnotation.group(1).equals("cos")) {
                number = Math.cos(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            if (functionnotation.group(1).equals("tan")) {
                number = Math.tan(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            if (functionnotation.group(1).equals("log")) {
                number = Math.log(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            if (functionnotation.group(1).equals("exp")) {
                number = Math.exp(number);//* (degrees ? (Math.PI / 180) : 1));
            }
            int x;

            s = number.toString();

            expr = expr.substring(0,functionnotation.start()) + s + expr.substring(functionnotation.end(),expr.length());
            functionnotation  = FunctionNotation.matcher(expr);
        }

        //Log.e("xappr" , expr);

        if (expr.contains("="))
        {
            return EvaluateAssignment(expr);
        }

        //if (expr.contains("%")) expr = ConvertSyntaxSugar(expr);

        expr = Sanitize(expr);

        int start = 0, end = 0;
        if (!expr.contains("(") && !expr.contains(")")) return Evaluate(expr);
        ArrayList<String> tokens = Tokenize(expr);

        for (int x = 0; x < tokens.size(); x++)
        {
            if (tokens.get(x).equals("(")) start = x;
            else if (tokens.get(x).equals(")"))
            {
                end = x;
                break;
            }
        }

        String nestedExpr = "";
        for(int x = start +1;x<=end-1;x++){
            nestedExpr += tokens.get(x);
        }
        double nestedResult = Evaluate(nestedExpr);
        tokens.subList(start, end+1).clear();
        tokens.add(start, String.valueOf(nestedResult));


        String answer = "";
        for(String s : tokens){
            answer+=s;
        }
        return EvaluateNested(answer);
    }



    public String Sanitize(String expr)
    {

        expr = expr.replace("÷", "/");
        expr = expr.replace("x", "*");

        for(Character s : Operators)
        {
            expr = expr.replace(s.toString() , " " + s + " ");
        }

        expr = expr.replace("(", " ( ");
        expr = expr.replace(")", " ) ");

        List<String> tokens1 = Arrays.asList(expr.split(" "));
        ArrayList<String> tokens = new ArrayList<String>();
        for (String str : tokens1) {
            if (str != null && !str.isEmpty()) {
                tokens.add(str);
            }
        }
        //tokens.removeAll(Arrays.asList("", null));

        int removed = 0;
        int count = tokens.size();
        //Log.e("xappr" , tokens.toString());
        for (int x = 0; x < count; x++)
        {
            String value = tokens.get(x - removed).replace(" ", "");
            boolean isNumber = false;
            try{
                Double.parseDouble(value);
                isNumber = true;
            }catch (Exception ex){}

            if (       !isNumber
                    && !VariableTable.containsKey(value)
                    && !Operators.contains(value.toCharArray()[0])
                    && !value.contains("(")
                    && !value.contains(")")
                    && !value.contains("%"))
            {
                tokens.remove(x - removed);
                removed++;
            }
        }
        if (tokens.size() == 0) return "";

        String answer = "";
        for(String s : tokens){
            answer+=s;
        }
        return answer;
    }
}
