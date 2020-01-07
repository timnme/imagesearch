package com.instacentner.instacentner.domain.usecases

import com.instacentner.instacentner.domain.domainmodels.Image
import io.reactivex.Single

interface UnfavouriteImageUseCase {
    fun execute(imageId: Int): Single<Image>
}
