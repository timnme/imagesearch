package com.instacentner.instacentner.ui.favourites

import com.instacentner.instacentner.domain.domainmodels.Image
import timber.log.Timber

data class FavouriteImagesViewState(
    val loading: Boolean = false,
    val message: String = "",
    val imageList: List<Image>? = null
) {
    init {
        Timber.d("ViewState: $this")
    }
}