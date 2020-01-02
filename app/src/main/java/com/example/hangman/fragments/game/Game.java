package com.example.hangman.fragments.game;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.hangman.R;
import com.example.hangman.SoundManager;
import com.example.hangman.gamelogic.GameState;


/* The actual game screen */
public class Game extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        view.findViewById(R.id.galge).setOnClickListener(this);

        // Get screen size
        DisplayMetrics screenSize = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(screenSize);

        // Adjust button width according to screen size
        int buttonsPerRow = 6;
        int buttonWidth = (screenSize.widthPixels-50)/buttonsPerRow;

        // Create the "keyboard"
        TableLayout table = view.findViewById(R.id.buttons);
        TableRow row = null;
        for(int i=0; i<26; i++){
            if(i % buttonsPerRow == 0){
                row = new TableRow(getContext());
                table.addView(row);
            }
            row.addView(createLetterButton( (char) (97+i), buttonWidth));
        }

        // Adds danish letters to keyboard
        row.addView(createLetterButton( 'æ', buttonWidth ));
        row.addView(createLetterButton( 'ø', buttonWidth ));
        row.addView(createLetterButton( 'å', buttonWidth ));

        return view;
    }


    @Override
    public void onStart(){
        super.onStart();
        updateWord();
        // Starting game timer
        GameState.getState().startTimer(getView().findViewById(R.id.timer));
    }


    @Override
    public void onClick(View view) {

        // Disable the clicked button
        Button btn = (Button) view;
        btn.setEnabled(false);

        GameState gameState = GameState.getState();


        // Make a guess and update button according to result of guess
        if(gameState.guessLetter(btn.getText().toString())){
            btn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.col_button_correct));
            SoundManager.getInstance().playSound(getContext(), R.raw.snd_correct,0.9f);
        }else{
            btn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.col_button_wrong));
            SoundManager.getInstance().playSound(getContext(), R.raw.snd_wrong);
        }

        updateWord();

        // Update the hanged man picture
        int imageSrc = 0;
        switch(gameState.getAntalForkerteBogstaver()){
            case 0:
                imageSrc = R.drawable.galge; break;
            case 1:
                imageSrc = R.drawable.forkert1; break;
            case 2:
                imageSrc = R.drawable.forkert2; break;
            case 3:
                imageSrc = R.drawable.forkert3; break;
            case 4:
                imageSrc = R.drawable.forkert4; break;
            case 5:
                imageSrc = R.drawable.forkert5; break;
            case 6:
                imageSrc = R.drawable.forkert6; break;
        }
        ((ImageView)getView().findViewById(R.id.galge)).setImageResource(imageSrc);

        if(gameState.erSpilletSlut()){

            // Go to Finished screen
            getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout )
                .replace(R.id.frag1, new Finished())
                .addToBackStack(null)
                .commit();
        }
    }

    /* Generic method to create letter buttons (A-Å) */
    private Button createLetterButton(char letter, int width){
        Button btn = new Button(getContext());

        /* Since the button is a vector graphic, setting the background will make it look squared.
            Instead we change the tint. */
        btn.setLayoutParams(new TableRow.LayoutParams( width, TableRow.LayoutParams.WRAP_CONTENT));


        String str = "" + letter;
        btn.setText( str );

        btn.setOnClickListener(this);
        return btn;
    }


    /* Updates the displayed word to match the current word in the game logic*/
    private void updateWord(){
        TextView txt = getView().findViewById(R.id.word);
        txt.setText(GameState.getState().getSynligtOrd());
    }

    @Override
    public void onStop(){
        super.onStop();
        GameState.getState().stopGame();
    }
}
