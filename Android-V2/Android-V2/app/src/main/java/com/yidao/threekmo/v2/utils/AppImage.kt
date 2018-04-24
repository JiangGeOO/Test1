package com.yidao.threekmo.v2.utils

import android.widget.ImageView
import com.ijustyce.fastkotlin.glide.ImageLoader
import com.ijustyce.fastkotlin.utils.getWidth

/**
 * Created by deepin on 17-12-19.
 */
object AppImage {

    fun load(imageView: ImageView?, url: String?, corner: Int) {
        load(imageView, url, corner, -1, -1, ImageLoader.TYPE_ALL)
    }

    fun load(imageView: ImageView?, url: String?, corner: Int, type: Int) {
        load(imageView, url, corner, -1, -1, type)
    }

    fun load(imageView: ImageView?, url: String?, corner: Int, width: Int, height: Int) {
        load(imageView, url, corner, width, height, ImageLoader.TYPE_ALL)
    }

    fun load(imageView: ImageView?, url: String?, corner: Int, width: Int, height: Int, type: Int) {
        imageView ?: return
        url ?: return
        ImageLoader.loadWidthCorner(imageView, url, width, height, getWidth(corner), type)
    }

    fun circle(imageView: ImageView, url: String) {
        ImageLoader.loadCircle(imageView, url)
    }
}