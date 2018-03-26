package org.wikipedia.relatedvideos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Fred on 2018-03-26.
 */

public class ThumbnailLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private ImageView imageView;
    private String videoID;
    public static final String THUMBNAIL_URL = "https://img.youtube.com/vi/";

    public ThumbnailLoadTask(ImageView imageView, String videoID) {
        this.imageView = imageView;
        this.videoID = videoID;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap logo = null;
        try {
            InputStream is = new URL(THUMBNAIL_URL + videoID + "/0.jpg").openStream();

            logo = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logo;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
