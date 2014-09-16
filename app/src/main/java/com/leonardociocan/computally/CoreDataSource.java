package com.leonardociocan.computally;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class CoreDataSource {
    SQLiteDatabase database;
    DBHelper helper;

    public  CoreDataSource(Context context){
        helper = new DBHelper(context);
    }

    public void open(){
        database = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public void addSheet(String name){
        ContentValues values = new ContentValues();
        values.put(helper.NAME_COLUMN , name);
        database.insert(helper.TABLE , null , values);
    }

    public void addLine(long sheetId , String value){
        ContentValues values = new ContentValues();
        values.put(helper.VALUE_COLUMN , value);
        values.put("sheetId" , sheetId);
        database.insert("lines" , null , values);
    }

    public void updateLine(long id , String value ){
        ContentValues values = new ContentValues();
        values.put(helper.VALUE_COLUMN , value);
        database.update("lines", values, helper.ID_COLUMN + "=" + id, null);
    }

    public void deleteSheet(long id){
        database.delete(helper.TABLE , helper.ID_COLUMN + "=" + id , null);
    }

    public void deleteLine(long id){
        database.delete("lines" , helper.ID_COLUMN +"=" + id , null);
    }

    public ArrayList<Sheet> GetSheets(){
        ArrayList<Sheet> habits = new ArrayList<Sheet>();
        Cursor cursor = database.query(helper.TABLE , new String[] { helper.ID_COLUMN , helper.NAME_COLUMN }
        ,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);

            Sheet last = new Sheet(name , id);
            last.Lines = GetLines(last.id);
            habits.add(last);
            cursor.moveToNext();
        }
        cursor.close();
        return habits;
    }

    public ArrayList<Expression> GetLines(long sheetId){
        ArrayList<Expression> habits = new ArrayList<Expression>();
        Cursor cursor = database.query("lines" , new String[] { helper.ID_COLUMN , helper.VALUE_COLUMN }
                ,"sheetId="+sheetId,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String value = cursor.getString(1);

            habits.add(new Expression(id , value));
            cursor.moveToNext();
        }
        cursor.close();
        return habits;
    }



}
