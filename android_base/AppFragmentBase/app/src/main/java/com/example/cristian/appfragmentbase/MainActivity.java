package com.example.cristian.appfragmentbase;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cristian.appfragmentbase.Fragments.MainFragment;

public class MainActivity extends SingleFragmentActivity
        implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance("param1","param1");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "button pressed", Toast.LENGTH_SHORT).show();

        if (findViewById(R.id.detail_fragment_container) == null)
        {
            //no detail master interface
        }
        else
        {
            Fragment newDetail = new Fragment(); //replace with the proper fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }
}
