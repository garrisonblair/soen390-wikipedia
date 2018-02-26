package org.wikipedia.feed.imageBasedSearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.wikipedia.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoTaken {
    private String path;
    public String getPath(){
        return this.path;
    }
    public Intent takePhoto(Context context) {
        Intent currentTakePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (currentTakePhotoIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createPhotoFile(context);
            } catch (IOException ex) {
                Toast.makeText(context, "Error while creating file.", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);
                currentTakePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return currentTakePhotoIntent;
    }
    public File createPhotoFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + "_";
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(fileName, ".jpg", storageDirectory);
        this.path = file.getAbsolutePath();
        return file;
    }
}
