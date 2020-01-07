package com.instacentner.instacentner.ui.favourites

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface FavouriteImagesView : MvpView {
    fun intentLoadFavouriteImages(): Observable<LoadFavouriteImagesIntent>
//    fun intentFavouriteImage()
//    fun intentUnfavouriteImage()
//    fun intentWatchImageInfo(image: Int)

    fun render(viewState: FavouriteImagesViewState)
}