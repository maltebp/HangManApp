package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class Settings extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Using lambdas as there is no common code in the listeners
        ((Switch) view.findViewById(R.id.settings_sounds)).setOnCheckedChangeListener( (CompoundButton btn, boolean isChecked) ->{
            SoundManager.getInstance().toggleSound(isChecked) ;
        });

        ((Switch) view.findViewById(R.id.settings_music)).setOnCheckedChangeListener( (CompoundButton btn, boolean isChecked) ->{
            SoundManager.getInstance().toggleMusic(isChecked) ;
        });

        return view;
    }
}
