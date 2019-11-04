package com.example.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


/* The welcome screen when you open the app */
public class Intro extends Fragment {

    private FloatingTextAnimator textAnimator = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        // Starting music, if it's not already playing
        SoundManager soundManager = SoundManager.getInstance();
        soundManager.playMusic(getContext(), R.raw.soundtrack, 0.5f);

        // Intro Page: Start game
        view.findViewById(R.id.intro_page).setOnClickListener( (View v) -> {
            // Get player name
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.frag1, new EnterName())
                    .addToBackStack(null)
                    .commit();
        });

        // Score Board Button: Go to score board
        view.findViewById(R.id.btn_scoreboard).setOnClickListener((View v) -> {
            getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_upslow2, R.anim.slide_upslow,R.anim.slide_downslow2, R.anim.slide_downslow)
                .replace(R.id.frag1, new Scoreboard() )
                .addToBackStack(null)
                .commit();
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        /* If not placed here, the view dimensions won't have loaded yet, and it won't work. */
        textAnimator = new FloatingTextAnimator(getActivity(), getContext(), getView().findViewById(R.id.animation_container));
    }

    @Override
    public void onStop() {
        super.onStop();
        /* The animator runs in its own thread, and should be stopped
            if we're no longer in the fragment */
        textAnimator.stop();
    }
}
