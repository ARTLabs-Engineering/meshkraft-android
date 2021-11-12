package com.artlabs.meshkraft.data.model

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */

/**
 * Scene Viewer Mode
 * */
enum class Mode {
    /**
     * Scene Viewer displays the model in 3D mode with a View in your space button.
     */
    PREFERRED_3D {
        override fun toString() = "3d_preferred"
    },

    /**
     * Scene Viewer launches with the model displayed in 3D mode,
     * even if Google Play Services for AR is present on the device.
     * The View in your space button is never shown.
     */
    ONLY_3D {
        override fun toString() = "3d_only"
    },

    /**
     * Scene Viewer launches in AR native mode as the entry mode.
     * The user is given the option to switch between AR and 3D modes via the
     * View in your space and View in 3D buttons.
     */
    AR_PREFERRED {
        override fun toString() = "ar_preferred"
    },

    /**
     * You should launch via an explicit Android intent to com.google.ar.core.
     * @note PACKAGE_NAME is not com.google.ar.core. or isArSupport is false don't use
     */
    AR_ONLY {
        override fun toString() = "ar_only"
    }
}