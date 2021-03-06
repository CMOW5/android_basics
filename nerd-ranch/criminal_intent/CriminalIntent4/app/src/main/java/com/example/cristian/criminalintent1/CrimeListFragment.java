package com.example.cristian.criminalintent1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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

    /*
         OnResume() is used to update the data when the user change a value
         in CrimeFragment and press the back button, first the hosting activity's
         onResume() method is called and then its fragment manager calls onResume()
         on the fragment that the activity is currently hosting
     */

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) { //fisrt call in onCreateView(..) method
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else { //notify a change of data when the user presses the back button
            mAdapter.notifyDataSetChanged();
        }

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

            //why these lines solves the checkbox update problem?
            /*
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCrime.setSolved(isChecked);
                }
            });
            */

        }

        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(getActivity(), CrimeActivity.class);
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
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
