package com.example.hangman.database;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SettingsDAO{

    private final String filename = "settings";
    private final Context context;

    public SettingsDAO(Context context){
        this.context = context;
    }

    public void saveSettings(SettingsData settings){
        SharedPreferences settingsPref = context.getSharedPreferences(filename, MODE_PRIVATE);

        settingsPref
                .edit()
                .putBoolean("music", settings.isMusicEnabled())
                .putBoolean("sound", settings.isSoundEnabled())
                .apply();
    }

    public SettingsData loadSettings(){
        SharedPreferences settingsPref = context.getSharedPreferences(filename, MODE_PRIVATE);

        SettingsData settings = new SettingsData();

        settings.toggleMusic(settingsPref.getBoolean("music", true));
        settings.toggleSound(settingsPref.getBoolean("sound", true));

        return settings;
    }
}