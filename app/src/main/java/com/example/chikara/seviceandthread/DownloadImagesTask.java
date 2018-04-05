package com.example.chikara.seviceandthread;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.RemoteViews;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DownloadImagesTask extends AsyncTask<String, Integer, Bitmap> {

    private ConnectionCallBackListener mListener;
    private Context mContext;
    private Notification.Builder notificationBuilder = null;
    private NotificationManager notificationManager = null;

    public DownloadImagesTask(Context mContext, ConnectionCallBackListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        createNotification();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImage(strings[0]);
    }

    private Bitmap downloadImage(String url) {
        //---------------------------------------------------
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onError();
        }
        return bm;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        mListener.onSuccess(s);
    }


    private void createNotification() {
//        RemoteViews notificationLayout = new RemoteViews(mContext.getPackageName(),
//                R.layout.notification_layout);

        notificationBuilder = new Notification.Builder(mContext)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        BitmapFactory.decodeResource(mContext.getResources(),
                                R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(Notification.PRIORITY_HIGH)
//                .setContent(notificationLayout)
                .setStyle(new Notification.BigTextStyle().bigText("BIG TEXT"));

        if (notificationManager == null)
            notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationId.getID(), notificationBuilder.build());
    }

    interface ConnectionCallBackListener {
        void onSuccess(Bitmap bitmap);

        void onError();
    }

}
