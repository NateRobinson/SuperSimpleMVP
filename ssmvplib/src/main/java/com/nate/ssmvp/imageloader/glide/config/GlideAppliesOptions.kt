package com.nate.ssmvp.imageloader.glide.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.nate.ssmvp.imageloader.glide.GlideImageLoaderStrategy

/**
 * 暴露给开发者配置 Glide 的接口，[GlideImageLoaderStrategy] 就实现了此接口
 * Created by Nate on 2020/5/4
 */
interface GlideAppliesOptions {
  /**
   * 开发者可以实现此方法，再里面添加自己的自定义逻辑
   * @param builder [GlideBuilder] 此类被用来创建 Glide
   */
  fun applyGlideOptions(context: Context, builder: GlideBuilder)

  /**
   * 开发者可以在里面做自定义配置
   * @param context Android context
   * @param glide [Glide]
   * @param registry [Registry]
   */
  fun registerComponents(context: Context, glide: Glide, registry: Registry)
}