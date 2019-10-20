package com.example.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Finished extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_finished, container, false);

        TextView resultText = view.findViewById(R.id.result_text);

        if(GalgeLogik.getInstance().erSpilletVundet()){
            String str = "You won!";
            resultText.setText( str );
            resultText.setTextColor(Color.rgb(80,255,80));
        }else{
            String str = "You lost!";
            resultText.setText( str );
            resultText.setTextColor(Color.rgb(255,80,80));
        }


        TextView correctWord = view.findViewById(R.id.correctword);

        correctWord.setText( GalgeLogik.getInstance().getOrdet() );

        view.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.fadein,R.anim.fadeout)
            .replace(R.id.frag1, new GameIntro())
            .addToBackStack(null)
            .commit();
    }
}