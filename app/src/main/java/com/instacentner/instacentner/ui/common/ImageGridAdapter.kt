package com.instacentner.instacentner.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instacentner.instacentner.R
import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.ui.imagesearch.intents.FavouriteImageIntent
import com.instacentner.instacentner.ui.imagesearch.intents.UnfavouriteImageIntent
import com.instacentner.instacentner.ui.utils.ImageLoader
import io.reactivex.subjects.PublishSubject

class ImageGridAdapter(
    private val imageLoader: ImageLoader<ImageView>,
    private val imageWidthBasedOnDisplay: Int
) : ListAdapter<Image, ImageGridAdapter.ImageViewHolder>(
    object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }
) {
    val favouriteImageIntent = PublishSubject.create<FavouriteImageIntent>()
    val unfavouriteImageIntent = PublishSubject.create<UnfavouriteImageIntent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.grid_item_image, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.imageFavourite.setOnClickListener {
            if (item.isFavourite) {
                unfavouriteImageIntent.onNext(UnfavouriteImageIntent(item.id))
            } else {
                favouriteImageIntent.onNext(FavouriteImageIntent(item))
            }
        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.image_preview)
        val imageFavourite: ImageView = itemView.findViewById(R.id.favourite)

        fun bind(previewImage: Image) {
            with(imageView) {
                layoutParams.width = imageWidthBasedOnDisplay
                layoutParams.height =
                    imageWidthBasedOnDisplay * previewImage.previewImageHeight / previewImage.previewImageWidth
                requestLayout()
            }
            imageLoader.load(previewImage.previewUrl, imageView)
            imageFavourite.setImageResource(
                if (previewImage.isFavourite) R.drawable.ic_favorite else R.drawable.ic_non_favorite
            )
        }
    }
}