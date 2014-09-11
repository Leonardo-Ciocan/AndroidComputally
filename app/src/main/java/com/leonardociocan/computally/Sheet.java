package com.leonardociocan.computally;

import android.net.wifi.SupplicantState;

import java.util.ArrayList;
import java.util.HashMap;

public class Sheet {
    public CoreSolver Solver = new CoreSolver();
    public HashMap<String , Integer> VariableColors = new HashMap<String, Integer>();
    public ArrayList<String> Lines = new ArrayList<String>();
    public ArrayList<Substitution> Substitutions = new ArrayList<Substitution>();
}
