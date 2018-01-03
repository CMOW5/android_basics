package com.cristian.photogallery;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "alltest";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //so that rotation does not repeatedly fire off new AsyncTask s to fetch the JSON data
        setRetainInstance(true);
        new FetchItemsTask().execute();

        //this handler will be attached to the main thread’s Looper
        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                photoHolder.bindDrawable(drawable);
            }
        });
        mThumbnailDownloader.start();
        /*  This is a way to ensure that the thread’s guts are ready before proceeding
            Until you call getLooper() , there is no guarantee that onLooperPrepared()
            has been called, so there is a possibility that calls to queueThumbnail(...)
            will fail due to a null Handler .
        */
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
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

        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = itemView.findViewById(R.id.fragment_photo_gallery_image_view);
        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item,parent,false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
            holder.bindDrawable(placeholder);
            mThumbnailDownloader.queueThumbnail(holder, galleryItem.getUrl());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
            you call quit() to terminate the thread inside onDestroy() .
            This is critical. If you do not quit your HandlerThread s, they will never die
         */
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }
}
