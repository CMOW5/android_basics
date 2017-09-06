package com.example.cristian.recyclerviewbasic2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        /* as soon as you create your RecyclerView , you give it another object called a
        LayoutManager. RecyclerView requires a LayoutManager to work.
        If you forget to give it one, it will crash. */
        layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /*
       Now that you have an Adapter , your final step is to connect it to your RecyclerView.
       By Implementing a method called updateUI that sets up ListFragment’s user interface.

    */
    private void updateUI(){

        ItemSingleton itemsingleton = ItemSingleton.get(getActivity());
        List<SingleItem> itemsList = itemsingleton.getList();

        mAdapter = new MyAdapter(itemsList);
        mRecyclerView.setAdapter(mAdapter);

    }


    //defining the ViewHolder as an inner class

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SingleItem mItem;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_solved_check_box);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mItem.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }

        public void bindItem(SingleItem item) {
            mItem = item;
            mTitleTextView.setText(mItem.getTitle());
            //mDateTextView.setText(mItem.getDate().toString());
            //mSolvedCheckBox.setChecked(mItem.isSolved());
        }

        /*As it is now, this ViewHolder maintains a reference to a single view:
        the title TextView . This code expects for the itemView to be a TextView ,
        and will crash if it is not.
        */
    }

    //creating the adapter
    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        /*
        The RecyclerView will communicate with this adapter when a ViewHolder needs
        to be created or connected with a SingleItem object. The RecyclerView itself will
        not know anything about the SingleItem object, but the Adapter will know all of
        SingleItem ’s intimate and personal details.
        */

        private List<SingleItem> mItemsList;

        public MyAdapter(List<SingleItem> itemsList) {
            mItemsList = itemsList;
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
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.single_list_item, parent, false);
            return new MyHolder(view);
        }

        /*
        onBindViewHolder : This method will bind a ViewHolder’s View to your model object.
        It receives the ViewHolder and a position in your data set. To bind your View,
        you use that position to find the right model data. Then you update the View
        to reflect that model data.

        In your implementation, that position is the index of the SingleItem in your array.
        Once you pull it out, you bind that SingleItem to your View by sending its title to
        your ViewHolder’s TextView.
        */

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            SingleItem item = mItemsList.get(position);
            //holder.mTitleTextView.setText(item.getTitle());
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return mItemsList.size();
        }




    }








}
