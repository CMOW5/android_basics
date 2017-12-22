package com.cristian.beatbox1;

/**
 * Created by cristian on 21/12/17.
 */

public class Sound {

    private String mAssetPath;
    private String mName;

    /* In the constructor, you do a little work to make a presentable
    name for your sound. First, you split off the filename using
    String.split(String) . Once you have done that, you use
    String.replace(String, String) to strip off the file extension, too.
    */
    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

}
