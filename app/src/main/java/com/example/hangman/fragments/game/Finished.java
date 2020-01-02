package com.example.hangman.fragments.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hangman.R;
import com.example.hangman.SoundManager;
import com.example.hangman.database.IScoreDAO;
import com.example.hangman.database.Score;
import com.example.hangman.database.ScoreDAO;
import com.example.hangman.gamelogic.GameState;
import com.github.jinatonic.confetti.CommonConfetti;

import java.util.Date;


/* Displays the result of the game (win / loss) */
public class Finished extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view;
        GameState gameState = GameState.getState();

        String word = gameState.getOrdet();
        int mistakes = gameState.getAntalForkerteBogstaver();
        int time = gameState.getTime();
        String playerName = gameState.getPlayerName();


        if( gameState.erSpilletVundet() ){
            view = inflater.inflate(R.layout.fragment_won, container, false);

            // Updating score
            int score = calculateScore(word, gameState.getTime(), mistakes);
            IScoreDAO scoreDAO = new ScoreDAO(getContext());
            scoreDAO.addScore(new Score( playerName, score, word, mistakes, time, new Date(System.currentTimeMillis()) ));

            // Setting summary text
            TextView text_summary = view.findViewById(R.id.text_summary);
            text_summary.setText(buildSummaryString(mistakes, time));

            // Setting score
            TextView text_mistakes = view.findViewById(R.id.text_score);
            text_mistakes.setText( String.valueOf(score) );

            // Setting rank
            TextView text_rank = view.findViewById(R.id.text_rank);
            text_rank.setText( "#"+scoreDAO.getRank(gameState.getPlayerName()));

            SoundManager.getInstance().playSound(this.getContext(), R.raw.snd_victory);
            CommonConfetti.rainingConfetti(container, new int[] {R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorGrey} ).oneShot();
        }else {
            view = inflater.inflate(R.layout.fragment_lost, container, false);

            /* When a game has finished, and the player decides to continue,
                a new "series" of GameIntro and Game fragments are created.
                When pressing "back", the >entire< stack is popped, meaning
                all previous "Finished" fragments as well. When "returning"
                to these fragments, this method is run as well, and the game
                hasn't finished at that point. Thus it will play the "lost"
                melody.
                This if statement is a cheap, not pretty solution for this
                problem..*/
            if(gameState.erSpilletSlut()){
                // Update the 'correct word'-text with the correct word
                ((TextView) view.findViewById(R.id.correctword)).setText(gameState.getOrdet());
                SoundManager.getInstance().playSound(this.getContext(), R.raw.snd_lost);
            }
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


    private int calculateScore(String word, int time, int mistakes){
        double lengthFactor = word.length() * 100;
        double timeFactor = 1 / (1 + time/50.);
        double mistakeFactor = (7.-mistakes)/7.;

        int score = (int) (lengthFactor * timeFactor * mistakeFactor);
        return score > 0 ? score : 0;
    }

    private String buildSummaryString(int mistakes, int time){
        String summary = "in ";
        int minutes = time / 60;
        int seconds = time % 60;

        if(minutes > 0){
            summary += minutes + " minute" + (minutes>1 ? "s" : "") + " and ";
        }
        if(seconds > 0){
            summary += seconds + " second" + (seconds>1 ? "s" : "");
        }

        summary += ", with "+mistakes + (mistakes>1 ? " mistakes"  : " mistake")+", and your total score is: ";

        return summary;
    }
}
