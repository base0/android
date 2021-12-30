Android 11 or higher

build.gradle Module

    def camerax_version = "1.0.1"
    // CameraX core library using camera2 implementation
    implementation "androidx.camera:camera-camera2:$camerax_version"
    // CameraX Lifecycle Library
    implementation "androidx.camera:camera-lifecycle:$camerax_version"
    // CameraX View class
    implementation "androidx.camera:camera-view:1.0.0-alpha27"

AndroidManifest.xml

    <uses-feature android:name="android.hardware.camera.any" />
    <uses-permission android:name="android.permission.CAMERA" />
    
[reference](https://developer.android.com/codelabs/camerax-getting-started#0)
