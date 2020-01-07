package com.instacentner.instacentner.ui.imagesearch.intents

import timber.log.Timber

data class UnfavouriteImageIntent(
    val imageId: Int
) {
    init {
        Timber.d("Intent: $this")
    }
}