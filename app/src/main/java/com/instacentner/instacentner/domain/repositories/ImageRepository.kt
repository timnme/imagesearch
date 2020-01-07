package com.instacentner.instacentner.domain.repositories

import com.instacentner.instacentner.domain.domainmodels.Image
import io.reactivex.Single

interface ImageRepository {
    fun searchImages(query: String): Single<List<Image>>
    fun getFavouriteImages(): Single<List<Image>>
    fun favouriteImage(image: Image): Single<Image>
    fun unfavouriteImage(imageId: Int): Single<Image>
}