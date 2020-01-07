package com.instacentner.instacentner.data.network

import com.instacentner.instacentner.data.network.apimodels.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val KEY = "11623576-263e0ae0426f448ca29a65b8a"

interface ImageSearchApiService {
    @GET("?")
    fun searchImages(
        @Query("key") key: String = KEY,
        @Query("q") query: String
    ): Single<ApiResponse>
}
