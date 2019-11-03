package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static java.lang.Thread.sleep;


/* Starts the game, displaying the number of letters in the word to be guessed */
public class GameIntro extends Fragment implements View.OnClickListener {

    private boolean hasContinued = false;
    private GameState gameState;

    public GameIntro(GameState state){
        this.gameState = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_game_intro, container, false);

        // The Game gameState is reset every time we pass the game intro
        GalgeLogik logic = GalgeLogik.getInstance();
        logic.nulstil();

        ((TextView) view.findViewById(R.id.gameintro_word)).setText(logic.getSynligtOrd());
        view.findViewById(R.id.gameintro_page).setOnClickListener(this);

        ((TextView) view.findViewById(R.id.gameintro_text_name)).setText(gameState.getPlayerName());

        SoundManager.getInstance().clearMusic();

        /* Thread which will continue to the game screen, if the user doesn't tap
            with the sleep duration */
        hasContinued = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(2500);
                    onClick(view);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();


        return view;
    }


    @Override
    public void onClick(View v) {
        if(!hasContinued){
            // Start the game


            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.frag1, new Game())
                    .addToBackStack(null)
                    .commit();
            hasContinued = true;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        hasContinued = true;
    }
}
