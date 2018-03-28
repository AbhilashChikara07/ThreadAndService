package com.example.chikara.seviceandthread;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by chikara on 3/22/18.
 */

public class ServiceClass extends Service {

    private Looper mLooper;
    private HandlerClass mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mLooper = thread.getLooper();
        mHandler = new HandlerClass(mLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 5; i++) {
            Message msg = mHandler.obtainMessage();
            msg.arg1 = i;
            mHandler.sendMessage(msg);
        }

        return START_STICKY;
    }

    class HandlerClass extends Handler {

        HandlerClass(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1 == 1 || msg.arg1 == 3) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Log.e("Message", "" + msg.arg1);
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Log.e("Message", "" + msg.arg1);
            }
            stopSelf(msg.arg1);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
