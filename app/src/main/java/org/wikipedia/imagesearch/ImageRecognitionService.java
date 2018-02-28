package org.wikipedia.imagesearch;

/**
 * Created by steve on 20/02/18.
 */

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// ImageRecognitionService class which implements ImageRecognitionLabel interface
public class ImageRecognitionService /*implements ImageRecognitionLabel*/{

    // Attributes of ImageRecognitionService in case manipulation is necessary by outside classes
    private static List<EntityAnnotation> entityAnnotationLabels;
    private static BatchAnnotateImagesResponse batchAnnotateImagesResponse;

    // maximum dimesion for images passed to google cloud API
    private static final int MAX_DIMENSION = 1200;

    // maximum dimesion for images passed to google cloud API
    private static final int MAX_TYPE_RESULTS = 15;

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

    // Method implemented as per interface. returns label description as string based on passed in EntityAnnotation returned by google vision API call
    public static String getDescription(EntityAnnotation label){
        String description = "";
        if (label != null){
            // formats EntityAnnotation to String format
            description = String.format(Locale.US, label.getDescription());
            return description;
        }
        else {
            description = "No Description for EntityAnnotation";
        }
        return description;
    }

    // Method implemented as per interface. returns label score as double based on passed in EntityAnnotation returned by google vision API call
    public static double getScore(EntityAnnotation label){
        double score;
        if (label != null){
            // formats score to double to satisfy interface by casting as double
            score = (double)label.getScore();
        }
        else {
            score = 0.0;
        }
        return score;
    }

    // accessor method for entityAnnotationLabels
    public static List<EntityAnnotation> getEntityAnnotationLabels(){
        return entityAnnotationLabels;
    }

    // mutator method for entityAnnotationLabels
    public static void seteEntityAnnotationLabels(List<EntityAnnotation> entityAnnotationLabels){
        entityAnnotationLabels = entityAnnotationLabels;
    }

    // accessor method for batchAnnotateImagesResponse
    public static BatchAnnotateImagesResponse getBatchAnnotateImagesResponse(){
        return batchAnnotateImagesResponse;
    }

    // mutator method for batchAnnotateImagesResponse
    public static void setBatchAnnotateImagesResponse(BatchAnnotateImagesResponse batchAnnotateImagesResponse){
        batchAnnotateImagesResponse = batchAnnotateImagesResponse;
    }

    // upload the image to the google vision api
    public void uploadImageToCloudVisionAPI(Bitmap bitmap) {
        // Make sure URI is not full
        if (bitmap != null) {
            try {
                // scale the bitmap to save on bandwidth
                Bitmap bitmapForAPI = scaleDown(bitmap,MAX_DIMENSION);
                // call the google cloud vision API for scaled down bitmap
                callCloudVisionAPI(bitmapForAPI);
            }
            catch (IOException e) {
                System.out.println("Image upload failed because " + e.getMessage());
            }
        } else {
            System.out.println("Image upload failed due to null image.");
        }
    }


    // call the cloud vision API
    @SuppressLint("StaticFieldLeak")
    private static List<EntityAnnotation> callCloudVisionAPI(final Bitmap bitmap) throws IOException {

        // cleans current state of ImageRecognition singleton's previous data
        ImageRecognitionService.seteEntityAnnotationLabels(null);
        ImageRecognitionService.setBatchAnnotateImagesResponse(null);

        new AsyncTask<Object, Void, List<EntityAnnotation>>() {
            @Override
            protected List<EntityAnnotation> doInBackground(Object... params) {
                // sets up HttpTransport and JsonFactory object instances for handling call to google cloud vision
                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                try {
                    // initialize VisionRequestInitializer with personal API_KEY for google vision API
                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(API_KEY);

                    // intantiates Vision.Builer instance with HttpTransport and JsonFactory objects
                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);

                    // sets the visionReqestInitializer of the build to the VisionRequestInitializer instance containing the API_KEY
                    builder.setVisionRequestInitializer(requestInitializer);

                    // "builds" an instance of Vision using the builder object
                    Vision vision = builder.build();

                    // instantiates new BatchAnnotateImagesRequest
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();

                    // creates arraylist of type AnnotateImageRequest and handles
                    ArrayList<AnnotateImageRequest> annotateImageRequests = new ArrayList<AnnotateImageRequest>(){{
                        // instantiates new AnnotateImageRequest object instance
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                        // creates image object instance
                        Image image = new Image();
                        // converts image to JPEG as per suggestion of API
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        // compresses using values suggested by API
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();
                        // encoding image as per API instructions
                        image.encodeContent(imageBytes);
                        // setting encoded image to AnnotateImageRequest instance
                        annotateImageRequest.setImage(image);
                        // add features for the cloud vision api request such as label detection and max number of results (set to static final int MAX_TYPE_RESULTS)
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(MAX_TYPE_RESULTS);
                            add(labelDetection);
                            // Currently commenting out until we have at least label detection working. will like use later
                    /*
                    Feature webDetection = new Feature();
                    webDetection.setType("WEB_DETECTION");
                    webDetection.setMaxResults(MAX_TYPE_RESULTS);
                    add(webDetection);
                    */
                        }});
                        // Add the list of features to the request which in this case is only label detect for now. will add web detection later
                        add(annotateImageRequest);
                    }};

