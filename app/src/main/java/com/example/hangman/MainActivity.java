package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean settingsActive = false;
    private Fragment menuFrag = new Settings();
    private Fragment fadeFrag = new BackgroundFade_Frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Settings main page to intro fragment
        /*if( savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag1, new Intro())
                    .commit();
        }

        findViewById(R.id.btn_settings).setOnClickListener(this);*/

        if( savedInstanceState == null ){
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag1, new Scoreboard())
                .commit();
        }
    }


    @Override
    public void onClick(View view){

        if(settingsActive){
            // Close Settings window

            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_up, R.anim.slide_up)
                .remove(menuFrag)
                .commit();


            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fadeout, R.anim.fadeout)
                .remove(fadeFrag)
                .commit();

        }else{
            // Open Settings window
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadein)
                    .add(R.id.frag2, fadeFrag)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_down)
                    .add(R.id.frag2, menuFrag)
                    .commit();
        }

        settingsActive = !settingsActive;
    }

    @Override
    public void onBackPressed(){
        getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
            .replace(R.id.frag1, new Intro())
            .commit();
    }
}
