package com.yoboi.idhack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity{


    private static final String DEBUGTAG = "TAG";

    private static long press_time;

    private static boolean isPressed;

    private static long up_time;

    private static ClickProcessor clPro;

    private static boolean configured;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPressed = false;
        FileInputStream infile;
        clPro = new ClickProcessor();
        Thread thread = new Thread(clPro);
        thread.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Log.d(DEBUGTAG, "volume_down pressed");
            Context context = getApplicationContext();
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            if (!isPressed) {
                press_time = System.currentTimeMillis();
                isPressed = true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Log.d(DEBUGTAG, "volume_down released");
            up_time = System.currentTimeMillis();
            isPressed = false;
            addToQueue();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addToQueue() {
        Log.d(DEBUGTAG, "" + (up_time - press_time));
        clPro.addClick(up_time - press_time, System.currentTimeMillis());
    }

}