package org.wikipedia.imagesearch;

/**
 * Created by steve on 20/02/18.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.wikipedia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

// ImageRecognitionService class which implements ImageRecognitionLabel interface
public class ImageRecognitionService /*implements ImageRecognitionLabel*/{

    // Required attributes of ImageRecognitionService
    private List<EntityAnnotation> entityAnnotationLabels;

    private BatchAnnotateImagesResponse batchAnnotateImagesResponse;

    // API Key (do not abuse usage as we have limited calls
    private static final String API_KEY = "AIzaSyCPT4PRQAyO3iqTpWxJ6XHmchCSkxri9QA";

    // Static instance of the ImageRecognitionService object implemented as singleton instance
    private static ImageRecognitionService imageRecognitionService;

    // Static method to implement singleton instance of class
    public static ImageRecognitionService getImageRecognitionService(){
        if (imageRecognitionService == null){
            imageRecognitionService = new ImageRecognitionService();
            return imageRecognitionService;
        }
        else{
            return imageRecognitionService;
        }
    }

    // Method implemented as per interface. returns label description as string
    public String getDescription(EntityAnnotation label){
        String description = "";
        if (label != null){
            description = String.format(Locale.US, label.getDescription());
            return description;
        }
        else {
            description = "No Image";
        }
        return description;
    }

    // Method implemented as per interface. returns label score as double
    public double getScore(EntityAnnotation label){
        double score;
        if (label != null){
            score = label.getScore();
        }
        else {
            score = 0.0;
        }
        return score;
    }

    // accessor method for entityAnnotationLabels
    public List<EntityAnnotation> getEntityAnnotationLabels(){
        return this.entityAnnotationLabels;
    }

    // mutator method for entityAnnotationLabels
    public void seteEntityAnnotationLabels(List<EntityAnnotation> entityAnnotationLabels){
        this.entityAnnotationLabels = entityAnnotationLabels;
    }

    // accessor method for batchAnnotateImagesResponse
    public BatchAnnotateImagesResponse getBatchAnnotateImagesResponse(){
        return this.batchAnnotateImagesResponse;
    }

    // mutator method for batchAnnotateImagesResponse
    public void setBatchAnnotateImagesResponse(BatchAnnotateImagesResponse batchAnnotateImagesResponse){
        this.batchAnnotateImagesResponse = batchAnnotateImagesResponse;
    }

}

