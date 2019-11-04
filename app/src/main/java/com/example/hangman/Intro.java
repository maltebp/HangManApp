package com.example.hangman;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/* The welcome screen when you open the app */
public class Intro extends Fragment implements View.OnClickListener {

    private FloatingTextAnimator textAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        view.findViewById(R.id.intro_page).setOnClickListener(this);

        view.findViewById(R.id.btn_scoreboard).setOnClickListener((View v) -> {
            getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_upslow2, R.anim.slide_upslow,R.anim.slide_downslow2, R.anim.slide_downslow)
                .replace(R.id.frag1, new Scoreboard())
                .addToBackStack(null)
                .commit();
        });

        // Starting music
        //TODO: READD MUSIC
        //SoundManager.getInstance().playMusic(getContext(), R.raw.soundtrack, 0.5f);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        textAnimator = new FloatingTextAnimator(getActivity(), getContext(), getView().findViewById(R.id.animation_container));
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {

    }


    @Override
    public void onClick(View v) {

        // Get player name
        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
            .replace(R.id.frag1, new EnterName())
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        textAnimator.stop();
    }
}
