package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.hangman.database.SettingsDAO;
import com.example.hangman.database.SettingsData;

public class Settings extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SettingsData settings = new SettingsDAO(getContext()).loadSettings();

        // Toggle sounds
        Switch soundSwitch = view.findViewById(R.id.settings_sounds);
        soundSwitch.setChecked(settings.isSoundEnabled());
        soundSwitch.setOnCheckedChangeListener( (CompoundButton btn, boolean isChecked) ->{
            SoundManager.getInstance().toggleSound(isChecked);
            saveSettings();
        });

        // Toggle music
        Switch musicSwitch = view.findViewById(R.id.settings_music);
        musicSwitch.setChecked(settings.isMusicEnabled());
        musicSwitch.setOnCheckedChangeListener( (CompoundButton btn, boolean isChecked) ->{
            SoundManager.getInstance().toggleMusic(isChecked) ;
            saveSettings();
        });

        return view;
    }

    private void saveSettings(){
        // Store newly adjusted settings to preferences
        SettingsData settings = new SettingsData();
        settings.toggleMusic( SoundManager.getInstance().isMusicEnabled() );
        settings.toggleSound( SoundManager.getInstance().isSoundEnabled() );
        new SettingsDAO(getContext()).saveSettings(settings);
    }
}
