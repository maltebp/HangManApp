package com.example.hangman;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static java.lang.Thread.sleep;


/* Starts the game, displaying the number of letters in the word to be guessed */
public class EnterName extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_entername, container, false);

        EditText input_name = view.findViewById(R.id.input_name);

        input_name.setOnKeyListener((View v, int keyCode, KeyEvent event) ->{

            if(event.getAction() == KeyEvent.ACTION_DOWN){
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    GameState gameState = new GameState();
                    gameState.setPlayerName( ((EditText) v).getText().toString() );

                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frag1, new GameIntro(gameState))
                            .addToBackStack(null)
                            .commit();
                }
            }

            return false;
        });

        return view;
    }
}
