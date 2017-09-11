package com.example.cristian.criminalintent1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
This import will let you refer to the String constants in CrimeDbSchema.CrimeTable
by typing in CrimeTable.Cols.UUID , rather than typing out the entirety of
CrimeDbSchema.CrimeTable.Cols.UUID . Use that to finish filling out your table
definition code.
*/

import com.example.cristian.criminalintent1.database.CrimeDbSchema.CrimeTable;

/*
    A SQLiteOpenHelper is a class designed to get rid of the grunt work of opening
    a SQLiteDatabase .
*/

public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //code to create the initial database in onCreate(SQLiteDatabase)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")");
    }

    //code to handle any upgrades in onUpgrade(SQLiteDatabase, int, int)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
