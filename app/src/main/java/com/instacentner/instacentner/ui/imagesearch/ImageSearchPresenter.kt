package com.instacentner.instacentner.ui.imagesearch

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.usecases.FavouriteImageUseCase
import com.instacentner.instacentner.domain.usecases.ImageSearchUseCase
import com.instacentner.instacentner.domain.usecases.UnfavouriteImageUseCase
import com.instacentner.instacentner.ui.imagesearch.intents.FavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.intents.ImageSearchIntent
import com.instacentner.instacentner.ui.imagesearch.intents.UnfavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.viewstates.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ImageSearchPresenter @Inject constructor(
    private val imageSearchUseCase: ImageSearchUseCase,
    private val favouriteImageUseCase: FavouriteImageUseCase,
    private val unfavouriteImageUseCase: UnfavouriteImageUseCase
) : MviBasePresenter<ImageSearchView, ImageSearchViewState>() {

    override fun bindIntents() {
        val search = intent(ImageSearchView::intentSearch)
            .switchMap(::searchImage)

        val favourite = intent(ImageSearchView::intentFavouriteImage)
            .switchMap(::favouriteImage)

        val unfavourite = intent(ImageSearchView::intentUnfavouriteImage)
            .switchMap(::unfavouriteImage)

        val allIntents = Observable
            .merge(search, favourite, unfavourite)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
            allIntents
                .scan(
                    ImageSearchViewState(
                        loading = false,
                        message = "Type a word into the box above",
                        imageList = null
                    ),
                    ::viewStateReducer
                )
                .distinctUntilChanged(),
            ImageSearchView::render
        )
    }

    private fun searchImage(intent: ImageSearchIntent): Observable<ImageSearchViewStateChanges> {
        val query = intent.query
        return if (query.isEmpty()) Observable.just(ImageSearchNotStarted)
        else imageSearchUseCase.execute(query)
            .toObservable()
            .map { searchResult ->
                if (searchResult.isEmpty())
                    ImageSearchResultEmpty(query = query)
                else
                    ImageSearchResultSuccess(
                        query = query,
                        result = searchResult
                    )
            }
            .startWith(
                ImageSearchInProgress(query = query)
            )
            .onErrorReturn {
                ImageSearchResultError(query = query, error = it)
            }
    }

    private fun favouriteImage(intent: FavouriteImageIntent): Observable<ImageSearchViewStateChanges> {
        return favouriteImageUseCase.execute(intent.image)
            .toObservable()
            .map(::FavouriteImageResultSuccess)
    }

    private fun unfavouriteImage(intent: UnfavouriteImageIntent): Observable<ImageSearchViewStateChanges> {
        return unfavouriteImageUseCase.execute(intent.imageId)
            .toObservable()
            .map(::UnfavouriteImageResultSuccess)
    }

    private fun viewStateReducer(
        previousState: ImageSearchViewState,
        changes: ImageSearchViewStateChanges
    ): ImageSearchViewState {
        return with(changes) {
            when (this) {
                is ImageSearchNotStarted -> previousState.copy(
                    loading = false,
                    message = "Type a word into the box above"
                )
                is ImageSearchInProgress -> previousState.copy(
                    loading = true,
                    message = """Search for "$query"""",
                    imageList = null
                )
                is ImageSearchResultEmpty -> previousState.copy(
                    loading = false,
                    message = """Nothing found for query "$query"""",
                    imageList = null
                )
                is ImageSearchResultSuccess -> previousState.copy(
                    loading = false,
                    message = """Results for "$query"""",
                    imageList = result
                )
                is ImageSearchResultError -> previousState.copy(
                    loading = false,
                    message = """Error while searching for "$query":${"\n${error.message}"}""",
                    imageList = null
                )
                is FavouriteImageResultSuccess -> {
                    val newImageList: ArrayList<Image> = ArrayList()
                    previousState.imageList?.forEach {
                        if (it.id != result.id) {
                            newImageList.add(it)
                        } else {
                            newImageList.add(result)
                        }
                    }
                    previousState.copy(
                        loading = false,
                        message = "Image ${result.id} added to favourites",
                        imageList = newImageList
                    )
                }
                is UnfavouriteImageResultSuccess -> {
                    val newImageList: ArrayList<Image> = ArrayList()
                    previousState.imageList?.forEach {
                        if (it.id != result.id) {
                            newImageList.add(it)
                        } else {
                            newImageList.add(result)
                        }
                    }
                    previousState.copy(
                        loading = false,
                        message = "Image ${result.id} removed from favourites",
                        imageList = newImageList
                    )
                }
            }
        }
    }
}