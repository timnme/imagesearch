package com.instacentner.instacentner.domain.domainmodels

data class Image(
    val id: Int,
    val previewImageWidth: Int,
    val previewImageHeight: Int,
    val previewUrl: String,
    val largeImageUrl: String,
    val isFavourite: Boolean
)
