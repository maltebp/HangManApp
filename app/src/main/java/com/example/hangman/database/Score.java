package com.example.hangman.database;

import androidx.annotation.NonNull;

public class Score implements Comparable {

    private final String name;
    private final int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
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
