package com.example.cristian.criminalintent1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by root on 1/12/17.
 */

public class CrimeListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        /* as soon as you create your RecyclerView , you give it another object called a
        LayoutManager. RecyclerView requires a LayoutManager to work.
        If you forget to give it one, it will crash. */

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    /*
       Now that you have an Adapter , your final step is to connect it to your RecyclerView.
       By Implementing a method called updateUI that sets up CrimeListFragment’s user interface.

    */

    /*
    OnResume() is used to update the data when the user change a value
    in CrimeFragment and press the back button
     */

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //outState used to get the information about subtitle
    //when the device is rotated
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        /*  you call through to the superclass implementation of onCreateOptionsMenu(...) .
            This is not required, but we recommend calling through as a matter of convention.
            That way, any menu functionality defined by the superclass will still work.
            However, it is only a convention – the base Fragment implementation of this
            method does nothing.
         */

        super.onCreateOptionsMenu(menu, inflater);


        /*  you call MenuInflater.inflate(int, Menu) and pass in the resource ID of your
            menu file. This populates the Menu instance with the items defined in your file.
        */

        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

        //Toast toast = Toast.makeText(getActivity(),"hola",Toast.LENGTH_SHORT);
        //toast.show();

    }

    /*
        to respond to selection of the MenuItem . You will create a new Crime ,
        add it to CrimeLab , and then start an instance of CrimePagerActivity
        to edit the new Crime.
    */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                //this method Declare that the options menu has changed, so should be recreated.
                //calls onCreateOptionsMenu again
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //set the subtitle of the toolbar.
    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        //the activity that is hosting the CrimeListFragment is cast to an AppCompatActivity.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        /*
            Next, the activity that is hosting the CrimeListFragment is cast to an
            AppCompatActivity. CriminalIntent uses the AppCompat library, so all
            activities will be a subclass of AppCompatActivity, which allows you
            to access the toolbar. For legacy reasons, the toolbar is still referred
            to as “action bar” in many places within the AppCompat library.
         */
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        /*
        When the CrimeListActivity is resumed, it receives a call to onResume() from the OS.When
        CrimeListActivity receives this call, its FragmentManager calls onResume() on the
        fragments that the activity is currently hosting.In this case, the only fragment
        is CrimeListFragment.
        */

        //call notifyDataSetChanged() if the CrimeAdapter is already set up.

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();

    }


    //defining the ViewHolder as an inner class

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);

        }

        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(getActivity(), CrimeActivity.class);
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        /*As it is now, this ViewHolder maintains a reference to a single view:
        the title TextView . This code expects for the itemView to be a TextView ,
        and will crash if it is not.
        */
    }

    //creating the adapter
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        /*
        The RecyclerView will communicate with this adapter when a ViewHolder needs
        to be created or connected with a Crime object. The RecyclerView itself will
        not know anything about the Crime object, but the Adapter will know all of
        Crime ’s intimate and personal details.
        */

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /*
        onCreateViewHolder is called by the RecyclerView when it needs a new View to
        display an item.
        In this method, you create the View and wrap it in a ViewHolder.
        The RecyclerView does not expect that you will hook it up to any data yet.

        For the View , you inflate a layout from the Android standard library called
        simple_list_item_1 . This layout contains a single TextView, styled to look
        nice in a list.

        */

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /*
        onBindViewHolder : This method will bind a ViewHolder’s View to your model object.
        It receives the ViewHolder and a position in your data set. To bind your View,
        you use that position to find the right model data. Then you update the View
        to reflect that model data.

        In your implementation, that position is the index of the Crime in your array.
        Once you pull it out, you bind that Crime to your View by sending its title to
        your ViewHolder’s TextView.
        */

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            //holder.mTitleTextView.setText(crime.getTitle());
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }




    }








}
