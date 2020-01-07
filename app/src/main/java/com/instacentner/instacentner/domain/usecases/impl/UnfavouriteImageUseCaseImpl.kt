package com.instacentner.instacentner.domain.usecases.impl

import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.repositories.ImageRepository
import com.instacentner.instacentner.domain.usecases.UnfavouriteImageUseCase
import io.reactivex.Single
import javax.inject.Inject

class UnfavouriteImageUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : UnfavouriteImageUseCase {
    override fun execute(imageId: Int): Single<Image> {
        return repository.unfavouriteImage(imageId)
    }
}