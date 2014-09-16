package com.leonardociocan.computally;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.StaticLayout;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE = "sheets";
    public static final String NAME_COLUMN = "name";
    public static final String ID_COLUMN = "_id";

    public static final String LINES_TABLE = "lines";
    public static final String VALUE_COLUMN = "value";

    public static final String DATABASE_NAME = "computally.db";

    private static final String DATABASE_CREATE = "create table "
            + TABLE + "("+
    ID_COLUMN
    + " integer primary key autoincrement, " + NAME_COLUMN
            + " text); ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        sqLiteDatabase.execSQL("create table lines(_id integer primary key autoincrement, value text ,sheetId integer,FOREIGN KEY(sheetId) REFERENCES sheets(_id) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
