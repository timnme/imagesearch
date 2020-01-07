package com.instacentner.instacentner.data.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.instacentner.instacentner.data.database.ImageDao
import com.instacentner.instacentner.data.database.room.databasemodels.FavouriteImage
import io.reactivex.Single

@Dao
interface ImageDaoRoomImpl : ImageDao {
    @Query("SELECT * FROM favourite_images")
    override fun getFavourites(): Single<List<FavouriteImage>>

    @Query("SELECT * FROM favourite_images WHERE id=:id")
    override fun getById(id: Int): FavouriteImage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(image: FavouriteImage)

    @Query("DELETE FROM favourite_images WHERE id=:imageId")
    override fun delete(imageId: Int)
}