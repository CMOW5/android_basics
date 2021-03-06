package com.example.cristian.criminalintent1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 1/12/17.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        /* as soon as you create your RecyclerView , you give it another object called a
        LayoutManager. RecyclerView requires a LayoutManager to work.
        If you forget to give it one, it will crash. */

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /*
       Now that you have an Adapter , your final step is to connect it to your RecyclerView.
       By Implementing a method called updateUI that sets up CrimeListFragment’s user interface.

    */
    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);

    }


    //defining the ViewHolder as an inner class
    //ViewHolder's job : it holds on to a View

    private class CrimeHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
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
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
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
            holder.mTitleTextView.setText(crime.getTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }




    }








}
