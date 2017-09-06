package com.example.cristian.criminalintent1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.cristian.criminalintent1.database.CrimeBaseHelper;
import com.example.cristian.criminalintent1.database.CrimeCursorWrapper;
import com.example.cristian.criminalintent1.database.CrimeDbSchema;
import com.example.cristian.criminalintent1.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by root on 1/12/17.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){

        mContext = context.getApplicationContext(); //for later use

        /*
        It’s important to think about the lifetime of each of these objects. If any of your
        activities exist, Android will have also created an Application object.
        Activities come and go as the user navigates through your application but
        the application object will still exist. It has a much longer lifetime than
        any one activity. The CrimeLab is a singleton, which means that once it is created,
        it will not be destroyed until your entire application process is destroyed.
        The CrimeLab maintains a reference to its mContext object. If you store an
        activity as the mContext object, that activity will never be cleaned up by
        the garbage collector because the CrimeLab has a reference to it.
        Even if the user has navigated away from that activity, it will never be cleaned up.
        To avoid this wasteful situation, you use the application context so that your
        activities can come and go and the CrimeLab can maintain a reference
        to a Context object. Always think about the lifetime of
        your activities as you keep a reference to them.
        */


        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

        /*

        When you call getWritableDatabase() here, CrimeBaseHelper will do the following:
        1. Open up /data/data/com.bignerdranch.android.criminalintent/databases/crimeBase.db ,
                creating a new database file if it does not already exist.

        2. If this is the first time the database has been created, call onCreate(SQLiteDatabase) , then save
        out the latest version number.

        3. If this is not the first time, check the version number in the database. If the
        version number in CrimeOpenHelper is higher, call onUpgrade(SQLiteDatabase, int, int).

        */


        /*
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(crime);
        }
        */
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
        /*
        The insert(String, String, ContentValues) method has two important arguments,
        and one that is rarely used. The first argument is the table you want to insert
        into – here, CrimeTable.NAME . The last argument is the data you want to put in.

        The second argument is called nullColumnHack. if you to call insert(...) with an
        empty ContentValues . SQLite does not allow this, so your insert(...) call would fail.

        If you passed in a value of uuid for nullColumnHack , though, it would ignore that empty
        ContentValues . Instead, it would pass in a ContentValues with uuid set to null . This would allow
        your insert(...) to succeed and create a new row.
        */

    }


    //ode to query for all crimes, walk the cursor, and populate a Crime list.
    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        /*

        Database cursors are called cursors because they always have their finger on a
        particular place in a query. So to pull the data out of a cursor, you move it
        to the first element by calling moveToFirst(), and then reading in row data.
        Each time you want to advance to a new row, you call moveToNext() ,
        until finally isAfterLast() tells you that your pointer is off the end of the dataset.

         */

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();

            /*

                The last important thing to do is to call close() on your Cursor .
                This bit of housekeeping is important. If you do not do it, your
                Android device will spit out nasty error logs to berate you. Even
                worse, if you make a habit out of it, you will eventually run out
                of open file handles and crash your app. So: close your cursors.

             */
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }

    //method to update rows in the database.
    public void updateCrime(Crime crime) {

        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});

        /*
        In The update(String, ContentValues, String, String[]) method you pass in
        the table name you want to update and the ContentValues you want to assign to each
        row you update. However, the last bit is different, because now you have to specify
        which rows get updated. You do that by building a where clause (the third argument),
        and then specifying values for the arguments in the where clause
        (the final String[] array).

        the were clausure ? is to avoid a SQL injection attack.

        */
    }



    /*
        Writes and updates to databases are done with the assistance of a class
        called ContentValues. ContentValues is a key-value store class, like Java’s
        HashMap or the Bundle s you have been using so far. However, unlike HashMap
        or Bundle it is specifically designed to store the kinds of data SQLite can
        hold.
    */
    private static ContentValues getContentValues(Crime crime) {

        /*
            For the keys, you use your column names. These are not arbitrary names;
            they specify the columns that you want to insert or update

            Every column is specified here except for _id , which is automatically created
            for you as a unique row ID.
        */
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }


    //private Cursor queryCrimes(String whereClause, String[] whereArgs) {
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        /*

        The table argument is the table to query. The columns argument names which
        columns you want values for and what order you want to receive them in.
        And then where and whereArgs do the same thing they do in update(...).

        public Cursor query(
            String table,
            String[] columns,
            String where,
            String[] whereArgs,
            String groupBy,
            String having,
            String orderBy,
            String limit)

         */

        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        //return cursor;
        return new CrimeCursorWrapper(cursor);
    }

    public File getPhotoFile(Crime crime) {

        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        /* verifies that there is external storage to save them to */
        if (externalFilesDir == null) {
            //If there no external storage
            return null;
        }

        return new File(externalFilesDir, crime.getPhotoFilename());
    }


}
