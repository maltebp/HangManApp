package com.example.hangman.database;

import androidx.annotation.NonNull;

import java.util.Date;

public class Score implements Comparable {

    private final String name;
    private final int score;
    private final String word;
    private final int mistakes;
    private final int time;
    private final Date date;

    public Score(String name, int score, String word, int mistakes, int time, Date date) {
        this.name = name;
        this.score = score;
        this.word = word;
        this.mistakes = mistakes;
        this.time = time;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    public int getMistakes() {
        return mistakes;
    }

    public Date getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"+name+","+score+"}";
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return score - ((Score) o).getScore();
    }
}
