package com.nate.ssmvp.data

import com.jess.arms.integration.RetrofitServiceProxyHandler
import com.jess.arms.integration.cache.CacheType
import com.nate.ssmvp.data.SSIRepositoryManager.ICustomObtainService
import com.nate.ssmvp.data.cache.SSCache
import com.nate.ssmvp.data.cache.SSCache.SSCacheFactory
import dagger.Lazy
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.lang.reflect.Proxy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 框架的数据层管理类
 * Created by Nate on 2020/5/2
 */
@Suppress("UNCHECKED_CAST")
@Singleton
class SSRepositoryManager @Inject constructor() : SSIRepositoryManager {

  @Inject
  lateinit var mRetrofit: Lazy<Retrofit>

  @Inject
  lateinit var mRxCache: Lazy<RxCache>

  @Inject
  lateinit var mCacheFactory: SSCacheFactory<String, in Any>

  @JvmField
  @Inject
  var mICustomObtainService: ICustomObtainService? = null

  private var mRetrofitServiceCache: SSCache<String, in Any>? = null
  private var mCacheServiceCache: SSCache<String, in Any>? = null

  /**
   * 根据传入的 Class 获取对应的 Retrofit service
   * @param serviceClass ApiService class
   * @param <T>          ApiService class
   * @return ApiService
   */
  override fun <T> obtainRetrofitService(serviceClass: Class<T>): T {
    if (mRetrofitServiceCache == null) {
      mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE)
    }
    var retrofitService: T? = null
    if (serviceClass.isInstance(mRetrofitServiceCache?.get(serviceClass.canonicalName ?: ""))) {
      retrofitService = mRetrofitServiceCache?.get(serviceClass.canonicalName ?: "") as T?
    }
    if (retrofitService == null) {
      retrofitService = mICustomObtainService?.createRetrofitService(mRetrofit.get(), serviceClass)
      if (retrofitService == null) {
        retrofitService = Proxy.newProxyInstance(
            serviceClass.classLoader, arrayOf<Class<*>>(serviceClass), RetrofitServiceProxyHandler(mRetrofit.get(), serviceClass)
        ) as T
      }
      mRetrofitServiceCache?.put(serviceClass.canonicalName ?: "", retrofitService!!)
    }
    if (retrofitService == null) {
      throw RuntimeException("Can not create retrofitService!")
    }
    return retrofitService
  }

  /**
   * 根据传入的 Class 获取对应的 RxCache service
   * @param cacheClass Cache class
   * @param <T>        Cache class
   * @return Cache
   */
  override fun <T> obtainCacheService(cacheClass: Class<T>): T {
    if (mCacheServiceCache == null) {
      mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVICE_CACHE)
    }
    var cacheService: T? = null
    if (cacheClass.isInstance(mRetrofitServiceCache?.get(cacheClass.canonicalName ?: ""))) {
      cacheService = mCacheServiceCache?.get(cacheClass.canonicalName ?: "") as T?
    }
    if (cacheService == null) {
      cacheService = mRxCache.get().using(cacheClass)
      mCacheServiceCache?.put(cacheClass.canonicalName ?: "", cacheService!!)
    }
    if (cacheService == null) {
      throw RuntimeException("Can not create cacheService!")
    }
    return cacheService
  }

  override fun clearAllCache() {
    mRxCache.get().evictAll().subscribe()
  }
}