                    // sets requests for BatchAnnotateImagesRequest instance (which is this case is only one request in the list)
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    // creation of annotateRequest based on batchAnnotateImagesRequest created above
                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);

                    // As per Google Cloud Vision API "Due to a bug: requests to Vision API containing large images fail when GZipped." therefore GZip disabled
                    annotateRequest.setDisableGZipContent(true);

                    // obtains BatchAnnotateImagesResponse after sending request to google cloud vision and obtaining results
                    BatchAnnotateImagesResponse response = annotateRequest.execute();

                    // sets ImageRecognitionService attributes of batchAnnotateImagesResponse and entityAnnotationLabels created from response
                    ImageRecognitionService.setBatchAnnotateImagesResponse(batchAnnotateImagesResponse);
                    ImageRecognitionService.seteEntityAnnotationLabels(response.getResponses().get(0).getLabelAnnotations());

                    // returns the response transformed into entityAnnotationLabels which allow access to description and score attributes
                    return ImageRecognitionService.getEntityAnnotationLabels();

                }
                catch (GoogleJsonResponseException e) {
                    System.out.println("Request to API failed due to: " + e.getContent());
                }
                catch (IOException e) {
                    System.out.println("Request to API failed due to: " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(List<EntityAnnotation> result) {
                return;
            }
        }.execute();

        return null;
    }


    // Scales down image based on supplied max dimension
    private static Bitmap scaleDown(Bitmap bitmapToScale, int maxDimension) {

        // obtain original height and width
        int originalWidth = bitmapToScale.getWidth();
        int originalHeight = bitmapToScale.getHeight();

        // set resized height and width to value of maximum dimension (private static final int MAX_DIMENSION)
        int scaledWidth = maxDimension;
        int scaledHeight = maxDimension;

        // check to see if which is longer between height and width before scaling
        // if height is larger then set height to max and scale width accordingly
        if (originalHeight > originalWidth) {
            // scaledHeight set to max dimension
            scaledHeight = maxDimension;
            // scaledWidth casted as integer after being scaled proportionally based on scaled height multiplied originalWidth/originalHeight (cross multiplication)
            scaledWidth = (int) (scaledHeight * (double) originalWidth / (double) originalHeight);
        }
        // check to see if which is longer between height and width before scaling
        // if width is larger then set width to max and scale height accordingly
        else if (originalWidth > originalHeight) {
            // scaledWidth set to max dimension
            scaledWidth = maxDimension;
            // scaledHeight casted as integer after being scaled proportionally based on scaled height multiplied originalHeight/originalWidth (cross multiplication)
            scaledHeight = (int) (scaledWidth * (float) originalHeight / (float) originalWidth);
        }
        // check to see if which is longer between height and width before scaling
        // if width is equal to height then scale both width and height proportionally by setting each to MAX_DIMENSION
        else if (originalHeight == originalWidth) {
            // since both originalHeight and originalWidth are equal, scale both to the MAX_DIMENSION
            scaledHeight = maxDimension;
            scaledWidth = maxDimension;
        }
        // return scaled bitmap based on scaledWidth and scaledHeight
        return Bitmap.createScaledBitmap(bitmapToScale, scaledWidth, scaledHeight, false);
    }

}

