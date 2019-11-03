package com.example.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/* Displays the result of the game (win / loss) */
public class Finished extends Fragment implements View.OnClickListener {

    private String correctWord;
    private int mistakes;
    private boolean won;

    public Finished(boolean won, String correctWord, int mistakes){
        this.correctWord = correctWord;
        this.mistakes = mistakes;
        this.won = won;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_finished, container, false);

        TextView text_result = view.findViewById(R.id.result_text);
        TextView text_mistakes = view.findViewById(R.id.text_mistakes);

        // Update the winning/losing text with text and color
        if(won){
            String str = "You won!";
            text_result.setText( str );
            text_result.setTextColor(Color.rgb(80,255,80));
            text_mistakes.setText( "... with "+mistakes+" mistakes");

        }else{
            String str = "You lost!";
            text_result.setText( str );
            text_result.setTextColor(Color.rgb(255,80,80));
            text_mistakes.setVisibility(View.INVISIBLE);
        }

        // Update the 'correct word'-text with the correct word
        ((TextView) view.findViewById(R.id.correctword)).setText(correctWord);

        view.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        // Restart the game
        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.fadein,R.anim.fadeout)
            .replace(R.id.frag1, new GameIntro())
            .addToBackStack(null)
            .commit();
    }
}
