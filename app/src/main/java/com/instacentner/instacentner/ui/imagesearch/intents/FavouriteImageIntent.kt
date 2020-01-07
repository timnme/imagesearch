package com.instacentner.instacentner.ui.imagesearch.intents

import com.instacentner.instacentner.domain.domainmodels.Image
import timber.log.Timber

data class FavouriteImageIntent(
    val image: Image
) {
    init {
        Timber.d("Intent: $this")
    }
}