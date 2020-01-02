package com.example.hangman

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.example.hangman.database.ScoreDAO

import com.example.hangman.database.SettingsDAO
import com.example.hangman.fragments.intro.Intro
import com.example.hangman.fragments.settings.BackgroundFade_Frag
import com.example.hangman.fragments.settings.Settings
import com.example.hangman.gamelogic.GameState


/**
 * The only activity of the entire app, and everything starts from here.
 * Contains the fragment containers (settings and screen).
 */
class Main : AppCompatActivity() {

    private var settingsActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adding on click listener to settings button
        findViewById<View>(R.id.btn_settings).setOnClickListener { v: View ->
            if (settingsActive) {
                onBackPressed()
            } else {
                settingsActive = true

                // Open Settings window
                supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .add(R.id.frag2, BackgroundFade_Frag())
                        .addToBackStack(null)
                        .commit()

                // Show background fade (partly transparent screen).
                supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up)
                        .add(R.id.frag2, Settings())
                        .addToBackStack(null)
                        .commit()
            }
        }


        // Load settings data from preferences into the SoundManager
        val settingsData = SettingsDAO(baseContext).loadSettings()
        SoundManager.getInstance().toggleMusic(settingsData.isMusicEnabled)
        SoundManager.getInstance().toggleSound(settingsData.isSoundEnabled)


       /* *//* Load extra words from DR. It's not inteferring with any UI,
            so there is no problem in just running it on a regular thread. *//*
        Thread {
            try {
                GameState.getState().hentOrdFraDr()
            } catch (e: Exception) {
                println("Der skete en fejl, da vi hentede ord fra DR: ")
                e.printStackTrace()
            }
        }.start()*/


        // Setting page to intro fragment
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frag1, Intro())
                .commit()
    }


    override fun onBackPressed() {
        if (settingsActive) {
            settingsActive = false

            /* If settings are open, we have 2 fragments to pop:
                1) the settings window, and 2) the background fade */
            supportFragmentManager.popBackStack()
            supportFragmentManager.popBackStack()

        } else {
            /* Pop all fragments to go all the way back to the intro screen.
               Note: the intro screen is not added to the back stack, so it
               won't "pop" that.*/
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
        }
    }


    /**
     * When app is minimized we turn off the music  */
    override fun onPause() {
        super.onPause()
        SoundManager.getInstance().clearMusic()
    }


    /**
     * When app is opened again, we resume the music  */
    override fun onResume() {
        super.onResume()
        SoundManager.getInstance().playMusic(baseContext, R.raw.soundtrack, 0.5f)
    }
}
