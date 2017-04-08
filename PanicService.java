package com.yoboi.idhack;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;
import android.os.Process;


/**
 * Createdbyj22taon4/8/2017.
 */

public class PanicService extends Service {


    @Override
    public void onCreate() {
/*
Startupthethreadrunningtheservice.Notethatwecreatea
separatethreadbecausetheservicenormallyrunsintheprocess's
mainthread,whichwedon'twanttoblock.Wealsomakeit
backgroundprioritysoCPU-intensiveworkwillnotdisruptourUI.
*/
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

//GettheHandlerThread'sLooperanduseitforourHandler

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "servicestarting", Toast.LENGTH_SHORT).show();

        Panic pc = new Panic();
        pc.run();

//Foreachstartrequest,sendamessagetostartajobanddeliverthe
//startIDsoweknowwhichrequestwe'restoppingwhenwefinishthejob


//Ifwegetkilled,afterreturningfromhere,restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
//Wedon'tprovidebinding,soreturnnull
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "servicedone", Toast.LENGTH_SHORT).show();
    }
}
