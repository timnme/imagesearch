package com.instacentner.instacentner.ui.imagesearch.viewstates

import com.instacentner.instacentner.domain.domainmodels.Image

sealed class ImageSearchViewStateChanges

object ImageSearchNotStarted : ImageSearchViewStateChanges()

data class ImageSearchInProgress(
    val query: String
) : ImageSearchViewStateChanges()

data class ImageSearchResultEmpty(
    val query: String
) : ImageSearchViewStateChanges()

data class ImageSearchResultSuccess(
    val query: String,
    val result: List<Image>
) : ImageSearchViewStateChanges()

data class ImageSearchResultError(
    val query: String,
    val error: Throwable
) : ImageSearchViewStateChanges()

data class FavouriteImageResultSuccess(
    val result: Image
) : ImageSearchViewStateChanges()

data class UnfavouriteImageResultSuccess(
    val result: Image
) : ImageSearchViewStateChanges()