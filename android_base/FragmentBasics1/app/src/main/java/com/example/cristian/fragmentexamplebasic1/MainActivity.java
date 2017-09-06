package com.example.cristian.fragmentexamplebasic1;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//If you're using the v7 appcompat library, your activity should
// extend AppCompatActivity instead of FragmentActivity, which is a subclass of FragmentActivity

public class MainActivity extends AppCompatActivity implements  BlankFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        /*
            First, you ask the FragmentManager for the fragment with a container
            view ID of R.id.fragment_container.
            If this fragment is already in the list,
            the FragmentManager will return it.
         */

        //search if there's a fragment in the given fragment_container
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        /*
           Why would a fragment already be in the list?
           The call to MainActivity.onCreate(...) could be in response to
           MainActivity being re-created after being destroyed on rotation or to
           reclaim memory. When an activity is destroyed, its FragmentManager saves out
           its list of fragments. When the activity is re-created,
           the new FragmentManager retrieves the list and re-creates the listed
           fragments to make everything as it was before.

            On the other hand, if there is no fragment with the given container
            view ID, then fragment will be null. In this case, you create a
            new CrimeFragment and a new fragment transaction that adds
            the fragment to the list.
         */

        if (fragment == null){

            fragment = new BlankFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();

            /*
                The FragmentManager.beginTransaction() method creates and returns
                an instance of FragmentTransaction. The FragmentTransaction class
                uses a fluent interface - methods that configure FragmentTransaction
                return a FragmentTransaction instead of void ,which allows you to
                chain them together. So the code highlighted above says,
                “Create a new fragment transaction, include one add operation in it,
                 and then commit it.”
             */
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
