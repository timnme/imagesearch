package com.instacentner.instacentner.ui.imagesearch.viewstates

import com.instacentner.instacentner.domain.domainmodels.Image
import timber.log.Timber

data class ImageSearchViewState(
    val loading: Boolean,
    val message: String,
    val imageList: List<Image>?
) {
    init {
        Timber.d("ViewState: $this")
    }
}