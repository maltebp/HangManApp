package com.example.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hangman.database.IScoreDAO;
import com.example.hangman.database.Score;
import com.example.hangman.database.ScoreDAO;


/* Displays the result of the game (win / loss) */
public class Finished extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view;
        GameState gameState = GameState.getState();

        if( gameState.erSpilletVundet() ){
            view = inflater.inflate(R.layout.fragment_won, container, false);

            // Updating score
            int mistakes = gameState.getAntalForkerteBogstaver();
            IScoreDAO scoreDAO = new ScoreDAO(getContext());
            scoreDAO.addScore(new Score(gameState.getPlayerName(), 7-mistakes));

            // Setting rank
            TextView text_rank = view.findViewById(R.id.won_text_rank);
            text_rank.setText( "#"+scoreDAO.getRank(gameState.getPlayerName()));

            // SettingsData mistakes
            TextView text_mistakes = view.findViewById(R.id.text_mistakes);
            text_mistakes.setText("... with "+gameState.getAntalForkerteBogstaver()+" mistakes");

        }else {
            view = inflater.inflate(R.layout.fragment_lost, container, false);
            // Update the 'correct word'-text with the correct word
            ((TextView) view.findViewById(R.id.correctword)).setText(gameState.getOrdet());
        }

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
