package com.instacentner.instacentner.data.database

import com.instacentner.instacentner.data.database.room.databasemodels.FavouriteImage
import io.reactivex.Single

interface ImageDao {
    fun getFavourites(): Single<List<FavouriteImage>>
    fun getById(id: Int): FavouriteImage?
    fun insert(image: FavouriteImage)
    fun delete(imageId: Int)
}