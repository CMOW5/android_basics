package com.example.cristian.criminalintent1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by cristian on 5/09/17.
 */

public class PictureUtils {

    /*
        A Bitmap is a simple object that stores literal pixel data.
        That means that even if the original file was compressed,
        there is no compression in the Bitmap itself.
        So a 16 megapixel 24-bit camera image which might only be a 5 Mb
        JPG would blow up to 48 Mb loaded into a Bitmap object (!).

        it does mean that you will need to scale the bitmap down by hand
     */

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {

        // Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // Figure out how much to scale down by
        /*
         inSampleSize . This determines how big each “sample” should be
         for each pixel – a sample size of 1 has one final horizontal pixel
         for each horizontal pixel in the original file, and  a sample size
         of 2 has one horizontal pixel for every two horizontal pixels in
         the original file. So when inSampleSize is 2 ,
         the image has a quarter of the number of pixels of the original.
         */
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }

    /*
        This method checks to see how big the screen is, and then scales
        the image down to that size. The ImageView you load into will always
         be smaller than this size, so this is a very conservative estimate.
     */
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
