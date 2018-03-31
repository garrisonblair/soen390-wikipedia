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
    private String url;

    public ThumbnailLoadTask(ImageView imageView, String thumbnailURL) {
        this.imageView = imageView;
        this.url = thumbnailURL;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap logo = null;

        try {
            InputStream is = new URL(url).openStream();

            logo = BitmapFactory.decodeStream(is);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logo;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
