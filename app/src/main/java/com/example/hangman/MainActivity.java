package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.hangman.database.SettingsDAO;
import com.example.hangman.database.SettingsData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean settingsActive = false;
    private Fragment menuFrag = new Settings();
    private Fragment fadeFrag = new BackgroundFade_Frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Setting main page to intro fragment
        if( savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag1, new Intro())
                    .commit();
        }


        findViewById(R.id.btn_settings).setOnClickListener(this);

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

    }


    @Override
    public void onClick(View view) {
        if (settingsActive){
            onBackPressed();
        }else{
            settingsActive = true;
            // Open SettingsData window
            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .add(R.id.frag2, fadeFrag)
                .addToBackStack(null)
                .commit();

            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up)
                .add(R.id.frag2, menuFrag)
                .addToBackStack(null)
                .commit();
        }

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
