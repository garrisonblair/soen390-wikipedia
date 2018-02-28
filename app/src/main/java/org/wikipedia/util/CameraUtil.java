package org.wikipedia.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.wikipedia.BuildConfig;
import org.wikipedia.settings.Prefs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtil {
    private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(String currPath) {
        this.path = currPath;
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
        if (Prefs.getSavePhoto()) {
            storageDirectory = getPublicStorageDirectory(context);
        }
        File file = File.createTempFile(fileName, ".jpg", storageDirectory);
        setPath(file.getAbsolutePath());
        return file;
    }

    public File getPublicStorageDirectory(Context context) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Wikipedia");
        if (directory.mkdirs()) {
            Toast.makeText(context, "Directory Wikipedia has created", Toast.LENGTH_SHORT).show();
        } /*else{
            Toast.makeText(context, "Directory already exists", Toast.LENGTH_SHORT).show();
        }*/
        return directory;
    }

    public void addPhotoToGallery(Context context, String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
