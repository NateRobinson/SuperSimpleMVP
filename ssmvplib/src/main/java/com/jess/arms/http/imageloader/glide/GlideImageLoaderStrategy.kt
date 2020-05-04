/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.http.imageloader.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy
import com.jess.arms.http.imageloader.ImageConfig
import com.jess.arms.utils.Preconditions
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * ================================================
 * 此类只是简单的实现了 Glide 加载的策略,方便快速使用,但大部分情况会需要应对复杂的场景
 * 这时可自行实现 [BaseImageLoaderStrategy] 和 [ImageConfig] 替换现有策略
 *
 * @see GlobalConfigModule.Builder.imageLoaderStrategy
 */
class GlideImageLoaderStrategy : BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {
  override fun loadImage(ctx: Context, config: ImageConfig) {
    Preconditions.checkNotNull(ctx, "Context is required")
    Preconditions.checkNotNull(config, "ImageConfigImpl is required")
    Preconditions.checkNotNull(config!!.imageView, "ImageView is required")
    val requests: GlideRequests
    requests = GlideArms.with(ctx!!) //如果context是activity则自动使用Activity的生命周期
    val glideRequest = requests.load(config.url)
    config as ImageConfigImpl
    when (config.getCacheStrategy()) {
      CacheStrategy.ALL -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
      CacheStrategy.NONE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
      CacheStrategy.RESOURCE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
      CacheStrategy.DATA -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
      CacheStrategy.AUTOMATIC -> glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      else -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
    }
    if (config.isCrossFade()) {
      glideRequest.transition(DrawableTransitionOptions.withCrossFade())
    }
    if (config.isCenterCrop()) {
      glideRequest.centerCrop()
    }
    if (config.isCircle()) {
      glideRequest.circleCrop()
    }
    if (config.isImageRadius()) {
      glideRequest.transform(RoundedCorners(config.getImageRadius()))
    }
    if (config.isRoundRadius()) {
      glideRequest.transform(GlideRoundTransform(ctx, config.radius()))
    }
    if (config.isBlurImage()) {
      glideRequest.transform(BlurTransformation(config.getBlurValue()))
    }
    if (config.getTransformation() != null) { //glide用它来改变图形的形状
      glideRequest.transform(config.getTransformation())
    }
    if (config.placeholder != 0) //设置占位符
    {
      glideRequest.placeholder(config.placeholder)
    }
    if (config.errorPic != 0) //设置错误的图片
    {
      glideRequest.error(config.errorPic)
    }
    if (config.getFallback() !== 0) //设置请求 url 为空图片
    {
      glideRequest.fallback(config.getFallback())
    }
    glideRequest.into(config.imageView)
  }

  override fun clear(ctx: Context, config: ImageConfig) {
    Preconditions.checkNotNull(ctx, "Context is required")
    Preconditions.checkNotNull(config, "ImageConfigImpl is required")

    config as ImageConfigImpl

    if (config.imageView != null) {
      GlideArms.get(ctx!!).requestManagerRetriever[ctx].clear(config.imageView)
    }
    if (config.getImageViews() != null && config.getImageViews().size > 0) { //取消在执行的任务并且释放资源
      for (imageView in config.getImageViews()) {
        GlideArms.get(ctx!!).requestManagerRetriever[ctx].clear(imageView!!)
      }
    }
    if (config.isClearDiskCache()) { //清除本地缓存
      Completable.fromAction {
        Glide.get(ctx!!)
            .clearDiskCache()
      }
          .subscribeOn(Schedulers.io())
          .subscribe()
    }
    if (config.isClearMemory()) { //清除内存缓存
      Completable.fromAction {
        Glide.get(ctx!!)
            .clearMemory()
      }
          .subscribeOn(AndroidSchedulers.mainThread())
          .subscribe()
    }
  }

  override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
    Timber.i("applyGlideOptions")
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    Timber.i("registerComponents")
  }
}