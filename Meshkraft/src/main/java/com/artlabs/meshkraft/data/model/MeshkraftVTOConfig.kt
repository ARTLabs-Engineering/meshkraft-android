package com.artlabs.meshkraft.data.model

/**
 * Configuration class for Meshkraft VTO sessions
 * This class allows customization of the VTO experience with various options
 * 
 * @param fontFamily Custom font family for the VTO interface
 * @param disableUI Whether to disable the default VTO UI elements
 * @param showBanner Whether to show the add to cart banner/button
 * @param bannerButtonText Custom text for the add to cart button
 * @param logoUrl URL for custom logo to display in the VTO interface
 * @param useWatermark Whether to show watermark in the VTO experience
 * @param accentColor Accent color for the VTO interface (hex format, e.g., "#007AFF")
 */
data class MeshkraftVTOConfig(
    val fontFamily: String? = null,
    val disableUI: Boolean? = null,
    val showBanner: Boolean? = null,
    val bannerButtonText: String? = null,
    val logoUrl: String? = null,
    val useWatermark: Boolean? = null,
    val accentColor: String? = null
)
