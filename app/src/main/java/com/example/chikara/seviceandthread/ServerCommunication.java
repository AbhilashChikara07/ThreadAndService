package com.example.chikara.seviceandthread;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chikara on 3/22/18.
 */

public class ServerCommunication extends AsyncTask<String, String, String> {
    private ConnectionListener mConnectionListener;
    private HttpsURLConnection mUrlConnection;


    ServerCommunication(ConnectionListener mConnectionListener) {
        this.mConnectionListener = mConnectionListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mConnectionListener.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder responseOutput = null;
        try {
            URL mUrl = new URL(strings[0]);
            mUrlConnection = (HttpsURLConnection) mUrl.openConnection();
            mUrlConnection.connect();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(mUrlConnection.getInputStream()));

            responseOutput = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mConnectionListener.onError(e.getMessage());
        }
        assert responseOutput != null;
        return (responseOutput.toString());
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mConnectionListener.onSuccess(s);
    }

    interface ConnectionListener {

        void onPreExecute();

        void onError(String error);

        void onSuccess(String result);

        void onNoNetwork();
    }
}
