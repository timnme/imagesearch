package com.instacentner.instacentner.data.network.apimodels

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("hits")
    val images: List<ApiImage>,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("totalHits")
    val totalImages: Int
)