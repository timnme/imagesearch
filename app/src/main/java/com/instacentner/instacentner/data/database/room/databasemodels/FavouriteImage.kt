package com.instacentner.instacentner.data.database.room.databasemodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_images")
data class FavouriteImage(
    @PrimaryKey
    val id: Int,
    val previewImageWidth: Int,
    val previewImageHeight: Int,
    val previewUrl: String,
    val largeImageUrl: String
)