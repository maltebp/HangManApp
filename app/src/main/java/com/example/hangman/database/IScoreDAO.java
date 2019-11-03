package com.example.hangman.database;

import java.util.List;

public interface IScoreDAO {

    void addScore(Score score);
    List<Score> getScores();
    void clearScores();
    int getRank(String name);
}
