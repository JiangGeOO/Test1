package com.ijustyce.fastkotlin.glide

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.GlideModule
import com.ijustyce.fastkotlin.utils.CommonTools
import com.ijustyce.fastkotlin.utils.ThreadUtils

/**
 * Created by yangchun on 2017/1/8.
 */

class MyGlideModule : GlideModule {

    private fun availableSpace(): Long {
        val root = Environment.getRootDirectory()
        val sf = StatFs(root.path)
        val blockSize = sf.blockSize.toLong()
        val availCount = sf.availableBlocks.toLong()
        var available = availCount * blockSize
        available /= (1024 * 1024).toLong()
        return available
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val calculator = MemorySizeCalculator(context)
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val memory = CommonTools.getAvailMemory(context)
        var value = 1.2
        if (memory < 650) value = 1.0
        if (memory < 350) value = 0.8
        if (memory < 150) value = 0.5

        val customMemoryCacheSize = (value * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (value * defaultBitmapPoolSize).toInt()

        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565)
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))

        val cacheDir = context.externalCacheDir//指定的是数据的缓存地址
        val diskCacheSize = 1024 * 1024 * 300//最多可以缓存多少字节的数据，这里是300M
        if (cacheDir == null) {
            builder.setDiskCache(ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize))
        } else {
            builder.setDiskCache(DiskLruCacheFactory(cacheDir.path, "glide", diskCacheSize))
        }

        val space = availableSpace()
        if (space < 80) {
            ThreadUtils.execute(Runnable { Glide.get(context).clearDiskCache() })
        }
    }

    override fun registerComponents(context: Context, glide: Glide) {

    }
}
