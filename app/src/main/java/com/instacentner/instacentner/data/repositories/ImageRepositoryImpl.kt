package com.instacentner.instacentner.data.repositories

import com.instacentner.instacentner.data.database.ImageDao
import com.instacentner.instacentner.data.network.ImageSearchApiService
import com.instacentner.instacentner.data.utils.DataModelConverter
import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.repositories.ImageRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ImageRepositoryImpl(
    private val dao: ImageDao,
    private val service: ImageSearchApiService,
    private val converter: DataModelConverter
) : ImageRepository {
    override fun searchImages(query: String): Single<List<Image>> {
        return service.searchImages(query = query)
            .subscribeOn(Schedulers.io())
            .map { apiResponse ->
                apiResponse.images.map { apiImage ->
                    converter.toDomain(apiImage, checkIfFavourite(apiImage.id))
                }
            }
    }

    private fun checkIfFavourite(id: Int): Boolean = dao.getById(id) != null

    override fun getFavouriteImages(): Single<List<Image>> {
        return dao.getFavourites()
            .subscribeOn(Schedulers.io())
            .map { favourites ->
                favourites.map { favouriteImage -> converter.toDomain(favouriteImage, true) }
            }
    }

    override fun favouriteImage(image: Image): Single<Image> {
        dao.insert(converter.toDb(image))
        return Single.create {
            if (!it.isDisposed) it.onSuccess(image.copy(isFavourite = true))
        }
    }

    override fun unfavouriteImage(imageId: Int): Single<Image> {
        val image = dao.getById(imageId)?.let { converter.toDomain(it, false) }
        dao.delete(imageId)
        return Single.create {
            if (image != null) it.onSuccess(image)
            else it.onError(Throwable("Error"))
        }
    }
}