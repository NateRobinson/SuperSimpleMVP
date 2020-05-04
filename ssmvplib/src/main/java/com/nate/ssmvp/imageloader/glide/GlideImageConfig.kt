package com.nate.ssmvp.imageloader.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.nate.ssmvp.imageloader.SSImageCacheStrategy
import com.nate.ssmvp.imageloader.SSImageConfig

/**
 * 针对 Glide 定制的图片加载配置类
 * Created by Nate on 2020/5/4
 */
class GlideImageConfig : SSImageConfig {
  // 图片缓存策略
  var cacheStrategy: SSImageCacheStrategy

  var fallback: Int

  // 圆角尺寸
  var imageRadius: Int

  // 模糊值
  var blurValue: Int

  // Glide 特有的，用来做图片的变换
  val transformation: BitmapTransformation?

  val imageViews: Array<out ImageView>?

  //是否使用淡入淡出过渡动画
  var isCrossFade: Boolean

  //是否将图片剪切为 CenterCrop
  var isCenterCrop: Boolean

  //是否将图片剪切为圆形
  var isCircle: Boolean

  //清理内存缓存
  var isClearMemory: Boolean

  //清理本地缓存
  var isClearDiskCache: Boolean
  var isRoundRadius: Boolean
  private var radius = 5

  companion object {
    fun builder(): Builder {
      return Builder()
    }
  }

  private constructor(builder: Builder) {
    url = builder.url
    imageView = builder.imageView
    placeholder = builder.placeholder
    errorPic = builder.errorPic
    fallback = builder.fallback
    cacheStrategy = builder.cacheStrategy
    imageRadius = builder.imageRadius
    blurValue = builder.blurValue
    transformation = builder.transformation
    imageViews = builder.imageViews
    isCrossFade = builder.isCrossFade
    isCenterCrop = builder.isCenterCrop
    isCircle = builder.isCircle
    isClearMemory = builder.isClearMemory
    isClearDiskCache = builder.isClearDiskCache
    isRoundRadius = builder.isRoundRadius
    radius = builder.radius
  }

  val isBlurImage: Boolean
    get() = blurValue > 0

  fun isImageRadius(): Boolean {
    return imageRadius > 0
  }

  fun radius(): Int {
    return radius
  }

  class Builder internal constructor() {
    var url: String? = null
    var imageView: ImageView? = null
    var placeholder = 0
    var errorPic = 0
    var fallback = 0
    var cacheStrategy = SSImageCacheStrategy.ALL
    var imageRadius = 0
    var blurValue = 0
    var transformation: BitmapTransformation? = null
    var imageViews: Array<out ImageView> = arrayOf()
    var isCrossFade = false
    var isCenterCrop = false
    var isCircle = false
    var isClearMemory = false
    var isClearDiskCache = false
    var isRoundRadius = false
    var radius = 5

    fun url(url: String?): Builder {
      this.url = url
      return this
    }

    fun placeholder(placeholder: Int): Builder {
      this.placeholder = placeholder
      return this
    }

    fun errorPic(errorPic: Int): Builder {
      this.errorPic = errorPic
      return this
    }

    fun fallback(fallback: Int): Builder {
      this.fallback = fallback
      return this
    }

    fun imageView(imageView: ImageView?): Builder {
      this.imageView = imageView
      return this
    }

    fun cacheStrategy(cacheStrategy: SSImageCacheStrategy): Builder {
      this.cacheStrategy = cacheStrategy
      return this
    }

    fun imageRadius(imageRadius: Int): Builder {
      this.imageRadius = imageRadius
      return this
    }

    fun blurValue(blurValue: Int): Builder { //blurValue 建议设置为 15
      this.blurValue = blurValue
      return this
    }

    fun roundRadius(roundRadius: Boolean, radius: Int): Builder {
      isRoundRadius = roundRadius
      this.radius = radius
      return this
    }

    fun transformation(transformation: BitmapTransformation?): Builder {
      this.transformation = transformation
      return this
    }

    fun imageViews(vararg imageViews: ImageView): Builder {
      this.imageViews = imageViews
      return this
    }

    fun isCrossFade(isCrossFade: Boolean): Builder {
      this.isCrossFade = isCrossFade
      return this
    }

    fun isCenterCrop(isCenterCrop: Boolean): Builder {
      this.isCenterCrop = isCenterCrop
      return this
    }

    fun isCircle(isCircle: Boolean): Builder {
      this.isCircle = isCircle
      return this
    }

    fun isClearMemory(isClearMemory: Boolean): Builder {
      this.isClearMemory = isClearMemory
      return this
    }

    fun isClearDiskCache(isClearDiskCache: Boolean): Builder {
      this.isClearDiskCache = isClearDiskCache
      return this
    }

    fun build(): GlideImageConfig {
      return GlideImageConfig(this)
    }
  }

}