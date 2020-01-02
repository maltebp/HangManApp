package com.example.hangman.database;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Date;
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
        Score currentScore = getScore(score.getName());

        if( currentScore == null || currentScore.getScore() < score.getScore() ){
            try{
                JSONObject json = new JSONObject();
                json.put("name", score.getName());
                json.put("score", score.getScore());
                json.put("word", score.getWord());
                json.put("mistakes", score.getMistakes());
                json.put("time", score.getTime());
                json.put("date", score.getDate().getTime());

                SharedPreferences highscores = context.getSharedPreferences(filename, MODE_PRIVATE);
                highscores.edit()
                        .putString(score.getName(), json.toString() )
                        .commit();
                /* Using commit rather than apply as its plausible that I'll
                need to fetch the newly updated scores immediatley after exiting this function */
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }


    /** Loads a single player's score */
    @Override
    public Score getScore(String name){
        SharedPreferences scores = context.getSharedPreferences(filename, MODE_PRIVATE);
        String scoreObj = scores.getString(name, "");
        if( scoreObj.equals("") ){
            return null;
        }
        try{
            JSONObject json = new JSONObject(scoreObj);
            return new Score(
                    json.getString("name"),
                    json.getInt("score"),
                    json.getString("word"),
                    json.getInt("mistakes"),
                    json.getInt("time"),
                    new Date(json.getLong("date")));
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Score> getScores() {
        SharedPreferences highscores = context.getSharedPreferences(filename, MODE_PRIVATE);
        Map<String, ?> entries = highscores.getAll();

        LinkedList<Score> scores = new LinkedList<>();

        for( String name : entries.keySet() ) {
            Score score = getScore(name);
            if(score != null){
                scores.add(score);
            }
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

    @Override
    public int getRank(String name){
        List<Score> scores = getScores();
        for(int i=0; i<scores.size(); i++){
            if( scores.get(i).getName().equals(name))
                return i+1;
        }
        return 0;
    }
}
