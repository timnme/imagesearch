package com.instacentner.instacentner.data.utils

import com.instacentner.instacentner.data.database.room.databasemodels.FavouriteImage
import com.instacentner.instacentner.data.network.apimodels.ApiImage
import com.instacentner.instacentner.domain.domainmodels.Image

interface DataModelConverter {
    fun toDomain(apiImage: ApiImage, isFavourite: Boolean): Image
    fun toDomain(favouriteImage: FavouriteImage, isFavourite: Boolean): Image
    fun toDb(image: Image): FavouriteImage
}
