package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

// Opgave: https://docs.google.com/document/d/1YyJa7KwQISyCBJ7GEIDSohXK_XGcidgemQPpUj8xTHY/edit#heading=h.s08yi2d0eqo0

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] fragStatus = {0, 1};
    private boolean menuActive = false;
    private Fragment menuFrag = new BFrag();
    private Fragment fadeFrag = new BackgroundFade_Frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if( savedInstanceState == null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag1, new Intro())
                    .commit();
        }

        findViewById(R.id.btn_settings).setOnClickListener(this);
    }


    @Override
    public void onClick(View view){


        if(view.getId() == R.id.background_fade)
            hideMenu();
        else{
            if(menuActive){
                hideMenu();
            }else{
                // Animation guide: https://stackoverflow.com/questions/21026409/fragment-transaction-animation-slide-in-and-slide-out

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

            menuActive = !menuActive;
        }


    }

    private void hideMenu(){
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
    }
}
