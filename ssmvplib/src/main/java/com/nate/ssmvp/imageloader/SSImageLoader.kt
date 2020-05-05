package com.nate.ssmvp.imageloader

import android.content.Context
import com.nate.ssmvp.utils.Preconditions
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 策略模式的图片加载框架，方便应用层快速的切换图片加载库
 * Created by Nate on 2020/5/4
 */
@Singleton
class SSImageLoader @Inject constructor() {

  @JvmField
  @Inject
  var loadImgStrategy: SSIImageLoaderStrategy<in SSImageConfig>? = null

  /**
   * 加载图片
   *
   * @param context
   * @param config
   * @param <T>
   */
  fun <T : SSImageConfig> loadImage(context: Context, config: T) {
    Preconditions.checkNotNull(
      loadImgStrategy,
      "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule"
    )
    loadImgStrategy?.loadImage(context, config)
  }

  /**
   * 停止加载或清理缓存
   *
   * @param context
   * @param config
   * @param <T>
   */
  fun <T : SSImageConfig> clear(context: Context, config: T) {
    Preconditions.checkNotNull(
      loadImgStrategy,
      "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule"
    )
    loadImgStrategy?.clear(context, config)
  }

}