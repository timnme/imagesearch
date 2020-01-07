package com.instacentner.instacentner.domain.usecases.impl

import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.repositories.ImageRepository
import com.instacentner.instacentner.domain.usecases.ImageSearchUseCase
import io.reactivex.Single
import javax.inject.Inject

class ImageSearchUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : ImageSearchUseCase {
    override fun execute(query: String): Single<List<Image>> {
        return repository.searchImages(query)
    }
}