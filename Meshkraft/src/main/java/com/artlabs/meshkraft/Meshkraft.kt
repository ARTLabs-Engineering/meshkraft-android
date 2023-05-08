package com.artlabs.meshkraft

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.app.Activity
import android.util.Log


import com.artlabs.meshkraft.data.model.Mode
import com.artlabs.meshkraft.data.model.Product
import com.artlabs.meshkraft.data.model.StatPayload

import com.artlabs.meshkraft.data.network.service.Api
import com.artlabs.meshkraft.data.network.service.Events.sendAnalyticsEvent
import com.artlabs.meshkraft.data.network.utlis.Result
import com.artlabs.meshkraft.data.network.utlis.callRequest

/**
 * @author Mert Gölcü
 * @date 22.01.2022
 */

enum class ArCoreAvailability {
    SUPPORTED,
    UNSUPPORTED_DEVICE_NOT_CAPABLE,
    SUPPORTED_APK_TOO_OLD,
    UNKNOWN_ERROR,
    INSTALL_REQUESTED
}

/**
 * Show product AR file on Google ModelViewer with ARTLabs
 */
object Meshkraft {

    /** is have 2 package [STANDARD_PACKAGE] or [AR_ONLY_PACKAGE] **/
    private var packageName = STANDARD_PACKAGE
    private var apiKey: String? = null

    /**
     * Set API Key and initialize SDK.
     * @param apiKey : Your API Key
     */
    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
        Api.setToken(apiKey)

        // Send INIT event
        val initPayload = StatPayload(
            event = StatPayload.MeshkraftEvent(
                key = "INIT",
                segmentation = StatPayload.MeshkraftEvent.Segmentation(sku = null)
            )
        )
        sendAnalyticsEvent(initPayload)
    }

    /** Get Api key for access */
    fun getApiKey(): String? {
        return apiKey
    }

    /**
     * @param packageName must be [STANDARD_PACKAGE] or [AR_ONLY_PACKAGE]
     */
    fun setPackageName(packageName: String) {
        if (packageName == STANDARD_PACKAGE || packageName == AR_ONLY_PACKAGE)
            this.packageName = packageName
    }

//    /**
//     * Check if the device supports ARCore and if ARCore is installed.
//     * @param activity: Activity needed for checking ARCore support and requesting install
//     * @param userRequestedInstall: Boolean to trigger installation if ARCore is not installed
//     * @return Pair<Boolean, ArCoreAvailability> where the first value indicates if AR is supported and the second value is the ArCoreAvailability status
//     */
    fun isArSupported(activity: Activity, userRequestedInstall: Boolean): Pair<Boolean, ArCoreAvailability> {
        return Pair(true, ArCoreAvailability.SUPPORTED)
//        val availability = ArCoreApk.getInstance().checkAvailability(activity)
//        val arSupportPayload = StatPayload(
//            event = StatPayload.MeshkraftEvent(
//                key = "ARCORE_SUPPORT",
//                segmentation = StatPayload.MeshkraftEvent.Segmentation(availability = availability.toString(), sku = null)
//            )
//        )
//        sendAnalyticsEvent(arSupportPayload)
//
//        return when (availability) {
//            ArCoreApk.Availability.SUPPORTED_INSTALLED -> {
//
//            }
//            ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED -> {
//                try {
//                    val installStatus = ArCoreApk.getInstance().requestInstall(activity, userRequestedInstall)
//                    when (installStatus) {
//                        ArCoreApk.InstallStatus.INSTALLED -> Pair(true, ArCoreAvailability.SUPPORTED)
//                        ArCoreApk.InstallStatus.INSTALL_REQUESTED -> Pair(false, ArCoreAvailability.INSTALL_REQUESTED)
//                        else -> Pair(false, ArCoreAvailability.UNKNOWN_ERROR)
//                    }
//                } catch (e: Exception) {
//                    Pair(false, ArCoreAvailability.UNKNOWN_ERROR)
//                }
//            }
//            ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD -> {
//                Pair(false, ArCoreAvailability.SUPPORTED_APK_TOO_OLD)
//            }
//            ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE -> {
//                Pair(false, ArCoreAvailability.UNSUPPORTED_DEVICE_NOT_CAPABLE)
//            }
//            else -> {
//                Pair(false, ArCoreAvailability.UNKNOWN_ERROR)
//            }
//        }
    }


    /**
     * Start AR Session without listener and mode.
     * @param context : AR Session intent to ARCore so need context for intent
     * @param sku : product SKU
     */
    fun startBasicArSession(
        context: Context,
        sku: String
    ) {
        load(sku, object : ILoadState {
            override fun onLoading() {
                // nothing
            }

            override fun onFinish(data: LoadedData) {
                // Send START_AR event
                val startArPayload = StatPayload(
                    event = StatPayload.MeshkraftEvent(
                        key = "START_AR",
                        segmentation = StatPayload.MeshkraftEvent.Segmentation(sku = sku)
                    )
                )
                sendAnalyticsEvent(startArPayload)

                startARCore(
                    context,
                    data.url, data.name,
                )
            }

            override fun onFail(message: String) {
                // nothing
            }

        })
    }

    /**
     * Start Ar Session with listener and mode.
     *
     * @param context : AR Session intent to ARCore so need context for intent
     * @param sku : Product SKU
     * @param mode : Select [Mode]
     * @param listener : [IMeshkraftState] for session info
     */
    fun startArSession(
        context: Context,
        sku: String,
        mode: Mode? = Mode.PREFERRED_3D,
        listener: IMeshkraftState
    ) {
        Log.i("MeshkraftAR", "startArSession")
        val state = object : ILoadState {
            override fun onLoading() {
                Log.i("MeshkraftAR", "onLoading")
                listener.onLoading()
            }

            override fun onFinish(data: LoadedData) {
                Log.i("MeshkraftAR", "onFinish")
                // Send START_AR event
                val startArPayload = StatPayload(
                    event = StatPayload.MeshkraftEvent(
                        key = "START_AR",
                        segmentation = StatPayload.MeshkraftEvent.Segmentation(sku = sku)
                    )
                )
                sendAnalyticsEvent(startArPayload)

                startARCore(context, data.url, data.name, mode, listener)
                listener.onFinish()
            }

            override fun onFail(message: String) {
                Log.i("MeshkraftAR", "onFail")
                listener.onFail(message)
            }

        }
        load(sku, state)
    }

    /**
     * Start VTO Session
     * @param context: Context needed to start com.artlabs.meshkraft.WebViewActivity
     * @param sku: Product SKU
     */
    fun startVTOSession(context: Context, sku: String) {
        apiKey?.let { key ->
            val url = "https://viewer.artlabs.ai/embed/vto?sku=$sku&token=$key"
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        } ?: run {
            // Handle case when API key is not set, e.g., show an error message
        }
    }

    /**
     * Start ARCore with intent
     *
     * @param context
     * @param url
     * @param title
     * @param mode
     * @param listener
     */
    private fun startARCore(
        context: Context,
        url: String,
        title: String? = null,
        mode: Mode? = Mode.PREFERRED_3D,
        listener: IMeshkraftState? = null
    ) {
        try {
            Log.i("MeshkraftAR", "Starting ARCore")
            val intent = Intent(Intent.ACTION_VIEW)
            val uriBuilder = Uri.parse(VIEWER_URL).buildUpon()
            uriBuilder.appendQueryParameter(FILE, url)
            uriBuilder.appendQueryParameter(MODE, mode.toString())
            if (title != null) uriBuilder.appendQueryParameter(TITLE, title)

            intent.data = uriBuilder.build()
            intent.setPackage(packageName)
            context.startActivity(intent)
            listener?.onFinish()
        } catch (ex: Exception) {
            Log.i("MeshkraftAR", "ARCore Error")
            Log.i("MeshkraftAR", ex.message!!)
            listener?.onFail(ex.message!!)
        }
    }

}

