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
package com.jess.arms.http.imageloader

import android.content.Context
import com.jess.arms.utils.Preconditions
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ================================================
 * [ImageLoader] 使用策略模式和建造者模式,可以动态切换图片请求框架(比如说切换成 Picasso )
 * 当需要切换图片请求框架或图片请求框架升级后变更了 Api 时
 * 这里可以将影响范围降到最低,所以封装 [ImageLoader] 是为了屏蔽这个风险
 *
 * @see [ImageLoader wiki 文档](https://github.com/JessYanCoding/MVPArms/wiki.3.4)
 * Created by JessYan on 8/5/16 15:57
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@Singleton
class ImageLoader @Inject constructor() {

  @JvmField
  @Inject
  var loadImgStrategy: BaseImageLoaderStrategy<*>? = null

  /**
   * 加载图片
   *
   * @param context
   * @param config
   * @param <T>
  </T> */
  fun <T : ImageConfig> loadImage(context: Context, config: T) {
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
  </T> */
  fun <T : ImageConfig> clear(context: Context, config: T) {
    Preconditions.checkNotNull(
        loadImgStrategy,
        "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule"
    )
    loadImgStrategy?.clear(context, config)
  }

}