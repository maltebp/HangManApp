package com.example.hangman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Game extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        view.findViewById(R.id.galge).setOnClickListener(this);

        TableLayout table = view.findViewById(R.id.buttons);

        TableRow row = null;
        for(int i=0; i<26; i++){
            if(i % 6 == 0){
                row = new TableRow(getContext());
                table.addView(row);
            }

            row.addView(createLetterButton( (char) (97+i) ));
        }

        row.addView(createLetterButton( 'æ' ));
        row.addView(createLetterButton( 'ø' ));
        row.addView(createLetterButton( 'å' ));

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        updateWord();
    }


    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
        btn.setEnabled(false);

        GalgeLogik logic = GalgeLogik.getInstance();

        logic.gætBogstav(btn.getText().toString());

        if(logic.erSidsteBogstavKorrekt()){
            btn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.col_button_correct));
            SoundManager.getInstance().playSound(getContext(), R.raw.snd_correct);
        }else{
            btn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.col_button_wrong));
            SoundManager.getInstance().playSound(getContext(), R.raw.snd_wrong);
        }

        updateWord();

        int imageSrc = 0;

        switch(logic.getAntalForkerteBogstaver()){
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

        if(logic.erSpilletSlut()){
            getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein,R.anim.fadeout)
                .replace(R.id.frag1, new Finished())
                .commit();
        }
    }

    private Button createLetterButton(char letter){
        Button btn = new Button(getContext());

        btn.setLayoutParams(new TableRow.LayoutParams( 160, TableRow.LayoutParams.WRAP_CONTENT));

        String str = "" + letter;
        btn.setText( str );

        btn.setOnClickListener(this);

        return btn;
    }


    private void updateWord(){
        TextView txt = getView().findViewById(R.id.word);
        txt.setText(GalgeLogik.getInstance().getSynligtOrd());
    }


}
