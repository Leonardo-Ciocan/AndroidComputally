package com.leonardociocan.computally;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 08/09/14.
 */
public class Core {
    public static Sheet selectedSheet = new Sheet();
    public static List<Sheet> sheets = new ArrayList<Sheet>();

    public void addSheet(Sheet s){

    }




    static ArrayList<SheetChangedListener> listener = new ArrayList<SheetChangedListener>();




    public static void setSheetChangedListener(SheetChangedListener listener){
        Core.listener.add( listener);
    }

    public static void reloadAll(){
        for(SheetChangedListener l : listener){
            l.SheetChanged();
        }
    }
}
