package com.nate.ssmvp.dagger.component

import android.app.Application
import com.nate.ssmvp.base.SSAppDelegate
import com.nate.ssmvp.config.SSMVPConfig
import com.nate.ssmvp.dagger.module.SSAppModule
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.dagger.module.SSThirdLibModule
import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.data.cache.SSCache
import com.nate.ssmvp.data.cache.SSCache.SSCacheFactory
import com.nate.ssmvp.imageloader.SSImageLoader
import com.nate.ssmvp.utils.SSMvpUtils
import dagger.BindsInstance
import dagger.Component
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.ExecutorService
import javax.inject.Singleton

/**
 * 可通过 [SSMvpUtils.obtainAppComponentFromContext] 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * Created by Nate on 2020/5/3
 */
@Singleton
@Component(modules = [SSAppModule::class, SSThirdLibModule::class, SSConfigModule::class])
interface SSAppComponent {
  fun application(): Application

  /**
   * 数据层，目前提供了 Retrofit 和 RxCache 的实例获取
   * @return [SSIRepositoryManager]
   */
  fun repositoryManager(): SSIRepositoryManager

  /**
   * RxJava 错误处理管理类
   *
   * @return [RxErrorHandler]
   */
  fun rxErrorHandler(): RxErrorHandler

  /**
   * 图片加载管理器, 用于加载图片的管理类, 使用策略者模式, 可在运行时动态替换任何图片加载框架
   * 需要在 [SSMVPConfig.applyOptions] 中手动注册 [BaseImageLoaderStrategy], [ImageLoader] 才能正常使用
   * @return [ImageLoader]
   */
  fun imageLoader(): SSImageLoader

  /**
   * 网络请求框架
   *
   * @return [OkHttpClient]
   */
  fun okHttpClient(): OkHttpClient

  /**
   * 缓存文件根目录 (RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下), 应该将所有缓存都统一放到这个根目录下
   * 便于管理和清理, 可在 [ConfigModule.applyOptions] 种配置
   *
   * @return [File]
   */
  fun cacheFile(): File

  /**
   * 用来存取一些整个 App 公用的数据, 切勿大量存放大容量数据, 这里的存放的数据和 [Application] 的生命周期一致
   *
   * @return [SSCache]
   */
  fun extras(): SSCache<String, in Any>

  /**
   * 用于创建框架所需缓存对象的工厂
   *
   * @return [SSCache.SSCacheFactory]
   */
  fun cacheFactory(): SSCacheFactory<String, in Any>

  /**
   * 返回一个全局公用的线程池,适用于大多数异步需求。
   * 避免多个线程池创建带来的资源消耗。
   *
   * @return [ExecutorService]
   */
  fun executorService(): ExecutorService

  fun inject(delegate: SSAppDelegate)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder
    fun ssConfigModule(ssConfigModule: SSConfigModule): Builder
    fun build(): SSAppComponent
  }
}
