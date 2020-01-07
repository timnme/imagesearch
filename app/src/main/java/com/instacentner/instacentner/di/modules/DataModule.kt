package com.instacentner.instacentner.di.modules

import android.content.Context
import androidx.room.Room
import com.instacentner.instacentner.data.database.ImageDao
import com.instacentner.instacentner.data.database.room.AppDatabase
import com.instacentner.instacentner.data.network.ImageSearchApiService
import com.instacentner.instacentner.data.repositories.ImageRepositoryImpl
import com.instacentner.instacentner.data.utils.DataModelConverter
import com.instacentner.instacentner.data.utils.impl.DataModelConverterImpl
import com.instacentner.instacentner.di.ForApplication
import com.instacentner.instacentner.domain.repositories.ImageRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideImageRepository(
        dao: ImageDao,
        service: ImageSearchApiService,
        converter: DataModelConverter
    ): ImageRepository {
        return ImageRepositoryImpl(dao, service, converter)
    }

    @Provides
    @Singleton
    fun provideDb(@ForApplication context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideImageDao(db: AppDatabase): ImageDao {
        return db.imageDao()
    }

    @Provides
    @Singleton
    fun provideApiService(): ImageSearchApiService {
        val interceptor = HttpLoggingInterceptor { message ->
            Timber.i(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ImageSearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataModelConverter(): DataModelConverter {
        return DataModelConverterImpl()
    }
}
