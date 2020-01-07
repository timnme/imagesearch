package com.instacentner.instacentner.domain.usecases

import com.instacentner.instacentner.domain.domainmodels.Image
import io.reactivex.Single

interface ImageSearchUseCase {
    fun execute(query: String): Single<List<Image>>
}