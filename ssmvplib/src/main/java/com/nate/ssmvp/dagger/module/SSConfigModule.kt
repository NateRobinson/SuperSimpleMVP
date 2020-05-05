package com.nate.ssmvp.dagger.module

import android.app.Application
import com.nate.ssmvp.config.SSConfig.DEFAULT_BASE_URL
import com.nate.ssmvp.dagger.module.SSThirdLibModule.GsonConfiguration
import com.nate.ssmvp.dagger.module.SSThirdLibModule.OkHttpConfiguration
import com.nate.ssmvp.dagger.module.SSThirdLibModule.RetrofitConfiguration
import com.nate.ssmvp.dagger.module.SSThirdLibModule.RxCacheConfiguration
import com.nate.ssmvp.data.SSIRepositoryManager.ICustomObtainService
import com.nate.ssmvp.data.cache.SSCache
import com.nate.ssmvp.data.cache.SSCache.SSCacheFactory
import com.nate.ssmvp.data.cache.SSCacheType
import com.nate.ssmvp.data.cache.SSLruCache
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.http.SSOkHttpHandler
import com.nate.ssmvp.imageloader.SSIImageLoaderStrategy
import com.nate.ssmvp.imageloader.glide.GlideImageLoaderStrategy
import com.nate.ssmvp.utils.SSFileUtils
import dagger.Module
import dagger.Provides
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.internal.threadFactory
import java.io.File
import java.util.ArrayList
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

/**
 * 提供一些配置类的全局依赖实例
 * Created by Nate on 2020/5/3
 */
@Module
class SSConfigModule {

  companion object {
    fun builder(): Builder {
      return Builder()
    }
  }

  private var mApiUrl: HttpUrl? = null
  private var mLoaderStrategy: SSIImageLoaderStrategy<*>? = null
  private var mHandler: SSOkHttpHandler? = null
  private var mInterceptors: ArrayList<Interceptor>? = null
  private var mErrorListener: ResponseErrorListener? = null
  private var mCacheFile: File? = null
  private var mRetrofitConfiguration: RetrofitConfiguration? = null
  private var mOkhttpConfiguration: OkHttpConfiguration? = null
  private var mRxCacheConfiguration: RxCacheConfiguration? = null
  private var mGsonConfiguration: GsonConfiguration? = null
  private var mCacheFactory: SSCacheFactory<String, in Any>? = null
  private var mExecutorService: ExecutorService? = null
  private var mCustomObtainService: ICustomObtainService? = null

  private constructor(builder: Builder) {
    mApiUrl = builder.apiUrl
    mLoaderStrategy = builder.loaderStrategy
    mHandler = builder.handler
    mInterceptors = builder.interceptors
    mErrorListener = builder.responseErrorListener
    mCacheFile = builder.cacheFile
    mRetrofitConfiguration = builder.retrofitConfiguration
    mOkhttpConfiguration = builder.okHttpConfiguration
    mRxCacheConfiguration = builder.rxCacheConfiguration
    mGsonConfiguration = builder.gsonConfiguration
    mCacheFactory = builder.cacheFactory
    mExecutorService = builder.executorService
    mCustomObtainService = builder.customObtainService
  }

  @Singleton
  @Provides
  fun provideInterceptors(): ArrayList<Interceptor>? {
    return mInterceptors
  }

  /**
   * 提供 BaseUrl
   *
   * @return
   */
  @Singleton
  @Provides
  fun provideBaseUrl(): HttpUrl {
    return if (mApiUrl == null) DEFAULT_BASE_URL else mApiUrl!!
  }

  /**
   * 提供图片加载框架,默认使用 [GlideImageLoaderStrategy]
   *
   * @return
   */
  @Singleton
  @Provides
  fun provideImageLoaderStrategy(): SSIImageLoaderStrategy<*>? {
    return if (mLoaderStrategy == null) GlideImageLoaderStrategy() else mLoaderStrategy
  }

  /**
   * 提供处理 Http 请求和响应结果的处理类
   *
   * @return
   */
  @Singleton
  @Provides
  fun provideGlobalHttpHandler(): SSOkHttpHandler {
    return if (mHandler == null) SSOkHttpHandler.EMPTY else mHandler!!
  }

  /**
   * 提供缓存文件
   */
  @Singleton
  @Provides
  fun provideCacheFile(application: Application): File {
    return if (mCacheFile == null) SSFileUtils.getCacheFile(application) else mCacheFile!!
  }

  /**
   * 提供处理 RxJava 错误的管理器的回调
   *
   * @return
   */
  @Singleton
  @Provides
  fun provideResponseErrorListener(): ResponseErrorListener {
    return if (mErrorListener == null) ResponseErrorListener.EMPTY else mErrorListener!!
  }

