package com.yoboi.idhack;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Createdbyj22taon4/8/2017.
 */

public class Panic extends AppCompatActivity {
    private static final String DEBUGTAG = "TAG";

    private static long press_time;

    private static boolean isPressed;

    private static long up_time;

    private static ClickProcessor clPro;


    public void run() {
        isPressed = false;
        clPro = new ClickProcessor();
        Thread thread = new Thread(clPro);
        thread.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//Log.d(DEBUGTAG,"volume_downpressed");
            Context context = getApplicationContext();
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            addPress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//Log.d(DEBUGTAG,"volume_downreleased");
            addUp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addPress() {
        if (!isPressed) {
            press_time = System.currentTimeMillis();
            isPressed = true;
        }
    }

    private void addUp() {
        up_time = System.currentTimeMillis();
        isPressed = false;
        addToQueue();
    }

    private void addToQueue() {
        Log.d(DEBUGTAG, "" + (up_time - press_time));
        clPro.addClick(up_time - press_time, System.currentTimeMillis());
    }
}
