package com.instacentner.instacentner.data.utils.impl

import com.instacentner.instacentner.data.database.room.databasemodels.FavouriteImage
import com.instacentner.instacentner.data.network.apimodels.ApiImage
import com.instacentner.instacentner.data.utils.DataModelConverter
import com.instacentner.instacentner.domain.domainmodels.Image

class DataModelConverterImpl : DataModelConverter {
    override fun toDb(image: Image): FavouriteImage {
        return with(image) {
            FavouriteImage(
                id = id,
                previewUrl = previewUrl,
                largeImageUrl = largeImageUrl,
                previewImageWidth = previewImageWidth,
                previewImageHeight = previewImageHeight
            )
        }
    }

    override fun toDomain(favouriteImage: FavouriteImage, isFavourite: Boolean): Image {
        return with(favouriteImage) {
            Image(
                id = id,
                previewUrl = previewUrl,
                largeImageUrl = largeImageUrl,
                previewImageWidth = previewImageWidth,
                previewImageHeight = previewImageHeight,
                isFavourite = isFavourite
            )
        }
    }

    override fun toDomain(apiImage: ApiImage, isFavourite: Boolean): Image {
        return with(apiImage) {
            Image(
                id = userId + imageHeight + imageWidth,
                previewUrl = previewURL,
                largeImageUrl = largeImageURL,
                previewImageWidth = previewWidth,
                previewImageHeight = previewHeight,
                isFavourite = isFavourite
            )
        }
    }
}
