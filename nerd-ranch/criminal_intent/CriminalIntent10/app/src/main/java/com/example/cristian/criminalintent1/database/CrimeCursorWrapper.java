package com.example.cristian.criminalintent1.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.cristian.criminalintent1.Crime;
import com.example.cristian.criminalintent1.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;


/*

    Remember the DRY rule of thumb: Don’t Repeat Yourself. Instead of writing this code
    each time you need to read data from a Cursor , you can create your own Cursor subclass
    that takes care of this in one place. The easiest way to write a Cursor subclass is to
    use CursorWrapper . A CursorWrapper lets you wrap a Cursor you received from another
    place and add new methods on top of it.

 */

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {

        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;

    }

}
