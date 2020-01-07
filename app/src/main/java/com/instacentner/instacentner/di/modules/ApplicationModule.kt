package com.instacentner.instacentner.di.modules

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import com.instacentner.instacentner.di.App
import com.instacentner.instacentner.di.ForApplication
import com.instacentner.instacentner.ui.DISPLAY_WIDTH
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.windowManager
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: Application
) {
    @Provides
    @Singleton
    fun provideApplication(): App {
        return application as App
    }

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Named(DISPLAY_WIDTH)
    fun provideDisplayWidth(app: App): Int {
        return DisplayMetrics().let {
            app.windowManager.defaultDisplay.getMetrics(it)
            it.widthPixels
        }
    }
}
