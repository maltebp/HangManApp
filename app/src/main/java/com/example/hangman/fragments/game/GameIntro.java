package com.example.hangman.fragments.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hangman.R;
import com.example.hangman.SoundManager;
import com.example.hangman.gamelogic.GameState;

import static java.lang.Thread.sleep;


/* Starts the game, displaying the number of letters in the word to be guessed */
public class GameIntro extends Fragment implements View.OnClickListener {

    private boolean hasContinued = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_game_intro, container, false);

        // The Game gameState is reset every time we pass the game intro
        GameState gameState = GameState.getState();
        gameState.nulstil();

        ((TextView) view.findViewById(R.id.gameintro_word)).setText(gameState.getSynligtOrd());
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
