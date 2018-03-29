package com.example.chikara.seviceandthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BookEntity> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BookAdaptorClass mAdaptor;
    private SwipeRefreshLayout mSwipeRefresh;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.mProgressBar);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mSwipeRefresh = findViewById(R.id.mSwipeRefresh);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(false);
                serverCommunication();
            }
        });

        serverCommunication();
    }


    private void serverCommunication() {
        new ServerCommunication(new ServerCommunication.ConnectionListener() {
            @Override
            public void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(String result) {
                mProgressBar.setVisibility(View.GONE);
                serializeXmlData(result);
                mAdaptor = new BookAdaptorClass(MainActivity.this, mList);
                mRecyclerView.setAdapter(mAdaptor);
            }

            @Override
            public void onNoNetwork() {

            }
        }).execute("https://s3.eu-central-1.amazonaws.com/testandroid1/data.xml");
    }

    private void serializeXmlData(String xmlResponse) {
        try {
            XmlPullParserHandler parser = new XmlPullParserHandler();
            mList = parser.parse(new StringReader(xmlResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            return download_Image(urls[0]);
        }


        private Bitmap download_Image(String url) {
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
            } catch (IOException e) {
                Log.e("Hub", "Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
            //---------------------------------------------------
        }

        @Override
        protected void onPostExecute(Bitmap result) {
        }
    }
}
