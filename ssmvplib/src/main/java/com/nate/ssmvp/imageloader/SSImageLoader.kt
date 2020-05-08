package com.nate.ssmvp.imageloader

import android.content.Context
import com.nate.ssmvp.imageloader.glide.GlideImageConfig
import com.nate.ssmvp.imageloader.glide.GlideImageLoaderStrategy
import com.nate.ssmvp.utils.Preconditions
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 策略模式的图片加载框架，方便应用层快速的切换图片加载库
 * Created by Nate on 2020/5/4
 */
@Singleton
class SSImageLoader @Inject constructor() {

  var loadImgStrategy = GlideImageLoaderStrategy()

  /**
   * 加载图片
   *
   * @param context
   * @param config
   * @param <T>
   */
  fun loadImage(context: Context, config: GlideImageConfig) {
    loadImgStrategy.loadImage(context, config)
  }

  /**
   * 停止加载或清理缓存
   *
   * @param context
   * @param config
   * @param <T>
   */
  fun clear(context: Context, config: GlideImageConfig) {
    loadImgStrategy.clear(context, config)
  }

}