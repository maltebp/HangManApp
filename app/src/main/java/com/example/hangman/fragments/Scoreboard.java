package com.example.hangman.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
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

        // Loading Scores
        IScoreDAO scoreDb = new ScoreDAO(getContext());
        List<Score> scores = scoreDb.getScores();

        // Displaying appropriate text depending on number of scores
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


    private class ScoreAdapter extends ArrayAdapter {

        private Context context;
        private List<Score> scores;
        private View currentExtendedInfo = null;

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

            TextView tv;

            // Rank
            tv = row.findViewById(R.id.text_rank);
            tv.setText("#"+(position+1));

            Score scoreData = scores.get(position);

            // Score
            tv = row.findViewById(R.id.text_score);
            tv.setText( String.valueOf(scoreData.getScore()));

            // Name
            tv = row.findViewById(R.id.text_name);
            tv.setText(scoreData.getName());

            // Word
            tv = row.findViewById(R.id.text_word);
            tv.setText( "Word: "+scoreData.getWord() );

            // Mistakes
            tv = row.findViewById(R.id.text_mistakes);
            tv.setText( "Mistakes: "+scoreData.getMistakes() );

            // Time
            tv = row.findViewById(R.id.text_time);
            tv.setText( "Time: "+timeText(scoreData.getTime()) );

            // Date
            tv = row.findViewById(R.id.text_date);
            tv.setText( "Date: "+dateText(scoreData.getDate()) );

            // Extended Score Setup
            row.findViewById(R.id.scoreboard_element_extended).setVisibility(View.GONE);
            row.setOnClickListener( (View view) -> {
                View extendedInfo = view.findViewById(R.id.scoreboard_element_extended);
                if( extendedInfo.getVisibility() == View.GONE ){
                    if( currentExtendedInfo != null )
                        currentExtendedInfo.setVisibility(View.GONE);
                    currentExtendedInfo = extendedInfo;
                    extendedInfo.setVisibility(View.VISIBLE);
                }else{
                    extendedInfo.setVisibility(View.GONE);
                }
            });
            return row;
        }


        private String timeText(int time){
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(2);
            return nf.format(time/60) + ":" + nf.format(time % 60);
        }


        private String dateText(Date date){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            return String.format("%d/%d-%d",
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
            );
        }
    }

}
