package com.example.cristian.recyclerviewbasic1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ItemSingleton mItemSingleton;
    private List<SingleItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mItemSingleton = ItemSingleton.get(getApplicationContext());
        Toast.makeText(this, String.valueOf(mItemSingleton.getList().size()), Toast.LENGTH_SHORT).show();
        mList = mItemSingleton.getList();
        mAdapter = new MyAdapter(mList, getApplicationContext());
        //mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);


    }
}
