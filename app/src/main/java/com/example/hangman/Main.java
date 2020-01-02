package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hangman.database.SettingsDAO;
import com.example.hangman.database.SettingsData;
import com.example.hangman.fragments.intro.Intro;
import com.example.hangman.fragments.settings.BackgroundFade_Frag;
import com.example.hangman.fragments.settings.Settings;
import com.example.hangman.gamelogic.GameState;


/**
 * The only activity of the entire app, and everything starts from here.
 * Contains the fragment containers (settings and screen).
 */
public class Main extends AppCompatActivity {

    private boolean settingsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding on click listener to settings button
        findViewById(R.id.btn_settings).setOnClickListener( (View v) -> {
            if (settingsActive){
                onBackPressed();
            }else{
                settingsActive = true;

                // Open Settings window
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .add(R.id.frag2, new BackgroundFade_Frag())
                        .addToBackStack(null)
                        .commit();

                // Show background fade (partly transparent screen).
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up)
                        .add(R.id.frag2, new Settings())
                        .addToBackStack(null)
                        .commit();
            }
        });


        // Load settings data from preferences into the SoundManager
        SettingsData settingsData = new SettingsDAO(getBaseContext()).loadSettings();
        SoundManager.getInstance().toggleMusic(settingsData.isMusicEnabled());
        SoundManager.getInstance().toggleSound(settingsData.isSoundEnabled());


        /* Load extra words from DR. It's not inteferring with any UI,
            so there is no problem in just running it on a regular thread. */
        new Thread( () ->{
            try{
                GameState.getState().hentOrdFraDr();
            }catch(Exception e){
                System.out.println("Der skete en fejl, da vi hentede ord fra DR: ");
                e.printStackTrace();
            }
        }).start();


        // Setting page to intro fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag1, new Intro())
                .commit();
    }


    @Override
    public void onBackPressed() {
        if(settingsActive){
            settingsActive = false;

             /* If settings are open, we have 2 fragments to pop:
                1) the settings window, and 2) the background fade */
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().popBackStack();

        }else{
            /* Pop all fragments to go all the way back to the intro screen.
               Note: the intro screen is not added to the back stack, so it
               won't "pop" that.*/
            while(getSupportFragmentManager().getBackStackEntryCount()>0){
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }


    /**
     * When app is minimized we turn off the music */
    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.getInstance().clearMusic();
    }


    /**
     * When app is opened again, we resume the music */
    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.getInstance().playMusic(getBaseContext(), R.raw.soundtrack, 0.5f);
    }
}