package com.nate.ssmvp.data

import retrofit2.Retrofit

/**
 * Data 层接口，用来定义数据层获取数据，清理数据相关接口
 * Created by Nate on 2020/5/2
 */
interface SSIRepositoryManager {
  fun <T> obtainRetrofitService(serviceClass: Class<T>): T
  fun <T> obtainCacheService(cacheClass: Class<T>): T
  fun clearAllCache()

  interface ICustomObtainService {
    fun <T> createRetrofitService(
      retrofit: Retrofit,
      serviceClass: Class<T>
    ): T
  }
}