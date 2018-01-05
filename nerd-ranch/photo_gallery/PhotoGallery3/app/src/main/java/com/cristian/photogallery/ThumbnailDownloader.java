package com.cristian.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
    Notice you gave the class a single generic argument, <T> . Your ThumbnailDownloader ’s user,
    PhotoGalleryFragment in this case, will need to use some object to identify each download
    and to determine which UI element to update with the image once it is downloaded.
    Rather than locking the user into a specific type of object as the identifier,
    using a generic makes the implementation more flexible
 */

public class ThumbnailDownloader<T> extends HandlerThread {

    private static final String TAG = "alltest";
    //MESSAGE_DOWNLOAD will be used to identify messages as download requests
    private static final int MESSAGE_DOWNLOAD = 0;

    /*
        The newly added mRequestHandler will store a reference to the Handler responsible
        for queueing download requests as messages onto the ThumbnailDownloader
        background thread. This handler will also be in charge of processing download
        request messages when they are pulled off the queue.
     */
    private Handler mRequestHandler;

    /*
        The mRequestMap variable is a ConcurrentHashMap . A ConcurrentHashMap is a thread-safe
        version of HashMap . Here, using a download request’s identifying object of type T
        as a key, you can store and retrieve the URL associated with a particular request.
        (In this case, the identifying object is a PhotoHolder , so the request response can be
        easily routed back to the UI element where the downloaded image should be placed.)
     */
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;

    public interface ThumbnailDownloadListener<T> {
        /*
            this will be called when an image has been fully downloaded and is ready
            to be added to the UI.
         */
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    /*
        onLooperPrepared() is called before the Looper checks the queue for the first time
     */
    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {

            /*
                Handler.handleMessage(...) will get called when a download message is
                pulled off the queue and ready to be processed.)
             */
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }

    /*
        The queueThumbnail() method expects an object of type T to use as the identifier
        for the download and a String containing the URL to download
     */
    public void queueThumbnail(T target, String url) {

        Log.i(TAG, "Got a URL: " + url);

        if (url == null) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);

            /*
                You obtain a message directly from mRequestHandler , which automatically
                sets the new Message object’s target field to mRequestHandler.
                This means mRequestHandler will be in charge of processing the message
                when it is pulled off the message queue. The message’s what field is set to
                MESSAGE_DOWNLOAD.
                Its obj field is set to the T target value (a PhotoHolder in this case)
                that is passed to queueThumbnail(...) .
             */
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                            .sendToTarget();
        }
    }

    /*
        If the user rotates the screen, ThumbnailDownloader may be hanging on to invalid
        PhotoHolders. Bad things will happen if the corresponding ImageView s get pressed.
     */
    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final T target) {

        try {
            final String url = mRequestMap.get(target);

            if (url == null) {
                return;
            }

            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

            Log.i(TAG, "Bitmap created");

            /*
                Because mResponseHandler is associated with the main thread’s Looper ,
                all of the code inside of run() will be executed on the main thread.
             */
            mResponseHandler.post(new Runnable() {
                public void run() {

                    /*
                        First, you double-check the requestMap . This is necessary because the
                        recycles its views. By the time ThumbnailDownloader finishes
                        downloading the Bitmap, may have recycled the PhotoHolder and
                        requested a different URL for it. This check ensures that each
                        PhotoHolder gets the correct image, even if another request has
                        been made in the meantime.
                     */

                    if (mRequestMap.get(target) != url) {
                        return;
                    }

                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });

        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

}
