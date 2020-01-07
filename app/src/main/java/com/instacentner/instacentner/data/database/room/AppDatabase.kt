package com.instacentner.instacentner.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.instacentner.instacentner.data.database.room.databasemodels.FavouriteImage

@Database(
    entities = [
        FavouriteImage::class
    ], exportSchema = false, version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDaoRoomImpl
}