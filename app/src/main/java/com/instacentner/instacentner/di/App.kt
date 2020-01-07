package com.instacentner.instacentner.di

import android.app.Application
import com.instacentner.instacentner.di.modules.ApplicationModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class App : Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        component.inject(this)
    }
}