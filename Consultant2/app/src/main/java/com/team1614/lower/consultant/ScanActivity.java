package com.team1614.lower.consultant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ScanActivity extends AppCompatActivity {

    ImageView image_to_scan;
    AlertDialog.Builder builder;
    File resizedImage1;
    ListAdapter adapter;
    TextView touch_to_scan,answer;
    BatchAnnotateImagesResponse batchResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        image_to_scan=(ImageView)findViewById(R.id.image_to_scan);
        touch_to_scan=(TextView) findViewById(R.id.touch_to_scan);
        answer=(TextView)findViewById(R.id.answer);

        builder = new AlertDialog.Builder(this);
        EasyImage.configuration(this)
                .setImagesFolderName("Scan")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(false);

        final String[] items = new String[]{"Take by Camera", "Select from Gallery"};
        final Integer[] icons = new Integer[]{R.drawable.camera_ic, R.drawable.ic_gallery};
        adapter = new ArrayAdapterWithIcons(getApplicationContext(), items, icons);

        builder.setTitle("Scan Answer ")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                EasyImage.openCamera(ScanActivity.this, 0);

                            }
                            break;
                            case 1: {
                                EasyImage.openGallery(ScanActivity.this, 0);

                            }
                            break;

                        }

                    }
                }).show();

        image_to_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder.setTitle("Scan Answer ")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item) {
                                    case 0: {
                                        EasyImage.openCamera(ScanActivity.this, 0);

                                    }
                                    break;
                                    case 1: {
                                        EasyImage.openGallery(ScanActivity.this, 0);

                                    }
                                    break;

                                }

                            }
                        }).show();
            }
        });

        touch_to_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vision.Builder visionBuilder = new Vision.Builder(
                        new NetHttpTransport(),
                        new AndroidJsonFactory(),
                        null);

                visionBuilder.setVisionRequestInitializer(
                        new VisionRequestInitializer("AIzaSyD40QHhkZga9AVBvSEjOejiTll0HzXC6xU"));
                Vision vision = visionBuilder.build();

                Feature desiredFeature = new Feature();
                desiredFeature.setType("TEXT_DETECTION");

                Bitmap bitmap1 = drawableToBitmap(image_to_scan.getDrawable());
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 70, stream1);
                byte[] bt_scan = stream1.toByteArray();

                Image inputImage = new Image();
                inputImage.encodeContent(bt_scan);

                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(inputImage);
                request.setFeatures(Arrays.asList(desiredFeature));

                BatchAnnotateImagesRequest batchRequest =
                        new BatchAnnotateImagesRequest();

                batchRequest.setRequests(Arrays.asList(request));

                try {

                    batchResponse =
                            vision.images().annotate(batchRequest).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                final TextAnnotation text = batchResponse.getResponses()
                        .get(0).getFullTextAnnotation();

                answer.setText(text.getText());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if (type == 0) {
                    try {

                        resizedImage1 = new Compressor(getApplicationContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToFile(imageFiles.get(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Picasso.with(getApplicationContext()).load(imageFiles.get(0)).into(image_to_scan);

                }
            }


            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(ScanActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
