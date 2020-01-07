package com.instacentner.instacentner.ui.imagesearch.intents

import timber.log.Timber

data class ImageSearchIntent(
    val query: String
) {
    init {
        Timber.d("Intent: $this")
    }
}