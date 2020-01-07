package com.instacentner.instacentner.domain.usecases.impl

import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.repositories.ImageRepository
import com.instacentner.instacentner.domain.usecases.ImageGetFavouritesUseCase
import io.reactivex.Single
import javax.inject.Inject

class ImageGetFavouritesUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : ImageGetFavouritesUseCase {
    override fun execute(): Single<List<Image>> {
        return repository.getFavouriteImages()
    }
}