package com.cristian.beatbox1;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cristian on 21/12/17.
 */

public class BeatBox {

    public static final String TAG = "cmetest";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    /*  Assets are accessed using the AssetManager class.
     *  You can get an AssetManager from any Context .
     */
    public BeatBox(Context context) {
        mAssets = context.getAssets();
        // This old constructor is deprecated, but we need it for
        // compatibility.
        /* The first parameter specifies how many sounds can play at
            any given time. Here, you pass in 5 . If five
            sounds are playing and you try to play a sixth one,
            the SoundPool will stop playing the oldest one.

            The second parameter determines the kind of audio stream
            your SoundPool will play on. Android has a variety of
            different audio streams, each of which has its own
            independent volume settings. This is why turning down
            the music does not also turn down your alarms.
            Check out the documentation for the AUDIO_* constants
            in AudioManager to see the other options.
            STREAM_MUSIC will put you on the same volume setting as
            music and games on the device.

            And the last parameter specifies the quality for the
            sample rate converter. The documentation says it
            is ignored, so you just pass in 0 .
         */
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;

        try {
            /*  To get a listing of what you have in your assets,
             *  you can use the list(String) method
             */
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.d(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.d(TAG, "Could not list assets", ioe);
            return;
        }

        for (String filename : soundNames) {
            try {
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            load(sound);
            mSounds.add(sound);
            } catch (IOException ioe) {
                Log.d(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        /* Calling mSoundPool.load(AssetFileDescriptor, int) loads a
        file into your SoundPool for later playback. To keep track
        of the sound and play it back again (or unload it),
        mSoundPool.load(...) returns an int ID, which you stash in
        the mSoundId field you just defined
        */
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();

        /* Before playing your soundId , you check to make sure it
           is not null . This might happen if the Sound failed to load.
        */
        if (soundId == null) {
            Log.d(TAG,"sound is null");
            return;
        }

        Log.d(TAG,"playing sound");
        /*
            the sound ID, volume on the left, volume on the right,
            priority (which is ignored), whether the audio should loop,
            and playback rate. For full volume and normal playback
            rate, you pass in 1.0 . Passing in 0 for the looping
            value says “do not loop.” (You can pass in -1 if you
            want it to loop forever.
         */
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
