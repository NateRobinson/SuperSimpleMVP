package com.nate.ssmvp.dagger.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jess.arms.http.GlobalHttpHandler
import com.jess.arms.utils.DataHelper
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Named
import javax.inject.Singleton

/**
 * 提供第三方库的对象实例
 * Created by Nate on 2020/5/3
 */

private const val TIME_OUT: Int = 10

@Module
abstract class SSThirdLibModule {

  @Module
  companion object {

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(application: Application, configuration: RetrofitConfiguration?, builder: Builder, client: OkHttpClient, httpUrl: HttpUrl,
      gson: Gson): Retrofit {

      builder.baseUrl(httpUrl)
          .client(client)

      configuration?.configRetrofit(application, builder)

      builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))

      return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(application: Application, configuration: OkHttpConfiguration?, builder: OkHttpClient.Builder,
      interceptors: ArrayList<Interceptor>?, handler: GlobalHttpHandler?, executorService: ExecutorService): OkHttpClient {

      builder.connectTimeout(TIME_OUT.toLong(), SECONDS)
          .readTimeout(TIME_OUT.toLong(), SECONDS)

      if (handler != null) {
        builder.addInterceptor(object : Interceptor {
          @Throws(IOException::class)
          override fun intercept(chain: Chain): Response {
            return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()))
          }
        })
      }

      //如果外部提供了 Interceptor 的集合则遍历添加
      if (interceptors != null) {
        for (interceptor in interceptors) {
          builder.addInterceptor(interceptor)
        }
      }

      //为 OkHttp 设置默认的线程池
      builder.dispatcher(Dispatcher(executorService))
      configuration?.configOkHttp(application, builder)
      return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Builder {
      return Builder()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
      return OkHttpClient.Builder()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRxCache(application: Application, configuration: RxCacheConfiguration?,
      @Named("RxCacheDirectory")
      cacheDirectory: File, gson: Gson): RxCache? {
      val builder = RxCache.Builder()
      var rxCache: RxCache? = null
      if (configuration != null) {
        rxCache = configuration.configRxCache(application, builder)
      }
      return rxCache ?: builder.persistence(cacheDirectory, GsonSpeaker(gson))
    }

    /**
     * 需要单独给 [RxCache] 提供子缓存文件夹
     *
     * @param cacheDir 框架缓存文件夹
     * @return [File]
     */
    @JvmStatic
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    fun provideRxCacheDirectory(cacheDir: File): File {
      val cacheDirectory = File(cacheDir, "RxCache")
      return DataHelper.makeDirs(cacheDirectory)
    }

    /**
     * 提供处理 RxJava 错误的管理器
     *
     * @param application [Application]
     * @param listener    [ResponseErrorListener]
     * @return [RxErrorHandler]
     */
    @JvmStatic
    @Singleton
    @Provides
    fun proRxErrorHandler(application: Application, listener: ResponseErrorListener): RxErrorHandler {
      return RxErrorHandler.builder()
          .with(application)
          .responseErrorListener(listener)
          .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
      val builder = GsonBuilder()
      configuration?.configGson(application, builder)
      return builder.create()
    }
  }

  /**
   * [Retrofit] 自定义配置接口
   */
  interface RetrofitConfiguration {
    fun configRetrofit(context: Context, builder: Builder)
  }

  /**
   * [OkHttpClient] 自定义配置接口
   */
  interface OkHttpConfiguration {
    fun configOkHttp(context: Context, builder: OkHttpClient.Builder)
  }

  /**
   * [RxCache] 自定义配置接口
   */
  interface RxCacheConfiguration {
    /**
     * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson
     * 请 `return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());`, 否则请 `return null;`
     *
     * @param context [Context]
     * @param builder [RxCache.Builder]
     * @return [RxCache]
     */
    fun configRxCache(context: Context, builder: RxCache.Builder): RxCache?
  }

  interface GsonConfiguration {
    fun configGson(context: Context, builder: GsonBuilder)
  }

}