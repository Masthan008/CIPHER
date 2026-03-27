package com.cipher.media.ui.video.enhancement

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.View
import android.widget.Toast
import androidx.media3.ui.PlayerView
import java.io.File
import java.io.FileOutputStream

/**
 * Captures high-quality screenshots from the ExoPlayer PlayerView
 * using PixelCopy API (Android O+).
 *
 * - Free: Scaled to 720p with CIPHER watermark
 * - Pro:  Full resolution, no watermark
 */
object ScreenshotManager {

    fun captureScreenshot(
        playerView: PlayerView,
        context: Context,
        isPro: Boolean,
        onComplete: (String?) -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Fallback: drawingCache (deprecated but functional on old devices)
            fallbackCapture(playerView, context, isPro, onComplete)
            return
        }

        val bitmap = Bitmap.createBitmap(
            playerView.width,
            playerView.height,
            Bitmap.Config.ARGB_8888
        )

        val window = (context as? android.app.Activity)?.window
        if (window == null) {
            onComplete(null)
            return
        }

        PixelCopy.request(
            window,
            intArrayOf(
                playerView.left,
                playerView.top,
                playerView.right,
                playerView.bottom
            ).let { loc ->
                android.graphics.Rect(loc[0], loc[1], loc[2], loc[3])
            },
            bitmap,
            { result ->
                if (result == PixelCopy.SUCCESS) {
                    val processed = processScreenshot(bitmap, isPro)
                    val path = saveToGallery(processed, context)
                    Handler(Looper.getMainLooper()).post {
                        onComplete(path)
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        onComplete(null)
                    }
                }
            },
            Handler(Looper.getMainLooper())
        )
    }

    private fun fallbackCapture(
        view: View,
        context: Context,
        isPro: Boolean,
        onComplete: (String?) -> Unit
    ) {
        try {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            val processed = processScreenshot(bitmap, isPro)
            val path = saveToGallery(processed, context)
            onComplete(path)
        } catch (e: Exception) {
            onComplete(null)
        }
    }

    private fun processScreenshot(bitmap: Bitmap, isPro: Boolean): Bitmap {
        if (isPro) return bitmap // Full quality, no watermark

        // Free tier: scale to 720p max
        val maxHeight = 720
        val scaleFactor = if (bitmap.height > maxHeight) {
            maxHeight.toFloat() / bitmap.height
        } else 1f

        val scaled = if (scaleFactor < 1f) {
            Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * scaleFactor).toInt(),
                (bitmap.height * scaleFactor).toInt(),
                true
            )
        } else bitmap

        // Add watermark
        val canvas = Canvas(scaled)
        val paint = Paint().apply {
            color = Color.argb(128, 255, 153, 51) // Semi-transparent CIPHER orange
            textSize = scaled.height * 0.04f
            isAntiAlias = true
            setShadowLayer(4f, 2f, 2f, Color.BLACK)
        }
        val text = "CIPHER"
        val textWidth = paint.measureText(text)
        canvas.drawText(
            text,
            scaled.width - textWidth - 16f,
            scaled.height - 16f,
            paint
        )

        return scaled
    }

    private fun saveToGallery(bitmap: Bitmap, context: Context): String? {
        return try {
            val filename = "CIPHER_${System.currentTimeMillis()}.png"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Scoped storage
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/CIPHER")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val uri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                ) ?: return null

                context.contentResolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }

                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, values, null, null)

                uri.toString()
            } else {
                // Legacy storage
                val dir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "CIPHER"
                )
                dir.mkdirs()
                val file = File(dir, filename)
                FileOutputStream(file).use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                // Notify gallery
                context.sendBroadcast(
                    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file))
                )
                file.absolutePath
            }
        } catch (e: Exception) {
            null
        }
    }

    fun shareScreenshot(context: Context, path: String) {
        try {
            val uri = if (path.startsWith("content://")) {
                Uri.parse(path)
            } else {
                Uri.fromFile(File(path))
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to share", Toast.LENGTH_SHORT).show()
        }
    }
}
