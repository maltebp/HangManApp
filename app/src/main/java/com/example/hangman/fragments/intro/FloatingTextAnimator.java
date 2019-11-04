package com.example.hangman.fragments.intro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hangman.gamelogic.GameState;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 *  Class to display and simulate floating text within
 *  a given layout, by extending the AsyncTask.
 *
 *  It uses the words available from the game logic.
 */
public class FloatingTextAnimator extends AsyncTask<Void,Void,Void> {

    private static final int NUM_TEXTS = 8;
    private static final int FRAME_FREQ = 16; // Milliseconds = ~60 FPS

    private int height;
    private int width;

    private Random rand = new Random();

    private boolean run = true;

    private LinkedList<FloatingText> texts = new LinkedList<>();


    public FloatingTextAnimator(Context context, FrameLayout container){

        // Post ensures that the dimensions have been calculating before we create the text objects.
        container.post(() -> {
            height = container.getHeight();
            width = container.getWidth();

            // Create text objects
            for(int i=0; i<NUM_TEXTS; i++){
                FloatingText text = new FloatingText(context, container);
                generateText(text);
                texts.add(text);
            }

            execute();
        });
    }

    /* Stops the simulation/rendering loop */
    public void stop(){
        run = false;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        // Do loop on seperate thread
        while(run){
            // Publish to update views on main thread
            publishProgress();
            try{
                Thread.sleep(FRAME_FREQ);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Void ... values) {
        // Update views on main thread
        for(FloatingText text : texts ){
            if( 0 > text.adjustDelay(FRAME_FREQ) ){
                text.update();
                if( text.getPosY() < 0 ){
                    generateText(text);
                }
            }
        }
    }


    /* Randoly generates a new text, including x-coordinate, size, speed etc. */
    public void generateText(FloatingText text){

        List<String> words = GameState.getState().getPossibleWords();

        /* There is a slight chance that words are being loaded from DR in this instant,
            and therefor the list might be empty. In that case, just reuse the old world.*/
        if(words.size() > 0 ){
            text.setText( words.get( rand.nextInt(words.size())) );
        }

        // X position
        text.setPosition((rand.nextFloat()*(width+100))-200, height);

        // Delay before it appears on the screen
        text.setDelay( rand.nextLong()%8000 );

        float speed = (rand.nextFloat()*4)+3;
        text.setSpeed( -speed );

        /* Transparency speed: how fast it goes fades away */
        float transparency = 40;
        float transparencySpeed =  transparency / (height / speed);
        text.setTransparencySpeed( -transparencySpeed );
        text.setTransparency(transparency);

        text.setSize(  (rand.nextFloat()*10) + 20);

        text.update();
    }


    /**
     * Represents a single floating text. To place the text using absolute
     * coordinates, the following is done for each text:
     *
     *  - Place a FrameLayout within the parent view, matching its size
     *
     */
    private class FloatingText{

        private float speed;
        private float x;
        private float y;
        private long delay;

        private TextView textView;
        private float transparency = 40;
        private float transparencySpeed;

        public FloatingText(Context context, FrameLayout container){

            textView = new TextView(context);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setMaxLines(1);

            container.addView(textView);
        }

        public void setPosition(float x, float y){
            this.x = x;
            this.y = y;
            update();
        }

        public void setSpeed(float speed){
            this.speed = speed;
        }

        public void setTransparencySpeed(float speed){
            this.transparencySpeed = speed;
        }

        public void setTransparency(float transparency){
            this.transparency = transparency;
        }

        public void setDelay(long delay){
            this.delay = delay;
        }

        public float adjustDelay(long amount){
            delay -= amount;
            return delay;
        }


        private void update(){

            // Position: Adjusting margin using params, to give the TextView absolute coordinates within its parent
            y += speed;

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            params.leftMargin = (int) x;
            params.topMargin = (int) y;
            textView.setLayoutParams(params);


            // Transparency: decrement integer transparancy and convert it to a color
            transparency += transparencySpeed;
            if(transparency < 0) transparency = 0;

            String transparencyHex = Integer.toHexString( (int) transparency );
            if(transparencyHex.length() == 1) transparencyHex = "0"+transparencyHex;
            textView.setTextColor( Color.parseColor("#"+transparencyHex+"000000") );
        }

        public void setText(String text){
            textView.setText(text);
        }

        public void setSize(float size){
            textView.setTextSize(size);
        }

        public float getPosY() {
            return y;
        }
    }
}
