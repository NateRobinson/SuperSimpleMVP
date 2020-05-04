package com.nate.ssmvp.imageloader.glide

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jess.arms.utils.Preconditions
import com.nate.ssmvp.imageloader.SSIImageLoaderStrategy
import com.nate.ssmvp.imageloader.SSImageCacheStrategy.ALL
import com.nate.ssmvp.imageloader.SSImageCacheStrategy.AUTOMATIC
import com.nate.ssmvp.imageloader.SSImageCacheStrategy.DATA
import com.nate.ssmvp.imageloader.SSImageCacheStrategy.NONE
import com.nate.ssmvp.imageloader.SSImageCacheStrategy.RESOURCE
import com.nate.ssmvp.imageloader.SSImageConfig
import com.nate.ssmvp.imageloader.glide.config.GlideAppliesOptions
import com.nate.ssmvp.imageloader.glide.config.SSMvpGlide
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 针对 Glide 定制的图片加载策略
 * Created by Nate on 2020/5/4
 */
class GlideImageLoaderStrategy : SSIImageLoaderStrategy<GlideImageConfig>, GlideAppliesOptions {
  @SuppressLint("CheckResult")
  override fun loadImage(ctx: Context, config: SSImageConfig) {

    Preconditions.checkNotNull(config.imageView, "ImageView is required")

    //如果context是activity则自动使用Activity的生命周期
    val requests = SSMvpGlide.with(ctx)
    val glideRequest = requests.load(config.url)

    config as GlideImageConfig

    when (config.cacheStrategy) {
      ALL -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
      NONE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
      RESOURCE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
      DATA -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
      AUTOMATIC -> glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }
    if (config.isCrossFade) {
      glideRequest.transition(DrawableTransitionOptions.withCrossFade())
    }
    if (config.isCenterCrop) {
      glideRequest.centerCrop()
    }
    if (config.isCircle) {
      glideRequest.circleCrop()
    }
    if (config.isImageRadius()) {
      glideRequest.transform(RoundedCorners(config.imageRadius))
    }
    if (config.isRoundRadius) {
      glideRequest.transform(GlideRoundTransform(ctx, config.radius()))
    }
    if (config.isBlurImage) {
      glideRequest.transform(GlideBlurTransformation(config.blurValue))
    }
    if (config.transformation != null) {
      //glide用它来改变图形的形状
      glideRequest.transform(config.transformation)
    }
    if (config.placeholder != 0) {
      //设置占位符
      glideRequest.placeholder(config.placeholder)
    }
    if (config.errorPic != 0) {
      //设置错误的图片
      glideRequest.error(config.errorPic)
    }
    if (config.fallback != 0) {
      //设置请求 url 为空图片
      glideRequest.fallback(config.fallback)
    }
    glideRequest.into(config.imageView!!)
  }

  override fun clear(ctx: Context, config: SSImageConfig) {
    Preconditions.checkNotNull(ctx, "Context is required")
    Preconditions.checkNotNull(config, "ImageConfigImpl is required")

    config as GlideImageConfig

    if (config.imageView != null) {
      SSMvpGlide.get(ctx).requestManagerRetriever[ctx].clear(config.imageView!!)
    }
    if (config.imageViews != null && config.imageViews.isNotEmpty()) { //取消在执行的任务并且释放资源
      for (imageView in config.imageViews) {
        SSMvpGlide.get(ctx).requestManagerRetriever[ctx].clear(imageView as ImageView)
      }
    }
    if (config.isClearDiskCache) { //清除本地缓存
      Completable.fromAction {
        Glide.get(ctx).clearDiskCache()
      }.subscribeOn(Schedulers.io()).subscribe()
    }
    if (config.isClearMemory) { //清除内存缓存
      Completable.fromAction {
        Glide.get(ctx).clearMemory()
      }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()
    }
  }

  override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
    // 在里面可以做自定义的操作
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    // 在里面可以做自定义的操作
  }
}