package com.instacentner.instacentner.domain.usecases

import com.instacentner.instacentner.domain.domainmodels.Image
import io.reactivex.Single

interface ImageGetFavouritesUseCase {
    fun execute(): Single<List<Image>>
}
