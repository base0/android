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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    ImageAnalysis imageAnalyzer;

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

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                //
                imageAnalyzer = new ImageAnalysis.Builder().build();
                imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor(), new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        // Log.i("vac", "analyze: " + image.getFormat());  // YUV_420_888
                        // Y     luminance
                        // U,V   chrominance (blue and red)
                        /* https://developer.android.com/reference/android/graphics/ImageFormat#YUV_420_888
                          The order of planes in the array returned by Image#getPlanes() is guaranteed such that plane #0 is always Y, plane #1 is always U (Cb), and plane #2 is always V (Cr). */
//                        Log.i("vac", "analyze: " + image.getWidth() + " " + image.getHeight());
                        //Log.i("vac", "analyze: " + image.getPlanes()[0].getBuffer().remaining());
                        ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
                        byte[] data = new byte[byteBuffer.remaining()];
                        byteBuffer.get(data);

                        final int WIDTH = image.getWidth();
                        final int HEIGHT = image.getHeight();
                        Bitmap bitmap = Bitmap.createBitmap(HEIGHT, WIDTH, Bitmap.Config.ARGB_8888);
                        int i = 0;
                        for (int x = HEIGHT - 1; x >= 0; x--) {
                            for (int y = 0; y < WIDTH; y++) {
                                int n = data[i++] & 0xFF;
                                bitmap.setPixel(x, y, 0xFF000000 | (n << 16) | (n << 8) | n);
                            }
                        }

                        ImageView imageView = findViewById(R.id.imageView);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (imageView != null)
                                    imageView.setImageBitmap(bitmap);
                            }
                        });
                        image.close();
                    }
                });


                ProcessCameraProvider cameraProvider = future.get();
                Preview preview = new Preview.Builder().build();
                PreviewView viewFinder = findViewById(R.id.viewFinder);
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
                cameraProvider.unbindAll();
                //cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview);
                //cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture, imageAnalyzer);
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalyzer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
}
