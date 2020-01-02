package com.example.hangman.database;

import java.util.List;

public interface IScoreDAO {

    void addScore(Score score);
    List<Score> getScores();
    void clearScores();
    Score getScore(String name);
    int getRank(String name);
}
