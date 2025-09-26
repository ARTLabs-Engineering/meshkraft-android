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
    fun onEvent(event: String, message: String) {
        when (event) {
            "close-event" -> {
                (context as? Activity)?.runOnUiThread {
                    context.finish()
                }
            }
            "add-to-cart" -> {
                (context as? Activity)?.runOnUiThread {
                    handleAddToCartEvent()
                }
            }
        }
    }
    
    private fun handleAddToCartEvent() {
        try {
            // Get the product SKU from the intent
            val activity = context as? WebViewActivity
            val productSKU = activity?.intent?.getStringExtra("productSKU") ?: ""
            
            // Call the delegate
            Meshkraft.vtoDelegate?.vtoAddToCartClicked(productSKU)
            
            // Close the VTO session
            activity?.finish()
            
        } catch (e: Exception) {
            android.util.Log.e("WebViewActivity", "Error handling add-to-cart event: ${e.message}")
        }
    }
}

