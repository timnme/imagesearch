package com.instacentner.instacentner.di

import com.instacentner.instacentner.di.modules.*
import com.instacentner.instacentner.ui.MainActivity
import com.instacentner.instacentner.ui.favourites.FavouriteImagesFragment
import com.instacentner.instacentner.ui.imagesearch.ImageSearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class,
        DomainModule::class,
        UiModule::class,
        UtilsModule::class
    ]
)
interface ApplicationComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(imageSearchFragment: ImageSearchFragment)
    fun inject(favouriteImagesFragment: FavouriteImagesFragment)
}