package com.votedesk.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


import java.io.InputStream;

public class AsynchDownloadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView targetView;

    public AsynchDownloadImage(ImageView targetImageView) {
        this.targetView = targetImageView;
    }

    protected Bitmap doInBackground(String... urlList) {
        String imageUrl = urlList[0];
        Bitmap imageData = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            imageData = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return imageData;
    }

    protected void onPostExecute(Bitmap result) {

        targetView.setImageBitmap(result);
    }
}