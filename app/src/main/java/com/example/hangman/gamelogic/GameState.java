package com.example.hangman.gamelogic;


import java.util.List;

/**
 * Extension of GalgeLogik which allows me to extend or change
 * some of the functionality, to fit the needs of this program.
 */
public class GameState extends GalgeLogik {

    // Singleton Logic ========
    private static GameState instance = null;

    private GameState(){}

    public static GameState getState(){
        if(instance == null) instance = new GameState();
        return instance;
    }
    // ========================


    private String playerName = "";

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getPossibleWords(){
        return muligeOrd;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
