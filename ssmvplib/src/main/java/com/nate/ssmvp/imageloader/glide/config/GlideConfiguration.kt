package com.nate.ssmvp.imageloader.glide.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator.Builder
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.jess.arms.http.OkHttpUrlLoader.Factory
import com.jess.arms.utils.DataHelper
import com.nate.ssmvp.utils.SSMvpUtils
import java.io.File
import java.io.InputStream

/**
 * super simple mvp 默认提供的 [AppGlideModule] 实现类
 * 用于配置缓存文件夹,切换图片请求框架等操作
 * Created by Nate on 2020/5/4
 */
@GlideModule(glideName = "SSMvpGlide")
class GlideConfiguration : AppGlideModule() {
  override fun applyOptions(context: Context, builder: GlideBuilder) {
    val appComponent = SSMvpUtils.obtainAppComponentFromContext(context)

    builder.setDiskCache {
      DiskLruCacheWrapper.create(
          DataHelper.makeDirs(File(appComponent.cacheFile(), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE.toLong()
      )
    }

    val calculator = Builder(context).build()

    val defaultMemoryCacheSize = calculator.memoryCacheSize
    val defaultBitmapPoolSize = calculator.bitmapPoolSize
    val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
    val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

    builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
    builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

    //开发者通过让 ImageLoadStrategy 实现 GlideAppliesOptions 可以做一些 Glide 自定义的配置
    val loadImgStrategy = appComponent.imageLoader().loadImgStrategy
    if (loadImgStrategy is GlideAppliesOptions) {
      (loadImgStrategy as GlideAppliesOptions).applyGlideOptions(context, builder)
    }
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    // 使用 OkHttp 做网络图片请求
    val appComponent = SSMvpUtils.obtainAppComponentFromContext(context)
    registry.replace(
        GlideUrl::class.java, InputStream::class.java, Factory(appComponent.okHttpClient())
    )

    //开发者通过让 ImageLoadStrategy 实现 GlideAppliesOptions 可以做一些 Glide 自定义的配置
    val loadImgStrategy = appComponent.imageLoader().loadImgStrategy
    if (loadImgStrategy is GlideAppliesOptions) {
      (loadImgStrategy as GlideAppliesOptions).registerComponents(context, glide, registry)
    }
  }

  override fun isManifestParsingEnabled(): Boolean {
    return false
  }

  companion object {
    const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024 //图片缓存文件最大值为100Mb
  }
}