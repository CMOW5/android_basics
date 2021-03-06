package com.example.cristian.recyclerviewbasic2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 1/12/17.
 */
                                    //AppCompat extends from FragmentActivity
public abstract class SingleFragmentActivity extends AppCompatActivity {

    /*  Subclasses of SingleFragmentActivity will implement this method to return
        an instance of the fragment that the activity is hosting.

        SingleFragmentActivity is a generic class of hosting activities

    */
    protected abstract Fragment createFragment();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity_fragment is a generic layout with a fragment Container
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
