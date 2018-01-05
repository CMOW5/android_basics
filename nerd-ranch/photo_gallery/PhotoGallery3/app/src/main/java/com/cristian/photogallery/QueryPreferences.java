package com.cristian.photogallery;

import android.content.Context;
import android.preference.PreferenceManager;


public class QueryPreferences {

    //key for the query preference
    private static final String PREF_SEARCH_QUERY = "searchQuery";

    //returns the query value stored in shared preferences
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();

        /*
            The apply() method makes the change in memory
            immediately and then does the actual file writing on a background thread.
         */
    }
}