/**
 * Session states
 */
interface IMeshkraftState {
    /**
     * on loading, its meaning is network request waiting...
     */
    fun onLoading()

    /**
     * @param message is exception string
     */
    fun onFail(message: String)

    /**
     * session finished
     */
    fun onFinish()
}

/**
 * interface for load extension
 */
private interface ILoadState {
    /**
     * loadin on network request
     */
    fun onLoading()

    /**
     * on response and not null
     * @param data
     */
    fun onFinish(data: LoadedData)

    /**
     * network error or null file
     * @param message is exception of request
     */
    fun onFail(message: String)
}

/**
 * network request and model url extension for AR session
 */
private fun load(sku: String, loadState: ILoadState) {
    Log.i("MeshkraftAR", "load")
    val request = Api.service.getProduct(sku)

    callRequest(request) {
        Log.i("MeshkraftAR", "callRequest")
        when (it) {
            is Result.Error -> {
                Log.i("MeshkraftAR", "Result.Error")
                loadState.onFail(it.exception.message!!)
            }
            Result.Loading -> {
                Log.i("MeshkraftAR", "Result.Loading")
                loadState.onLoading()
            }
            is Result.Success -> {
                Log.i("MeshkraftAR", "Result.Success")
                it.data?.name?.let { it1 -> Log.i("MeshkraftAR", "name :: " + it1) }
                val url = getUrlFromResponse(it.data)
                Log.i("MeshkraftAR", "URL is :: " + url)
                if (url != EMPTY)
                {
                    Log.i("MeshkraftAR", "name is :: " + it.data?.name!!)

                    loadState.onFinish(
                        LoadedData(
                            url = url,
                            name = it.data?.name!!
                        )
                    )
                }
                else {
                    Log.i("MeshkraftAR", "URL is empty")
                    loadState.onFail("3D Model is not available")
                }
            }
        }
    }
}

/**
 * response to url or [EMPTY]
 *
 * why @return [EMPTY] instead of null ?
 *  > so many null checks in it and hard to handle it
 */
private fun getUrlFromResponse(product: Product?): String {
    Log.i("MeshkraftAR", "getUrlFromResponse")

    if (product != null) {

        Log.i("MeshkraftAR", "Product is not null")
        Log.i("MeshkraftAR", product.assets.toString())
        if (product.assets?.glb?.url != null) {
            return product.assets.glb.url
        }
    }
    return EMPTY
}

/**
 * load extension returning data
 */
private data class LoadedData(
    val url: String,
    val name: String
)
