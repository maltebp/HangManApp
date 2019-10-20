package com.example.hangman;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {


    private static SoundManager instance = new SoundManager();

    private boolean soundsEnabled = true;

    private SoundManager(){}

    public static SoundManager getInstance(){
        return instance;
    }


    public void playSound(Context context, int resId){
        if(soundsEnabled){
            final MediaPlayer mp = MediaPlayer.create(context, resId );
            mp.start();
        }
    }

    public void setSoundsEnabled(boolean toggle){
        soundsEnabled = toggle;
    }

    public boolean isSoundsEnabled(){
        return soundsEnabled;
    }

}
