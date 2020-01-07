package com.instacentner.instacentner.ui.utils

interface ImageLoader<T> {
    fun load(url: String, target: T)
}
