package com.leonardociocan.computally;

import java.util.ArrayList;
import java.util.HashMap;

public class Sheet {
    long id;
    public CoreSolver Solver = new CoreSolver();
    public HashMap<String , Integer> VariableColors = new HashMap<String, Integer>();
    public ArrayList<Expression> Lines = new ArrayList<Expression>();
    public ArrayList<Substitution> Substitutions = new ArrayList<Substitution>();
    public String Name = "";

    public Sheet(String name) {
        Name = name;
    }

    public Sheet(String name , long id) {
        this.Name = name;
        this.id = id;
    }
    public Sheet(){}
}
