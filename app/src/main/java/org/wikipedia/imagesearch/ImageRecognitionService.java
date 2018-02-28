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


public class ImageRecognitionService{

    public interface Callback{
        void onVisionAPIResult(List<EntityAnnotation> list);
    }

    // maximum dimesion for images passed to google cloud API
    private static final int MAX_DIMENSION = 1200;
    private static final int QUALITY_NUMBER = 90;
    // maximum dimesion for images passed to google cloud API
    private static final int MAX_RESULTS = 15;
    // API Key (do not abuse usage as we have limited calls
    private static final String API_KEY = "AIzaSyCPT4PRQAyO3iqTpWxJ6XHmchCSkxri9QA";

    // Attributes of ImageRecognitionService in case manipulation is necessary by outside classes
    private List<EntityAnnotation> entityAnnotationLabels;
    private BatchAnnotateImagesResponse batchAnnotateImagesResponse;

    private Vision vision;

    private Callback callback;


   public ImageRecognitionService() {
       // sets up HttpTransport and JsonFactory object instances for handling call to google cloud vision
       HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
       JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

       // initialize VisionRequestInitializer with personal API_KEY for google vision API
       VisionRequestInitializer requestInitializer = new VisionRequestInitializer(API_KEY);

       // intantiates Vision.Builer instance with HttpTransport and JsonFactory objects
       Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);

       // sets the visionReqestInitializer of the build to the VisionRequestInitializer instance containing the API_KEY
       builder.setVisionRequestInitializer(requestInitializer);

       // "builds" an instance of Vision using the builder object
       this.vision = builder.build();
   }

   public ImageRecognitionService(Vision vision) {
       this.vision = vision;
   }

   public void setCallback(Callback callback) {
       this.callback = callback;
   }

   public Callback getCallback() {
       return callback;
   }

    // accessor method for entityAnnotationLabels
    private List<EntityAnnotation> getEntityAnnotationLabels() {
        return entityAnnotationLabels;
    }

    // mutator method for entityAnnotationLabels
    private void setEntityAnnotationLabels(List<EntityAnnotation> entityAnnotationLabels) {
        this.entityAnnotationLabels = entityAnnotationLabels;
    }

    // accessor method for batchAnnotateImagesResponse
    private BatchAnnotateImagesResponse getBatchAnnotateImagesResponse() {
        return batchAnnotateImagesResponse;
    }

    // mutator method for batchAnnotateImagesResponse
    private void setBatchAnnotateImagesResponse(BatchAnnotateImagesResponse batchAnnotateImagesResponse) {
        batchAnnotateImagesResponse = batchAnnotateImagesResponse;
    }

    // upload the image to the google vision api
    public void executeImageRecognition(Bitmap bitmap, Callback callback) {
        // Make sure URI is not full
        if (bitmap != null) {
            try {
                setCallback(callback);
                // scale the bitmap to save on bandwidth
                Bitmap bitmapForAPI = scaleDown(bitmap, MAX_DIMENSION);
                // call the google cloud vision API for scaled down bitmap
                callCloudVisionAPI(bitmapForAPI);
            } catch (IOException e) {
                System.out.println("Image upload failed because " + e.getMessage());
            }
        } else {
            System.out.println("Image upload failed due to null image.");
        }
    }


    // call the cloud vision API
    @SuppressLint("StaticFieldLeak")
    private void callCloudVisionAPI(final Bitmap bitmap) throws IOException {

        // cleans current state of ImageRecognition singleton's previous data
        setEntityAnnotationLabels(null);
        setBatchAnnotateImagesResponse(null);

        new AsyncTask<Object, Void, List<EntityAnnotation>>() {
            @Override
            protected List<EntityAnnotation> doInBackground(Object... params) {

                try {
                    // instantiates new BatchAnnotateImagesRequest
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();

                    // creates arraylist of type AnnotateImageRequest and handles
                    ArrayList<AnnotateImageRequest> annotateImageRequests = new ArrayList<AnnotateImageRequest>() { {
                        // instantiates new AnnotateImageRequest object instance
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                        // creates image object instance
                        Image image = new Image();
                        // converts image to JPEG as per suggestion of API
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        // compresses using values suggested by API
                        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_NUMBER, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();
                        // encoding image as per API instructions
                        image.encodeContent(imageBytes);
                        // setting encoded image to AnnotateImageRequest instance
                        annotateImageRequest.setImage(image);

                        // add features for the cloud vision api request such as label detection and max number of results (set to static final int MAX_RESULTS)
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(MAX_RESULTS);
                            add(labelDetection);

                            Feature webDetection = new Feature();
                            webDetection.setType("WEB_DETECTION");
                            webDetection.setMaxResults(MAX_RESULTS);
                            add(webDetection);

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
                    setBatchAnnotateImagesResponse(batchAnnotateImagesResponse);
                    setEntityAnnotationLabels(response.getResponses().get(0).getLabelAnnotations());

                    // returns the response transformed into entityAnnotationLabels which allow access to description and score attributes
                    return getEntityAnnotationLabels();

                } catch (GoogleJsonResponseException e) {
                    System.out.println("Request to API failed due to: " + e.getContent());
                } catch (IOException e) {
                    System.out.println("Request to API failed due to: " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(List<EntityAnnotation> result) {
                callback.onVisionAPIResult(result);
            }
        }.execute();
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
        } else if (originalWidth > originalHeight) {
            // check to see if which is longer between height and width before scaling
            // if width is larger then set width to max and scale height accordingly
            // scaledWidth set to max dimension
            scaledWidth = maxDimension;
            // scaledHeight casted as integer after being scaled proportionally based on scaled height multiplied originalHeight/originalWidth (cross multiplication)
            scaledHeight = (int) (scaledWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            // check to see if which is longer between height and width before scaling
            // if width is equal to height then scale both width and height proportionally by setting each to MAX_DIMENSION
            // since both originalHeight and originalWidth are equal, scale both to the MAX_DIMENSION
            scaledHeight = maxDimension;
            scaledWidth = maxDimension;
        }
        // return scaled bitmap based on scaledWidth and scaledHeight
        return Bitmap.createScaledBitmap(bitmapToScale, scaledWidth, scaledHeight, false);
    }

}

