package com.mertgolcu.meshkraft

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.ar.core.ArCoreApk
import com.mertgolcu.meshkraft.AR.Product
import com.mertgolcu.meshkraft.data.model.Mode
import com.mertgolcu.meshkraft.data.model.Model
import com.mertgolcu.meshkraft.data.network.service.Api
import com.mertgolcu.meshkraft.data.network.utlis.Result
import com.mertgolcu.meshkraft.data.network.utlis.callRequest

/**
 * @author Mert Gölcü
 * @date 11.09.2021
 */
object Meshkraft {
    private var packageName = STANDARD_PACKAGE
    private var currentMode = Mode.PREFERRED_3D
    private var isArSupported = false

    /** fail to session */
    var onFail: ((message: String) -> Unit)? = null

    /** session on loading */
    var onLoading: (() -> Unit?)? = null

    /** session finished */
    var onFinish: (() -> Unit)? = null

    /** Set Api key for access */
    fun setApiKey(apiKey: String) {
        Api.setToken(apiKey)
    }

    /**
     * if ArCore Supported can use [AR_ONLY_PACKAGE]
     */
    fun setPackageName(packageName: String) {
        this.packageName = packageName
    }

    /**
     * mode is intent mode [Mode]
     */
    fun setMode(mode: Mode) {
        currentMode = mode
    }

    /**
     * Ar support actually doesn't matter but u can control it for
     * package and mode usage
     */
    fun isArSupported(context: Context): Boolean {
        // find is supported
        val availability = ArCoreApk.getInstance().checkAvailability(context)
        isArSupported = availability.isSupported
        return isArSupported
    }

    /**
     * Ar Session Start
     * @param context : Ar Session intent to ARCore so need context for intent
     * @param sku : Product SKU
     * @throws onFail
     */
    fun startARSession(context: Context, sku: String) {
        onLoading?.invoke()
        val request = Api.service.getProduct(sku)
        callRequest(request) {
            when (it) {
                is Result.Error -> {
                    onFail?.invoke(it.exception.message!!)
                }
                is Result.Success -> {
                    val name = it.data?.name
                    val model = loadModel(it.data!!)
                    if (model != null) {
                        startARCore(
                            context = context,
                            url = model.url!!,
                            title = name,
                        )
                    } else
                        onFail?.invoke("Model is Null")
                }
                else -> {
                    /** Nothing **/
                }
            }
        }
    }

    private fun loadModel(data: Product): Model? {
        if (data.assets != null) {
            if (data.assets.glb != null) {
                return data.assets.glb
            }
        }
        return null
    }

    private fun startARCore(context: Context, url: String, title: String? = null) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val uriBuilder = Uri.parse(VIEWER_URL).buildUpon()
            uriBuilder.appendQueryParameter(FILE, url)
            uriBuilder.appendQueryParameter(MODE, currentMode.toString())
            if (title != null) uriBuilder.appendQueryParameter(TITLE, title)

            intent.data = uriBuilder.build()
            intent.setPackage(packageName)
            context.startActivity(intent)
            onFinish?.invoke()
        } catch (ex: Exception) {
            onFail?.invoke(ex.message!!)
        }
    }
}