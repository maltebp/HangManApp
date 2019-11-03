package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hangman.database.IScoreDAO;
import com.example.hangman.database.Score;
import com.example.hangman.database.ScoreDAO;

import java.util.List;

public class Scoreboard extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        StringBuilder output = new StringBuilder();

        output.append("\n\nHIGHSCORE TEST\n");

        IScoreDAO scoreDb = new ScoreDAO(getContext());


        output.append("Before clearing:\n");
        output.append(printScores(scoreDb.getScores())+"\n");

        output.append("\nClearing...\n ");
        scoreDb.clearScores();

        output.append("After clearing:\n");
        output.append(printScores(scoreDb.getScores() )+"\n");

        Score score;

        score = new Score("User1", 1);
        output.append("\nAdding: "+score+"\n");
        scoreDb.addScore(score);
        output.append("New scores: \n");
        output.append(printScores(scoreDb.getScores())+"\n");

        score = new Score("User2", 2);
        output.append("\nAdding: "+score+"\n");
        scoreDb.addScore(score);
        output.append("New scores: \n");
        output.append(printScores(scoreDb.getScores())+"\n");

        score = new Score("User3", 3);
        output.append("\nAdding: "+score+"\n");
        scoreDb.addScore(score);
        output.append("New scores: \n");
        output.append(printScores(scoreDb.getScores())+"\n");

        score = new Score("User1", 4);
        output.append("\nAdding: "+score+"\n");
        scoreDb.addScore(score);
        output.append("New scores: \n");
        output.append(printScores(scoreDb.getScores())+"\n");

        System.out.println(output.toString());
    }

    private String printScores(List<Score> scores){
        StringBuilder str = new StringBuilder();
        str.append("{ ");
        boolean first = true;
        for(Score score : scores){
            if(first)
                first = false;
            else
                str.append(", ");
            str.append(score);
        }
        str.append(" }");
        return str.toString();
    }
}
