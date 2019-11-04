package com.example.hangman.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangman.R;

/* A fade effect for the settings fragment */
public class BackgroundFade_Frag extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_background_fade_, container, false);

        view.setOnClickListener((View v) -> {
            getActivity().onBackPressed();
        });

        return view;
    }
}
