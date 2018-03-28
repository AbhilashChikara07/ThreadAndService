package com.example.chikara.seviceandthread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chikara on 3/22/18.
 */

public class ServerCommunicationUsingHandlerThread extends AppCompatActivity {

    private Thread serverCommThread;
    private int CONNECTION_TIME = 2 * 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_responce_layout);

    }


    private void serverCommunication() {
        serverCommThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                try {
                    URL url = new URL("http://www.android.com/");
                    httpsURLConnection = (HttpsURLConnection)
                            url.openConnection();
                    httpsURLConnection.setConnectTimeout(CONNECTION_TIME);
                    httpsURLConnection.setDoOutput(true);
//                    httpsURLConnection.setReadTimeout();
                    httpsURLConnection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    httpsURLConnection.disconnect();
                }
            }
        });
        serverCommThread.start();
    }
}
