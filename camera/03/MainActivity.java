package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};
    final int REQUEST_CODE_PERMISSIONS = 0;

    boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        // Set up the listener for take photo button
        Button button = findViewById(R.id.camera_capture_button);
        button.setOnClickListener(v -> takePhoto());
    }

    // https://github.com/android/camera-samples/blob/master/CameraXBasic/app/src/main/java/com/android/example/cameraxbasic/MainActivity.kt
    ImageCapture imageCapture;

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                imageCapture = new ImageCapture.Builder().build();

                ProcessCameraProvider cameraProvider = future.get();
                Preview preview = new Preview.Builder().build();
                PreviewView viewFinder = findViewById(R.id.viewFinder);
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
                cameraProvider.unbindAll();
                //cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview);
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
    
    static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";

    public void takePhoto() {
        if (imageCapture == null)
            return;

        final File photoFile = new File(new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg");

        ContentValues contentValue = new ContentValues();
        contentValue.put(MediaStore.MediaColumns.DISPLAY_NAME, photoFile.getName());
        contentValue.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValue.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValue).build();

        imageCapture.takePicture(outputOptions,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), photoFile.getName());
                        Log.d("vac", "onImageSaved: " + file.length());
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("vac", "onError: ");
                    }
                });
    }
}
