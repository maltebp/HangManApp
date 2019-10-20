package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class Settings extends Fragment implements Switch.OnCheckedChangeListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ((Switch) view.findViewById(R.id.settings_sounds)).setOnCheckedChangeListener(this);

        return view;
    }


    @Override
    public void onCheckedChanged(CompoundButton btn, boolean isChecked) {

        // Turn sound on/off
        if(btn == getView().findViewById(R.id.settings_sounds)){
            SoundManager.getInstance().setSoundsEnabled(isChecked);
        }
    }
}
