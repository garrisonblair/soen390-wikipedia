package org.wikipedia.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.io.IOException;

/**
 * Created by Andres on 2018-02-26.
 */

public class GalleryUtil{

    public static Intent newGalleryPickIntent(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        return photoPickerIntent;
    }

    public static Bitmap getSelectedPicture(int resultCode, Intent data, Activity activity){
        Bitmap bitmap = null;
        if (resultCode == Activity.RESULT_OK){
            if (data != null){
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),data.getData());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
