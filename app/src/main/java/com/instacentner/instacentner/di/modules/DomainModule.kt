package com.instacentner.instacentner.di.modules

import com.instacentner.instacentner.domain.repositories.ImageRepository
import com.instacentner.instacentner.domain.usecases.FavouriteImageUseCase
import com.instacentner.instacentner.domain.usecases.ImageGetFavouritesUseCase
import com.instacentner.instacentner.domain.usecases.ImageSearchUseCase
import com.instacentner.instacentner.domain.usecases.UnfavouriteImageUseCase
import com.instacentner.instacentner.domain.usecases.impl.FavouriteImageUseCaseImpl
import com.instacentner.instacentner.domain.usecases.impl.ImageGetFavouritesUseCaseImpl
import com.instacentner.instacentner.domain.usecases.impl.ImageSearchUseCaseImpl
import com.instacentner.instacentner.domain.usecases.impl.UnfavouriteImageUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideImageSearchUseCase(repository: ImageRepository): ImageSearchUseCase {
        return ImageSearchUseCaseImpl(repository)
    }

    @Provides
    fun provideImageGetFavouritesUseCase(repository: ImageRepository): ImageGetFavouritesUseCase {
        return ImageGetFavouritesUseCaseImpl(repository)
    }

    @Provides
    fun provideFavouriteImageUseCase(repository: ImageRepository): FavouriteImageUseCase {
        return FavouriteImageUseCaseImpl(repository)
    }

    @Provides
    fun provideUnfavouriteImageUseCase(repository: ImageRepository): UnfavouriteImageUseCase {
        return UnfavouriteImageUseCaseImpl(repository)
    }
}
