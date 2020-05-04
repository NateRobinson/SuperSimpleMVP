package com.nate.ssmvp.imageloader

import android.content.Context

/**
 * 图片加载策略,实现 [SSIImageLoaderStrategy] 并通过 [SSImageLoader.loadImgStrategy] 配置后,才可进行图片请求
 * Created by Nate on 2020/5/4
 */
interface SSIImageLoaderStrategy<T : SSImageConfig> {
  /**
   * 加载图片
   *
   * @param ctx [Context]
   * @param config 图片加载配置信息
   */
  fun loadImage(ctx: Context, config: SSImageConfig)

  /**
   * 清除
   *
   * @param ctx [Context]
   * @param config 图片加载配置信息
   */
  fun clear(ctx: Context, config: SSImageConfig)
}