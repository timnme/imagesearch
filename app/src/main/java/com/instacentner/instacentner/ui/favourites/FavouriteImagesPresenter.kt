package com.instacentner.instacentner.ui.favourites

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.instacentner.instacentner.domain.usecases.ImageGetFavouritesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteImagesPresenter @Inject constructor(
    private val imageGetFavouritesUseCase: ImageGetFavouritesUseCase
) : MviBasePresenter<FavouriteImagesView, FavouriteImagesViewState>(
    FavouriteImagesViewState()
) {
    override fun bindIntents() {
        val load: Observable<FavouriteImagesViewState> =
            intent(FavouriteImagesView::intentLoadFavouriteImages)
                .switchMap(::generateViewState)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(load, FavouriteImagesView::render)
    }

    private fun generateViewState(intent: LoadFavouriteImagesIntent): Observable<FavouriteImagesViewState> {
        return imageGetFavouritesUseCase.execute()
            .toObservable()
            .map { result ->
                if (result.isEmpty()) {
                    FavouriteImagesViewState(
                        message = "No favourite images"
                    )
                } else {
                    FavouriteImagesViewState(
                        message = "",
                        imageList = result
                    )
                }
            }
            .startWith(
                FavouriteImagesViewState(
                    loading = true
                )
            )
            .onErrorReturn { error ->
                FavouriteImagesViewState(
                    message = """Error while loading:${"\n${error.message}"}"""
                )
            }
    }
}