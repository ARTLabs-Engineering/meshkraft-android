package com.artlabs.meshkraft

/**
 * Delegate interface for handling VTO events
 * Implement this interface to receive callbacks when users interact with VTO features
 */
interface MeshkraftVTODelegate {
    /**
     * Called when the user clicks the "Add to Cart" button in the VTO experience
     * 
     * @param productSKU The SKU of the product being added to cart
     */
    fun vtoAddToCartClicked(productSKU: String)
}
