package com.example.cristian.criminalintent1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

/*
*   CrimeActivity is a hosting activity of a CrimeFragment
* */
public class CrimeActivity extends SingleFragmentActivity
        implements CrimeFragment.OnCrimeModifiedListener {


    private static final String EXTRA_CRIME_ID =
            "com.cristian.android.criminalintent.crime_id";

    private UUID mID;
    @Override
    protected Fragment createFragment() {
        //return new CrimeFragment();
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        mID = crimeId;
        return CrimeFragment.newInstance(crimeId); //para pasar los datos
                                                   //del intent en la actividad
                                                    // al fragmento

    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    public void onCrimeModified(boolean isModified) {
        // The user selected the headline of an article from the HeadlinesFragment
        Log.d("deg","callback");
        Intent intent = CrimeListActivity.newIntent(this, mID);
        setResult(Activity.RESULT_OK, intent);

    }

}
