package com.example.cristian.criminalintent1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by root on 1/12/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    private static final String EXTRA_MODIFIED_CRIME_ID =
            "com.cristian.android.criminalintent.modified_crime_id";

    private final int RESULT_CODE_CRIME_ACTIVITY = 1;
    
    @Override
    protected Fragment createFragment() {
        Log.d("deg","createfragment");
        return new CrimeListFragment();
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        //Intent intent = new Intent(packageContext, CrimeListActivity.class);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MODIFIED_CRIME_ID, crimeId);
        return intent;
    }

}