  @Singleton
  @Provides
  fun provideRetrofitConfiguration(): RetrofitConfiguration? {
    return mRetrofitConfiguration
  }

  @Singleton
  @Provides
  fun provideOkHttpConfiguration(): OkHttpConfiguration? {
    return mOkhttpConfiguration
  }

  @Singleton
  @Provides
  fun provideRxCacheConfiguration(): RxCacheConfiguration? {
    return mRxCacheConfiguration
  }

  @Singleton
  @Provides
  fun provideGsonConfiguration(): GsonConfiguration? {
    return mGsonConfiguration
  }

  @Singleton
  @Provides
  fun provideCacheFactory(application: Application): SSCacheFactory<String, in Any> {
    return if (mCacheFactory == null) object : SSCacheFactory<String, Any> {
      override fun build(type: SSCacheType): SSCache<String, Any> {
        return when (type.cacheTypeId) {
          SSCacheType.EXTRAS_TYPE_ID, SSCacheType.ACTIVITY_CACHE_TYPE_ID, SSCacheType.FRAGMENT_CACHE_TYPE_ID -> SmartCache(
            type.calculateCacheSize(application)
          )
          else -> SSLruCache(type.calculateCacheSize(application))
        }
      }
    } else mCacheFactory!!
  }

  /**
   * 返回一个全局公用的线程池,适用于大多数异步需求。
   * 避免多个线程池创建带来的资源消耗。
   *
   * @return [Executor]
   */
  @Singleton
  @Provides
  fun provideExecutorService(): ExecutorService {
    return if (mExecutorService == null) ThreadPoolExecutor(
      0, Int.MAX_VALUE, 60, SECONDS, SynchronousQueue(), threadFactory("SSMvp Executor", false)
    ) else mExecutorService!!
  }

  @Singleton
  @Provides
  fun provideObtainServiceDelegate(): ICustomObtainService? {
    return mCustomObtainService
  }

  class Builder internal constructor() {
    var apiUrl: HttpUrl? = null
    var loaderStrategy: SSIImageLoaderStrategy<*>? = null
    var handler: SSOkHttpHandler? = null
    var interceptors: ArrayList<Interceptor>? = null
    var responseErrorListener: ResponseErrorListener? = null
    var cacheFile: File? = null
    var retrofitConfiguration: RetrofitConfiguration? = null
    var okHttpConfiguration: OkHttpConfiguration? = null
    var rxCacheConfiguration: RxCacheConfiguration? = null
    var gsonConfiguration: GsonConfiguration? = null
    var cacheFactory: SSCacheFactory<String, in Any>? = null
    var executorService: ExecutorService? = null
    var customObtainService: ICustomObtainService? = null

    fun baseUrl(baseUrl: String): Builder {
      apiUrl = baseUrl.toHttpUrlOrNull()
      return this
    }

    //用来请求网络图片
    fun imageLoaderStrategy(loaderStrategy: SSIImageLoaderStrategy<*>): Builder {
      this.loaderStrategy = loaderStrategy
      return this
    }

    //用来处理http响应结果
    fun okHttpHandler(handler: SSOkHttpHandler): Builder {
      this.handler = handler
      return this
    }

    //动态添加任意个interceptor
    fun addInterceptor(interceptor: Interceptor): Builder {
      if (interceptors == null) {
        interceptors = ArrayList()
      }
      interceptors!!.add(interceptor)
      return this
    }

    //处理所有 RxJava 的 onError 逻辑
    fun responseErrorListener(listener: ResponseErrorListener): Builder {
      responseErrorListener = listener
      return this
    }

    fun cacheFile(cacheFile: File): Builder {
      this.cacheFile = cacheFile
      return this
    }

    fun retrofitConfiguration(retrofitConfiguration: RetrofitConfiguration): Builder {
      this.retrofitConfiguration = retrofitConfiguration
      return this
    }

    fun okHttpConfiguration(okHttpConfiguration: OkHttpConfiguration): Builder {
      this.okHttpConfiguration = okHttpConfiguration
      return this
    }

    fun rxCacheConfiguration(rxCacheConfiguration: RxCacheConfiguration): Builder {
      this.rxCacheConfiguration = rxCacheConfiguration
      return this
    }

    fun gsonConfiguration(gsonConfiguration: GsonConfiguration): Builder {
      this.gsonConfiguration = gsonConfiguration
      return this
    }

    fun cacheFactory(cacheFactory: SSCacheFactory<String, in Any>): Builder {
      this.cacheFactory = cacheFactory
      return this
    }

    fun executorService(executorService: ExecutorService?): Builder {
      this.executorService = executorService
      return this
    }

    fun customObtainService(customObtainService: ICustomObtainService?): Builder {
      this.customObtainService = customObtainService
      return this
    }

    fun build(): SSConfigModule {
      return SSConfigModule(this)
    }
  }

}