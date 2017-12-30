package com.cristian.photogallery;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "alltest";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //so that rotation does not repeatedly fire off new AsyncTask s to fetch the JSON data
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = rootView.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        /*  You call setupAdapter() in onCreateView(...) so that every time a new
            RecyclerView is created, it is reconfigured with an appropriate adapter.
        */
        setupAdapter();

        return rootView;
    }

    private void setupAdapter() {

        /*
            Notice that you check to see whether isAdded() is true before setting the adapter.
            This confirms that the fragment has been attached to an activity, and in turn that
            getActivity() will not be null.

            Remember that fragments can exist unattached to any activity.
            Before now, this possibility has not come up because your method calls have
            been driven by callbacks from the framework. In this scenario, if a fragment
            is receiving callbacks, then it definitely is attached to an activity.
            No activity, no callbacks.

            However, now that you are using an AsyncTask you are triggering some callbacks
            from a background thread. Thus you cannot assume that the fragment is attached
            to an activity.
            You must check to make sure that your fragment is still attached.
            If it is not, then operations that rely on that activity
            (like creating your PhotoAdapter , which in turn creates a TextView using
            the hosting activity as the context) will fail.
            This is why, in your code above, you check that isAdded() is true before
            setting the adapter.
        */
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }

        public void bindGalleryItem(GalleryItem item) {
            mTitleTextView.setText(item.toString());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //LayoutInflater inflater = LayoutInflater.from(getActivity());
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            holder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

}
