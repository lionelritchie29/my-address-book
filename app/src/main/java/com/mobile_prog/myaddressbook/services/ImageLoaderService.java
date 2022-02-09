package com.mobile_prog.myaddressbook.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageLoaderService extends AsyncTask<String, Void, Bitmap> {
    private ImageView imgView;

    public ImageLoaderService(ImageView imgView) {
        this.imgView = imgView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bm = null;
        try {
            InputStream in = new URL(url).openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            e.printStackTrace();
        }

        imgView.setImageBitmap(bm);
        return bm;
    }


}
