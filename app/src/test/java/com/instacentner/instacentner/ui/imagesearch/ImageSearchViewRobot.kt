package com.instacentner.instacentner.ui.imagesearch

import com.instacentner.instacentner.ui.imagesearch.intents.FavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.intents.ImageSearchIntent
import com.instacentner.instacentner.ui.imagesearch.intents.UnfavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.viewstates.ImageSearchViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit

class ImageSearchViewRobot(presenter: ImageSearchPresenter) {
    private val searchSubject = PublishSubject.create<ImageSearchIntent>()
    private val favouriteImageSubject = PublishSubject.create<FavouriteImageIntent>()
    private val unfavouriteImageSubject = PublishSubject.create<UnfavouriteImageIntent>()

    private val renderEvents = CopyOnWriteArrayList<ImageSearchViewState>()
    private val renderEventSubject = ReplaySubject.create<ImageSearchViewState>()

    private val view = object : ImageSearchView {
        override fun intentSearch(): Observable<ImageSearchIntent> =
            searchSubject

        override fun intentFavouriteImage(): Observable<FavouriteImageIntent> =
            favouriteImageSubject

        override fun intentUnfavouriteImage(): Observable<UnfavouriteImageIntent> =
            unfavouriteImageSubject

        override fun intentWatchImageInfo(imageId: Int) {
        }

        override fun render(viewState: ImageSearchViewState) {
            renderEvents.add(viewState)
            renderEventSubject.onNext(viewState)
        }
    }

    init {
        presenter.attachView(view)
    }

    fun fireSearchImageIntent(intent: ImageSearchIntent) {
        searchSubject.onNext(intent)
    }

    fun assertViewStateRendered(vararg expectedSearchViewStates: ImageSearchViewState) {
        val eventsCount = expectedSearchViewStates.size

        renderEventSubject.take(eventsCount.toLong())
            .timeout(1, TimeUnit.SECONDS)
            .blockingSubscribe()

        if (renderEventSubject.values.size > eventsCount) {
            Assert.fail(
                "Expected $eventsCount events, but there were ${renderEventSubject.values.size} events in total"
            )
        }

        Assert.assertEquals(
            arrayListOf(*expectedSearchViewStates),
            renderEvents
        )
    }
}
