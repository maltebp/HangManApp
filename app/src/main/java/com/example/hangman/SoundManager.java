package com.example.hangman;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Singleton to eeps track of whether sound is enabled or not, and is used to play
 * a given sound effect.
 *
 * To be used for music as well
 */
public class SoundManager {


    private static SoundManager instance = new SoundManager();

    private boolean soundsEnabled = true;

    private SoundManager(){}

    public static SoundManager getInstance(){
        return instance;
    }


    /**
     * Plays the sound effect if soundsEnabled is true
     */
    public void playSound(Context context, int resId){
        if(soundsEnabled){
            final MediaPlayer mp = MediaPlayer.create(context, resId );
            mp.start();
        }
    }

    /**
     * Enable/disable the sound effects
     */
    public void setSoundsEnabled(boolean toggle){
        soundsEnabled = toggle;
    }
}
