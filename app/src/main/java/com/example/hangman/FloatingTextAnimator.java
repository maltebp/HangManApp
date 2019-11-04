package com.example.hangman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FloatingTextAnimator extends AsyncTask<Void,Void,Void> {

    private static final int NUM_TEXTS = 8;
    private static final int FRAME_FREQ = 16; // Milliseconds

    private Context context;
    private FrameLayout parent;
    private int height;
    private int width;

    private Random rand = new Random();

    private boolean run = true;

    private LinkedList<FloatingText> texts = new LinkedList<>();

    public FloatingTextAnimator(Activity activity, Context context, FrameLayout parent){

        System.out.println("Creating ");

        parent.post(() -> {
            this.context = context;
            this.parent = parent;

            System.out.println("Setting height/width: "+parent.getHeight() + ", "+parent.getWidth());

            ViewGroup.LayoutParams params = parent.getLayoutParams();
            height = parent.getHeight();    
            width = parent.getWidth();

            for(int i=0; i<NUM_TEXTS; i++){
                FloatingText text = new FloatingText(activity, context, parent);
                generateText(text);
                texts.add(text);
            }

            execute();
        });
    }


    public void stop(){
        run = false;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        while(run){
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
        for(FloatingText text : texts ){
            if( 0 > text.adjustDelay(FRAME_FREQ) ){
                if( text.move() < 0 ){
                    generateText(text);
                }
            }
        }
    }

    public void generateText(FloatingText text){

        List<String> words = GameState.getState().muligeOrd;

        /* There is a slight chance that words are being loaded from DR in this instant,
            and therefor the list might be empty. In that case, just reuse the old world.*/
        if(words.size() > 0 ){
            text.setText( words.get( rand.nextInt(words.size())) );
        }

        text.setPosition((rand.nextFloat()*(width+100))-200, height);

        text.setDelay( rand.nextLong()%8000 );


        float speed = (rand.nextFloat()*4)+3;

        text.setSpeed( -speed );

        float transparency = 40;
        float transparencySpeed =  transparency / (height / speed);

        text.setTransparencySpeed( -transparencySpeed );
        text.setTransparency(transparency);

        text.setSize(  (rand.nextFloat()*10) + 20);

        text.update();
    }



    private class FloatingText{

        private float speed;
        private float x;
        private float y;
        private long delay;

        private FrameLayout frame;
        private TextView textView;
        private Activity activity;
        private float transparency = 40;
        private float transparencySpeed;

        public FloatingText(Activity activity, Context context, FrameLayout parent){
            this.activity = activity;

            FrameLayout.LayoutParams params;

            frame = new FrameLayout(context);
            params = new FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
            frame.setLayoutParams(params);

            textView = new TextView(context);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setMaxLines(1);
            params = new FrameLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);


            textView.setLayoutParams(params);

            frame.addView(textView);
            parent.addView(frame);
        }

        public void setPosition(float x, float y){
            this.x = x;
            this.y = y;
            update();
        }

        public float move(){
            transparency += transparencySpeed;
            if(transparency < 0) transparency = 0;
            y += speed;
            update();
            return y;
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

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            params.leftMargin = (int) x;
            params.topMargin = (int) y;
            frame.setLayoutParams(params);

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
    }
}
