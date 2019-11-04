package com.example.hangman.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hangman.R;
import com.example.hangman.database.IScoreDAO;
import com.example.hangman.database.Score;
import com.example.hangman.database.ScoreDAO;

import java.util.List;

public class Scoreboard extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        IScoreDAO scoreDb = new ScoreDAO(getContext());

        List<Score> scores = scoreDb.getScores();

        ListView list_highscores = view.findViewById(R.id.list_highscores);
        TextView text_noscores = view.findViewById(R.id.text_noscores);
        if(scores.size() == 0){
            text_noscores.setVisibility(View.VISIBLE);
            list_highscores.setVisibility(View.GONE);
        }else{
            text_noscores.setVisibility(View.GONE);
            list_highscores.setVisibility(View.VISIBLE);
        }

        list_highscores.setAdapter( new ScoreAdapter(getContext(), scores));

    }


    private class ScoreAdapter extends ArrayAdapter{

        private Context context;
        private List<Score> scores;

        public ScoreAdapter(@NonNull Context context, List<Score> scores) {
            super(context, -1, scores);
            this.context = context;
            this.scores = scores;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row;
            if( convertView != null){
                row = convertView;
            }else{
                row = inflater.inflate(R.layout.scoreboard_element, parent, false);
            }

            TextView text_rank = row.findViewById(R.id.text_rank);
            text_rank.setText("#"+(position+1));

            TextView text_score = row.findViewById(R.id.text_score);
            text_score.setText( ""+scores.get(position).getScore() );

            TextView text_name = row.findViewById(R.id.text_name);
            text_name.setText(scores.get(position).getName());

            return row;
        }
    }

}
