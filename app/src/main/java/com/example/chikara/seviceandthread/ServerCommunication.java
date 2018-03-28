package com.example.chikara.seviceandthread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chikara on 3/22/18.
 */

public class ServerCommunication implements Runnable {
    private ConnectionListener mConnectionListener;
    private HttpsURLConnection mUrlConnection;
    private String mStringUrl;
    private int CONNECTION_TIME = 60 * 1000;
    private String mRequestMethod;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();


    public ServerCommunication(String mStringUrl, String mRequestMethod,
                               ConnectionListener mConnectionListener) {
        this.mStringUrl = mStringUrl;
        this.mRequestMethod = mRequestMethod;
        this.mConnectionListener = mConnectionListener;
        mConnectionListener.onPreExecute();
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL mUrl = new URL(mStringUrl);
                    mUrlConnection = (HttpsURLConnection) mUrl.openConnection();
                    mUrlConnection.connect();

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(mUrlConnection.getInputStream()));

                    StringBuilder responseOutput = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        responseOutput.append(line);
                    }
                    mConnectionListener.onSuccess(responseOutput.toString());
                    Log.e("responseOutput", "" + responseOutput);
                } catch (Exception e) {
                    e.printStackTrace();
                    mConnectionListener.onError(e.getMessage());
                }
            }
        }).start();
    }


    interface ConnectionListener {

        void onPreExecute();

        void onError(String error);

        void onSuccess(String result);

        void onNoNetwork();
    }
}
