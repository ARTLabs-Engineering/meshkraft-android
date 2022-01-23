package com.artlabs.meshkraft

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.artlabs.meshkraft.data.model.Mode
import com.artlabs.meshkraft.data.model.Product
import com.artlabs.meshkraft.data.network.service.Api
import com.artlabs.meshkraft.data.network.utlis.Result
import com.artlabs.meshkraft.data.network.utlis.callRequest

/**
 * @author Mert Gölcü
 * @date 22.01.2022
 */

/**
 * Show product AR file on Google ModelViewer with ARTLabs
 */
object Meshkraft {

    /** is have 2 package [STANDARD_PACKAGE] or [AR_ONLY_PACKAGE] **/
    private var packageName = STANDARD_PACKAGE

    /** Set Api key for access */
    fun setApiKey(apiKey: String) {
        Api.setToken(apiKey)
    }

    /**
     * @param packageName must be [STANDARD_PACKAGE] or [AR_ONLY_PACKAGE]
     */
    fun setPackageName(packageName: String) {
        if (packageName == STANDARD_PACKAGE || packageName == AR_ONLY_PACKAGE)
            this.packageName = packageName
    }

    /**
     * Start Ar Session without listener and mode.
     * @param context : Ar Session intent to ARCore so need context for intent
     * @param sku : product sku
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
     * @param context : Ar Session intent to ARCore so need context for intent
     * @param sku : Product Sku
     * @param mode : Select [Mode]
     * @param listener : [IMeshkraftState] for session info
     */
    fun startArSession(
        context: Context,
        sku: String,
        mode: Mode? = Mode.PREFERRED_3D,
        listener: IMeshkraftState
    ) {
        val state = object : ILoadState {
            override fun onLoading() {
                listener.onLoading()
            }

            override fun onFinish(data: LoadedData) {
                startARCore(context, data.url, data.name, mode, listener)
            }

            override fun onFail(message: String) {
                listener.onFail(message)
            }

        }
        load(sku, state)
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
    val request = Api.service.getProduct(sku)
    callRequest(request) {
        when (it) {
            is Result.Error -> {
                loadState.onFail(it.exception.message!!)
            }
            Result.Loading -> {
                loadState.onLoading()
            }
            is Result.Success -> {
                val url = getUrlFromResponse(it.data)
                if (url != EMPTY)
                    loadState.onFinish(
                        LoadedData(
                            url = url,
                            name = it.data?.name!!
                        )
                    )
                else
                    loadState.onFail("Model is not available")
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
    if (product != null) {
        if (product.models != null) {
            val firstOrNull = product.models.firstOrNull {
                it.type == FILE_TYPE
            }
            if (firstOrNull != null) {
                if (firstOrNull.file != null) {
                    if (firstOrNull.file.url != null) {
                        return firstOrNull.file.url
                    }
                }
            }
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
