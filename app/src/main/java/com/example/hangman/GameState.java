package com.example.hangman;

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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
