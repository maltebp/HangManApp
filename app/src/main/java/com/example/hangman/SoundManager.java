package com.example.hangman;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.annotation.RawRes;

import java.util.LinkedList;

/**
 * Singleton to keeps track of whether sound is enabled or not, and is used to play
 * a given sound effect.
 *
 * To be used for music as well
 */
public class SoundManager {

    private static SoundManager instance = new SoundManager();
    private SoundManager(){}

    public static SoundManager getInstance(){
        return instance;
    }

    private boolean soundEnabled = true;
    private boolean musicEnabled = true;
    private LinkedList<SoundInstance> sounds = new LinkedList<>();
    private SoundInstance music;


    /**
     * Plays the sound effect if soundEnabled is true
     */
    public void playSound(Context context, @RawRes int resId, float volume){
        if(soundEnabled){
            SoundInstance sound = new SoundInstance(context, resId, volume, false);
            sound.start();
            sounds.add(sound);
        }
    }

    public void playSound(Context context, @RawRes int resId){
        playSound(context, resId, 1);
    }


    public void playMusic(Context context, @RawRes int resId, float volume){
        if(music != null)
            music.stop();
        music = new SoundInstance(context, resId, volume, true);
        if(musicEnabled){
            music.start();
        }
    }


    public void clearMusic(){
        if(music != null)
            music.stop();
            music = null;
    }

    /**
     * Enable/disable the sound effects
     */
    public void toggleSound(boolean toggle){
        soundEnabled = toggle;
        if(!toggle){
            for(SoundInstance sound : sounds){
                sound.stop();
            }
            sounds.clear();
        }
    }

    public void toggleMusic(boolean toggle){
        musicEnabled = toggle;
        if(music != null){
            if(!toggle){
                music.pause();
            }else{
                music.start();
            }
        }
    }

    public boolean isMusicEnabled(){
        return musicEnabled;
    }


    public boolean isSoundEnabled(){
        return soundEnabled;
    }


    public class SoundInstance implements MediaPlayer.OnCompletionListener {

        private MediaPlayer mediaPlayer;
        private boolean looping;

        protected SoundInstance( Context context, @RawRes int resId, float volume, boolean loop){
            mediaPlayer = MediaPlayer.create(context, resId);
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.setLooping(loop);
            mediaPlayer.setOnCompletionListener(this);
            looping = loop;
        }

        public void start(){
            mediaPlayer.start();
        }

        public void stop(){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        public void pause(){
            mediaPlayer.pause();
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            // Not sure if this if statement is necessary - didn't bother to test it
            if(!looping){
                stop();
                        sounds.remove(this);
            }
        }
    }

}
