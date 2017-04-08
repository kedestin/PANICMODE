package com.yoboi.idhack;

// import java.util.ArrayList;
import android.util.Log;

import java.util.Stack;

/**
 * Created by j22ta on 4/8/2017.
 */

public class ClickProcessor implements Runnable{
    /*INSTANCE VARIABLES*/
    private Stack<Click> clickStack;
    // private ArrayList<Boolean> openSequence;
    private boolean sequence_started;

    private static final int openSize = 8;

    // used as a test for this prrof of concept.  User would be able to define their own
    // not a great method as it is not very complex
    // better method might have different options for the length of the button press rather than
    // long/short
    private static final boolean openArray[] = {true, false, true, false, true, false, true, false};


    /*CONSTRUCTOR*/
    ClickProcessor(){
        clickStack = new Stack<Click>();
        sequence_started = false;

    }
    public int run(long currentTime) {
        if (!clickStack.isEmpty()){
            if (clickStack.peek().time_pressed > BEGIN_INPUT_DURATION){
                for (int i = 0; i < clickStack.size(); i++) {
                    clickStack.pop();
                }
                sequence_started = false;
            }
            else if (currentTime - clickStack.peek().arrival_time >= END_INPUT_DURATION && clickStack.size() >= 10){
                return -1;
            }
            else if (clickStack.size() == openSize && currentTime - clickStack.peek().arrival_time >=END_INPUT_DURATION) {
                return checkOpen();
            }
        }
        return 0;
    }

    private int checkOpen() {
        for (int i = 7; i <= 0; ++i) {
            if (openArray[i]) {
                if (clickStack.pop().time_pressed < 1000) {
                    return 0;
                }
            }
            else if (clickStack.pop().time_pressed > 1000) {
                return 0;
            }
        }
        return 1;
    }


    public void addClick(long duration_pressed, long timestamp) {
        if (sequence_started){
            Click newClick = new Click();
            newClick.time_pressed = duration_pressed;
            newClick.arrival_time = timestamp;
            clickStack.add(newClick);
        }
        else if (duration_pressed >= BEGIN_INPUT_DURATION){
            sequence_started = true;
        }
    }
    /*CONSTANTS*/
    private static final long BEGIN_INPUT_DURATION = 2000;
    private static final long END_INPUT_DURATION = 5000;

    @Override
    public void run() {
        while(true) {
            long value = run(System.currentTimeMillis());
            if (value == -1) {
                Log.d("FINISHED", "PANIC MODE HAS BEEN ACTIVATED");
            } else if (value == 0) {
               // Log.d("FINISHED", "DOES NOTHING");
            } else if (value == 1) {
                Log.d("FINISHED", "OPEN THE WHISTLER APP");
            } else {
                Log.d("FINISHED", "THIS SHOULD NOT HAPPEN. LIKE EVER");
            }
        }
    }
}

