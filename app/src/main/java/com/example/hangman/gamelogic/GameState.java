package com.example.hangman.gamelogic;


import android.os.AsyncTask;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

/**
 * Extension of GalgeLogik which allows me to extend or change
 * some of the functionality, to fit the needs of this program.
 * This is implemented as a Singleton.
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

    private TimerTask timer = null;

    public void startTimer(TextView timerText){
        timer = new TimerTask(timerText);
    }

    public int getTime(){
        return timer.getTime();
    }

    public boolean guessLetter(String letter){
        gætBogstav(letter);
        if(erSpilletSlut()){
            if( timer != null && !timer.isCancelled()){
                timer.cancel(true);
            }
        }
        return erSidsteBogstavKorrekt();
    }

    public void stopGame(){
        if( timer != null && !timer.isCancelled() ) {
            timer.cancel(true);
        }
    }

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

    static class TimerTask extends AsyncTask {

        private int currentTime = 0;
        private TextView timerText;

        TimerTask(TextView timerText){
            this.timerText = timerText;
            timerText.setText("00:00");
            execute();
        }

        int getTime(){
            return currentTime;
        }

        @Override
        protected Void doInBackground(Object ... obj) {
            try {
                do{
                    Thread.sleep(1000);
                    currentTime += 1;
                    publishProgress();
                }while(true);
            } catch (InterruptedException e) {}
            return null;
        }

        @Override
        protected void onProgressUpdate(Object ... obj) {
            int seconds = currentTime % 60;
            int minutes = currentTime / 60;
            NumberFormat format = NumberFormat.getInstance();
            format.setMinimumIntegerDigits(2);
            timerText.setText(  format.format(minutes) +":" + format.format(seconds) );
        }
    }
}
