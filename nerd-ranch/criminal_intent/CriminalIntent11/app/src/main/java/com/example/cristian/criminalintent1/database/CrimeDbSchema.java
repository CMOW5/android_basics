package com.example.cristian.criminalintent1.database;

/**
 * Created by root on 2/3/17.
 */

public class CrimeDbSchema {

    public static final class CrimeTable {

        /*
            The CrimeTable class only exists to define the String constants needed to describe
            the moving pieces of your table definition. The first piece of that definition
            is the name of the table in your database, CrimeTable.NAME .
        */

        public static final String NAME = "crimes"; //table name

        //table columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }

    }



}
