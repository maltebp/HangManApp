package com.example.hangman;

import android.os.AsyncTask;

/**
 * A simplification of the AsyncTask for this tiny
 * purpose that I need it. */
public class SimpleTask extends AsyncTask<Void,Void,Void> {

    private Runnable runnable;

    public SimpleTask(Runnable runnable){
        this.runnable = runnable;
        execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        runnable.run();
        return null;
    }
}
