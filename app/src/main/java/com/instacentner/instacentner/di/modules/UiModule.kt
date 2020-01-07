package com.instacentner.instacentner.di.modules

import android.widget.ImageView
import com.instacentner.instacentner.ui.utils.ImageLoader
import com.instacentner.instacentner.ui.utils.impl.ImageLoaderGlideImpl
import dagger.Module
import dagger.Provides

@Module
class UiModule {
    @Provides
    fun provideImageLoader(): ImageLoader<ImageView> {
        return ImageLoaderGlideImpl()
    }
}
