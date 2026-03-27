package com.cipher.media.security

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Manages hidden front-camera capture for intruder detection.
 * Takes a selfie silently when a wrong PIN is entered in the Vault.
 */
class IntruderCameraManager(private val context: Context) {

    private val TAG = "IntruderCamera"

    /**
     * Capture a photo from the front camera and save it to the intruder photos directory.
     * @param onComplete callback with the saved file path, or null on failure.
     */
    fun captureIntruderPhoto(onComplete: (String?) -> Unit) {
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    val imageCapture = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build()

                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    cameraProvider.unbindAll()

                    // We need a lifecycle owner; for a service context, we use a no-op approach
                    // This works when called from an Activity context
                    val photoFile = createPhotoFile()

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    // Bind to lifecycle - requires activity context
                    try {
                        val lifecycle = (context as? androidx.lifecycle.LifecycleOwner)
                        if (lifecycle != null) {
                            cameraProvider.bindToLifecycle(lifecycle, cameraSelector, imageCapture)

                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                        Log.d(TAG, "Intruder photo saved: ${photoFile.absolutePath}")
                                        cameraProvider.unbindAll()
                                        onComplete(photoFile.absolutePath)
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e(TAG, "Photo capture failed: ${exception.message}")
                                        cameraProvider.unbindAll()
                                        onComplete(null)
                                    }
                                }
                            )
                        } else {
                            Log.w(TAG, "Context is not a LifecycleOwner, skipping camera capture")
                            cameraProvider.unbindAll()
                            onComplete(null)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Camera bind failed: ${e.message}")
                        cameraProvider.unbindAll()
                        onComplete(null)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Camera provider failed: ${e.message}")
                    onComplete(null)
                }
            }, ContextCompat.getMainExecutor(context))
        } catch (e: Exception) {
            Log.e(TAG, "Camera init failed: ${e.message}")
            onComplete(null)
        }
    }

    private fun createPhotoFile(): File {
        val intruderDir = File(context.filesDir, "intruder_photos")
        if (!intruderDir.exists()) intruderDir.mkdirs()
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File(intruderDir, "intruder_$timestamp.jpg")
    }
}
