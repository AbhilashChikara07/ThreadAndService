package com.example.chikara.seviceandthread;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdaptorClass extends RecyclerView.Adapter<BookAdaptorClass.HolderClass> {

    private Context mContext;
    private ArrayList<BookEntity> mList;
    private Notification.Builder notificationBuilder = null;
    private NotificationManager notificationManager = null;

    BookAdaptorClass(Context mContext, ArrayList<BookEntity> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }


    @Override
    public HolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderClass(LayoutInflater.from(mContext)
                .inflate(R.layout.inflate_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final HolderClass holder, int position) {
        final BookEntity mEntity = mList.get(position);

        holder.mTextView.setText("ID " + mEntity.getID() + "\n" + "NEW "
                + mEntity.getNEW() + "\n" + "EXT " +
                mEntity.getPDF_EXT() + "\n" + "TITLE " + mEntity.getTITLE());

            new DownloadImagesTask(mContext, new DownloadImagesTask.ConnectionCallBackListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    holder.mImageView.setImageBitmap(bitmap);
                }

                @Override
                public void onError() {

                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mEntity.getTHUMBNAIL().get(0));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderClass extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImageView;

        HolderClass(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.mTextView);
            mImageView = itemView.findViewById(R.id.mImageView);
        }
    }
}
