package com.example.cristian.criminalintent1;

import android.support.v4.app.Fragment;

/**
 * Created by root on 1/12/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
