package com.example.hangman.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.SortedList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class ScoreDAO implements IScoreDAO {

    private final String filename = "highscores";
    private final Context context;


    public ScoreDAO(Context context){
        this.context = context;
    }

    @Override
    public void addScore(Score score){
        SharedPreferences highscores = context.getSharedPreferences(filename, MODE_PRIVATE);

        int currentScore = highscores.getInt(score.getName(), -1);

        if(score.getScore() > currentScore){
            highscores.edit()
                .putInt(score.getName(), score.getScore())
                .commit();
        }
        /* Using commit as its plausible that I'll
            need to fetch the newly updated scores immediatley */
    }


    @Override
    public List<Score> getScores() {
        SharedPreferences highscores = context.getSharedPreferences(filename, MODE_PRIVATE);
        Map<String, ?> entries = highscores.getAll();

        LinkedList<Score> scores = new LinkedList<>();

        for( Map.Entry<String, ?> entry : entries.entrySet() ){
            scores.add( new Score(entry.getKey(), Integer.parseInt(entry.getValue().toString())  ) );
        }

        // Sort in ascending order using comparable interface (implemented by Scores)
        Collections.sort(scores);

        // Reverse the order to descending (highest score first)
        Collections.reverse(scores);

        return scores;
    }


    @Override
    public void clearScores() {
        SharedPreferences highscores = context.getSharedPreferences(filename, MODE_PRIVATE);
        highscores.edit()
                .clear()
                .commit();
    }
}
