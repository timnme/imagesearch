package com.instacentner.instacentner.ui.utils.impl

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.instacentner.instacentner.ui.utils.ImageLoader

class ImageLoaderGlideImpl : ImageLoader<ImageView> {
    override fun load(url: String, target: ImageView) {
        Glide.with(target.context)
            .load(url)
            .into(target)
    }
}