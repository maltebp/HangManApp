package com.example.hangman.fragments.game;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hangman.R;
import com.example.hangman.gamelogic.GameState;


/**
 * Prompt the user for a name, and checks if the name is okay */
public class EnterName extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_entername, container, false);

        EditText input_name = view.findViewById(R.id.input_name);

        input_name.requestFocus();

        // Set on enter action
        input_name.setOnKeyListener((View v, int keyCode, KeyEvent event) ->{
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                // Enter pressed

                if(validateName()){
                    // Name okay.
                    GameState.getState().setPlayerName( ((EditText) v).getText().toString() );

                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frag1, new GameIntro())
                            .addToBackStack(null)
                            .commit();
                }
            }
            return false;
        });

        return view;
    }


    /**
     * Checks the name (string in R.id.input_name) for a series of conditions,
     * and updates the result text (R.id.entername_text_result) accordingly.
     * a
     * @return True if the name was okay, false if not
     */
    private boolean validateName(){
        TextView text_result = getView().findViewById(R.id.entername_text_result);
        String name = ((TextView) getView().findViewById(R.id.input_name)).getText().toString();

        // Check length
        if(name.length() < 2){
            text_result.setText("Name is too short");
            return false;
        }

        // Check each character for ascii vaue
        for(int i=0; i<name.length(); i++){
            char c = name.charAt(i);

            // Only large and small letters
            if( c < 97 && c > 90 || c > 122 || c < 65 ){
                text_result.setText( "Name contains wrong characters" );
                return false;
            }
        }

        return true;
    }
}
