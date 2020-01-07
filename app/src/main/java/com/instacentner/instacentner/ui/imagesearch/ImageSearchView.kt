package com.instacentner.instacentner.ui.imagesearch

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.instacentner.instacentner.ui.imagesearch.intents.FavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.intents.ImageSearchIntent
import com.instacentner.instacentner.ui.imagesearch.intents.UnfavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.viewstates.ImageSearchViewState
import io.reactivex.Observable

interface ImageSearchView : MvpView {
    fun intentSearch(): Observable<ImageSearchIntent>
    fun intentFavouriteImage(): Observable<FavouriteImageIntent>
    fun intentUnfavouriteImage(): Observable<UnfavouriteImageIntent>
    fun intentWatchImageInfo(imageId: Int)

    fun render(viewState: ImageSearchViewState)
}