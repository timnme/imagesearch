package com.instacentner.instacentner.domain.usecases

import com.instacentner.instacentner.domain.domainmodels.Image
import io.reactivex.Single

interface FavouriteImageUseCase {
    fun execute(image: Image): Single<Image>
}
