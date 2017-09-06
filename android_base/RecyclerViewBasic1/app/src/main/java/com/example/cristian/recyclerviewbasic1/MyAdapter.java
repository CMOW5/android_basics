package com.example.cristian.recyclerviewbasic1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private String[] mDataset;
    private List<SingleItem> mList;
    private static Context mContext; //to show toast

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<SingleItem> mList, Context context) {
        this.mList = mList;
        mContext = context;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
         implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView mTextView;
        private SingleItem mItem;

        public ViewHolder(View itemView) { //constructor
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.my_text_view);
        }

        public void bindItem(SingleItem item){
            mItem = item;
            mTextView.setText(mItem.getName());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, mItem.getName() + " Clicked", Toast.LENGTH_SHORT).show();
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SingleItem item = mList.get(position);
        //holder.mTextView.setText(item.getName());
        holder.bindItem(item);
        //holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
        //return mDataset.length;
    }
}



