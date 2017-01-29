package com.pranjal.customerapp.Activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Request.ImageReq;
import com.pranjal.customerapp.Response.ImageResponse;
import com.pranjal.customerapp.Response.Tag;
import com.pranjal.customerapp.Service.ImageRequest;
import com.pranjal.customerapp.Utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchImage extends AppCompatActivity {
    private ImageView imageView;
    private Button btnChoose, btnUpload,btnCapture;
    private ProgressBar progressBar;
    String timeStamp;

    private String tatti = "";

    public static String BASE_URL = "http://192.168.1.100/AndroidUploadImage/upload.php";
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE= 2;

    private String filePath;
    private Uri fileUri; // file url to store image
    private boolean browse=true;

    public static final String IMAGE_DIRECTORY_NAME = "Sapran";

    private static final String storageContainer = "storage";
    private static final String storageConnectionString = "DefaultEndpointsProtocol=http;" +
            "AccountName=sapran;" +
            "AccountKey=PLrel6KqvzCecEd3S22tQf8rjEfusX/tU4MNQrpImMkjhZW7aoMwNbRWh8K0QBDZUovI2GbXa/uUFyNegfVMfg=="
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_image);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        imageView = (ImageView) findViewById(R.id.imageView);
        btnChoose = (Button) findViewById(R.id.button_choose);
        btnCapture= (Button) findViewById(R.id.button_capture);
        btnUpload = (Button) findViewById(R.id.button_upload);
        progressBar = (ProgressBar) findViewById(R.id.imageProgressBar);

        progressBar.setVisibility(View.INVISIBLE);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!browse) browse=true;
                imageBrowse();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(browse) browse=false;
                captureImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (fileUri != null) {
//                    Log.d("tag", "task");
                    MyTask myTask = new MyTask();
                    if(browse) myTask.execute (getPath(fileUri));
                    else myTask.execute (fileUri.getPath());
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {

            if (resultCode == RESULT_OK){

                fileUri = data.getData();
                filePath = getPath(fileUri);
//                Log.d("picUri", fileUri.toString());
//                Log.d("filePath", filePath);

                imageView.setImageURI(fileUri);

            }else if (requestCode == RESULT_CANCELED){
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled gallery upload", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to upload from gallery", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                filePath = fileUri.getPath();
//                Log.d("picUri", fileUri.toString());
//                Log.d("filePath", filePath);
                imageView.setImageURI(fileUri);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to show image", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private class MyTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... params) {
            try {
                // Retrieve storage account from connection-string.
//                Log.d("tag",params[0]);
                CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                // Create the blob client.
                CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                // Retrieve reference to a previously created container.
                CloudBlobContainer container = blobClient.getContainerReference(storageContainer);

                // Create or overwrite the blob (with the name "example.jpeg") with contents from a local file.
                // Create a media file name
                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                CloudBlockBlob blob = container.getBlockBlobReference(timeStamp + ".jpeg");
                File source = new File(params[0]);
                blob.upload(new FileInputStream(source), source.length());
                Toast.makeText(SearchImage.this, "Success1", Toast.LENGTH_SHORT).show();
//                Log.d("tag", "upload completed "+params[0]);

                // Create the container if it does not exist.
                //container.createIfNotExists();
            } catch (Exception e) {
                // Output the stack trace.
//                Log.d("tag", "exception");
                e.printStackTrace();
//                Toast.makeText(SearchImage.this, "Error", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        protected void onPostExecute(Long result) {
            progressBar.setVisibility(View.INVISIBLE);
//            Toast.makeText(SearchImage.this, "Post Execute", Toast.LENGTH_SHORT).show();

            makeAPICall();
        }

    }

    private void makeAPICall() {
        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(Constants.URL_IMAGE_REQUEST)
                .build();

        String url = Constants.URL_IMAGE + "/" + timeStamp + ".jpeg";

//        Toast.makeText(SearchImage.this, url, Toast.LENGTH_SHORT).show();

        ImageReq i = new ImageReq();
        i.setUrl(url);

        ImageRequest api = adapter.create(ImageRequest.class);

//        Toast.makeText(SearchImage.this, i.getUrl(), Toast.LENGTH_SHORT).show();

        api.postURL(i, new Callback<ImageResponse>() {
            @Override
            public void success(ImageResponse imageResponse, Response response) {
//                Toast.makeText(SearchImage.this,"success",Toast.LENGTH_SHORT).show();
                List<Tag> list = imageResponse.getTags();

                boolean food = false;

                for(Tag l : list){
//                    Toast.makeText(SearchImage.this, l.getName() + ": name ", Toast.LENGTH_SHORT).show();
                    if(l.getName().toString().equalsIgnoreCase("food") || l.getName().toString().equalsIgnoreCase("fruit")){
                        food = true;
                    }
                    else{
                        tatti = tatti + l.getName().toString() + ",";
                    }
                }

//                Toast.makeText(SearchImage.this, "tatti : " + tatti, Toast.LENGTH_SHORT).show();

                if(food) {
                    Intent i = new Intent(SearchImage.this, Recipe.class);
                    i.putExtra("Image_URL", tatti);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SearchImage.this, "No food found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(SearchImage.this,"Something went wrong. Please try again!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on screen orientation changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                Log.d("TAG", "Oops! Failed create "
//                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

}
