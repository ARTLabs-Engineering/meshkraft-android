package com.artlabs.meshkraft

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import java.io.*
import java.net.URL


class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    // Request code for camera permission
    private val CAMERA_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        webView = findViewById(R.id.web_view)

        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.javaScriptCanOpenWindowsAutomatically = true

            settings.setSupportZoom(false)
            settings.builtInZoomControls = false
            settings.displayZoomControls = false

            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

            addJavascriptInterface(JavaScriptInterface(this@WebViewActivity), "AndroidInterface")

            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.grant(request.resources)
                }
            }
        }

        val url = intent.getStringExtra("url")
        url?.let {
            webView.loadUrl(it)
        } ?: run {
            // Handle the case when the URL is not provided, e.g., show an error message
        }
    }

    override fun onDestroy() {
        // Remove the JavaScript interface and callbacks
        webView.removeJavascriptInterface("AndroidInterface")
        webView.webViewClient = object : WebViewClient() {}
        webView.webChromeClient = object : WebChromeClient() {}

        // Destroy the WebView
        webView.destroy()

        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted
                } else {
                    // Camera permission denied, inform the user or handle the situation accordingly
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}

class JavaScriptInterface(private val context: Context) {
    @JavascriptInterface
    fun onEvent(event: String) {
        if (event == "close-event") {
            (context as? Activity)?.runOnUiThread {
                context.finish()
            }
        }
    }

    @JavascriptInterface
    fun shareImage(imageUrl: String) {
        DownloadImageTask { bitmap ->
            val imageFile = createImageFile(bitmap)
            shareImageFile(imageFile)
        }.execute(imageUrl)
    }

    private fun createImageFile(bitmap: Bitmap): File {
        val filename = "vto_${System.currentTimeMillis()}.jpg"
        val outputStream: OutputStream

        val imageFile = File(context.cacheDir, filename)
        outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return imageFile
    }

    private fun shareImageFile(file: File) {
        val uri = Uri.fromFile(file)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    private class DownloadImageTask(private val onImageDownloaded: (Bitmap) -> Unit) :
        AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg params: String?): Bitmap {
            return try {
                val imageUrl = URL(params[0])
                BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
            } catch (e: IOException) {
                throw RuntimeException("Error downloading image", e)
            }
        }

        override fun onPostExecute(result: Bitmap) {
            onImageDownloaded(result)
        }
    }
}

