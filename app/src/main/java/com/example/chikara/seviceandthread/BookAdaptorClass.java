package com.example.chikara.seviceandthread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class BookAdaptorClass extends RecyclerView.Adapter<BookAdaptorClass.HolderClass> {

    private Context mContext;
    private ArrayList<BookEntity> mList;

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

        if (StaticImageCacheClass.cacheMap.get(mEntity.getTHUMBNAIL().get(0)) != null) {
            holder.mImageView.setImageBitmap(StaticImageCacheClass.
                    cacheMap.get(mEntity.getTHUMBNAIL().get(0)));
        } else {
            new DownloadImagesTask(mContext, new DownloadImagesTask.ConnectionCallBackListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    saveFile(bitmap);
                    holder.mImageView.setImageBitmap(bitmap);
                }

                @Override
                public void onError() {
                    Log.e("onError", "onError");
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mEntity.getTHUMBNAIL().get(0));
        }
    }

    private void saveFile(Bitmap bitmap) {
        String folderPath = Environment.getExternalStorageDirectory().toString()
                + File.separator + "AbhilashChikara";
        try {
            File fullPath = new File(folderPath);
            if (!fullPath.exists()) {
                fullPath.mkdirs();
            }

            OutputStream fOut = null;
            File file = new File(fullPath, String.valueOf(System.currentTimeMillis()) + ".jpg");
            file.createNewFile();
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            /*
            * Media Store use to save image in gallery.
            * */
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
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
