package com.instacentner.instacentner.ui.imagesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.instacentner.instacentner.R
import com.instacentner.instacentner.di.App
import com.instacentner.instacentner.ui.CHARS_COUNT_TO_START_SEARCH
import com.instacentner.instacentner.ui.COLUMN_NUMBER
import com.instacentner.instacentner.ui.DISPLAY_WIDTH
import com.instacentner.instacentner.ui.TIME_WAIT_TO_START_SEARCH
import com.instacentner.instacentner.ui.common.ImageGridAdapter
import com.instacentner.instacentner.ui.imagesearch.intents.FavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.intents.ImageSearchIntent
import com.instacentner.instacentner.ui.imagesearch.intents.UnfavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.viewstates.ImageSearchViewState
import com.instacentner.instacentner.ui.utils.ImageLoader
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_image_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class ImageSearchFragment : MviFragment<ImageSearchView, ImageSearchPresenter>(), ImageSearchView {
    @Inject
    @field:[Named(DISPLAY_WIDTH)]
    lateinit var displayWidth: java.lang.Integer
    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>
    @Inject
    lateinit var presenter: ImageSearchPresenter

    override fun createPresenter() = presenter

    private lateinit var adapter: ImageGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_image_search, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ImageGridAdapter(
            imageLoader,
            displayWidth.toInt() / COLUMN_NUMBER
        )
        recycler_RecyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(
                COLUMN_NUMBER, StaggeredGridLayoutManager.VERTICAL
            )
            it.adapter = adapter
        }
    }

    override fun intentSearch(): Observable<ImageSearchIntent> =
        search_SearchView.queryTextChanges()
            .skip(2)
            .filter { it.length >= CHARS_COUNT_TO_START_SEARCH || it.isEmpty() }
            .debounce(TIME_WAIT_TO_START_SEARCH, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .map { ImageSearchIntent(it.toString()) }

    override fun intentFavouriteImage(): Observable<FavouriteImageIntent> =
        adapter.favouriteImageIntent

    override fun intentUnfavouriteImage(): Observable<UnfavouriteImageIntent> =
        adapter.unfavouriteImageIntent

    override fun intentWatchImageInfo(imageId: Int) {}

    override fun render(viewState: ImageSearchViewState) {
        with(viewState) {
            progress_ProgressBar.visibility = if (loading) View.VISIBLE else View.GONE
            message_TextView.text = message
            adapter.submitList(imageList)
        }
    }
}