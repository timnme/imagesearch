package com.instacentner.instacentner.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.instacentner.instacentner.R
import com.instacentner.instacentner.di.App
import com.instacentner.instacentner.ui.COLUMN_NUMBER
import com.instacentner.instacentner.ui.DISPLAY_WIDTH
import com.instacentner.instacentner.ui.common.ImageGridAdapter
import com.instacentner.instacentner.ui.utils.ImageLoader
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_favourite_images.*
import javax.inject.Inject
import javax.inject.Named

class FavouriteImagesFragment : MviFragment<FavouriteImagesView, FavouriteImagesPresenter>(),
    FavouriteImagesView {
    @Inject
    @field:[Named(DISPLAY_WIDTH)]
    lateinit var displayWidth: java.lang.Integer
    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>
    @Inject
    lateinit var presenter: FavouriteImagesPresenter

    override fun createPresenter() = presenter

    private lateinit var adapter: ImageGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_favourite_images, container, false
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
        intentLoadFavouriteImages()
    }

    override fun intentLoadFavouriteImages(): Observable<LoadFavouriteImagesIntent> =
        Observable.create<LoadFavouriteImagesIntent> {
            if (!it.isDisposed) it.onNext(LoadFavouriteImagesIntent())
        }

    override fun render(viewState: FavouriteImagesViewState) {
        with(viewState) {
            progress_ProgressBar.visibility = if (loading) View.VISIBLE else View.GONE
            message_TextView.text = message
            adapter.submitList(imageList)
        }
    }
}