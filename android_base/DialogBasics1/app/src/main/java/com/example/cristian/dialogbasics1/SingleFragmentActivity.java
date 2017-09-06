package com.example.cristian.dialogbasics1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    /*  Subclasses of SingleFragmentActivity will implement this method to return
       an instance of the fragment that the activity is hosting.

       SingleFragmentActivity is a generic class of hosting activities

   */
    protected abstract Fragment createFragment();

    private final String classname = SingleFragmentActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        String methodname = " onCreate(..) ";

        //activity_fragment is a generic layout with a fragment Container
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            Log.d(Utility.LOG_TAG, classname + methodname + " fragment == null ");
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        else
            Log.d(Utility.LOG_TAG, classname + methodname + " fragment not null ");

    }

}
