package com.instacentner.instacentner.domain.usecases.impl

import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.repositories.ImageRepository
import com.instacentner.instacentner.domain.usecases.FavouriteImageUseCase
import io.reactivex.Single
import javax.inject.Inject

class FavouriteImageUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : FavouriteImageUseCase {
    override fun execute(image: Image): Single<Image> {
        return repository.favouriteImage(image)
    }
}