package com.example.hangman.database;

public class SettingsData {
    private boolean music;
    private boolean sound;

    public boolean isMusicEnabled() {
        return music;
    }

    public void toggleMusic(boolean music) {
        this.music = music;
    }

    public boolean isSoundEnabled() {
        return sound;
    }

    public void toggleSound(boolean sound) {
        this.sound = sound;
    }
}
