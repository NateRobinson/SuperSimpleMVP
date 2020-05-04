package com.nate.ssmvp.data.cache

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.fragment.app.Fragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.data.SSRepositoryManager

/**
 * 针对不同的场景定制不同的 CacheType
 * Created by Nate on 2020/5/2
 */
interface SSCacheType {
  /**
   * 返回框架内需要缓存的模块对应的 `id`
   */
  val cacheTypeId: Int

  /**
   * 计算对应模块需要的缓存大小
   */
  fun calculateCacheSize(context: Context): Int

  companion object {
    const val RETROFIT_SERVICE_CACHE_TYPE_ID = 0
    const val CACHE_SERVICE_CACHE_TYPE_ID = 1
    const val EXTRAS_TYPE_ID = 2
    const val ACTIVITY_CACHE_TYPE_ID = 3
    const val FRAGMENT_CACHE_TYPE_ID = 4

    /**
     * [SSRepositoryManager] 中存储 Retrofit Service 的容器
     */
    val RETROFIT_SERVICE_CACHE: SSCacheType = object : SSCacheType {
      val MAX_SIZE = 150
      val MAX_SIZE_MULTIPLIER = 0.002f
      override val cacheTypeId: Int
        get() = RETROFIT_SERVICE_CACHE_TYPE_ID

      override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        return if (targetMemoryCacheSize >= MAX_SIZE) {
          MAX_SIZE
        } else targetMemoryCacheSize
      }
    }

    /**
     * [SSRepositoryManager] 中储存 Cache Service 的容器
     */
    val CACHE_SERVICE_CACHE: SSCacheType = object : SSCacheType {
      val MAX_SIZE = 150
      val MAX_SIZE_MULTIPLIER = 0.002f
      override val cacheTypeId: Int
        get() = CACHE_SERVICE_CACHE_TYPE_ID

      override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        return if (targetMemoryCacheSize >= MAX_SIZE) {
          MAX_SIZE
        } else targetMemoryCacheSize
      }
    }

    /**
     * [SSAppComponent] 中的 extras
     */
    val EXTRAS: SSCacheType = object : SSCacheType {
      val MAX_SIZE = 500
      val MAX_SIZE_MULTIPLIER = 0.005f
      override val cacheTypeId: Int
        get() = EXTRAS_TYPE_ID

      override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        return if (targetMemoryCacheSize >= MAX_SIZE) {
          MAX_SIZE
        } else targetMemoryCacheSize
      }
    }

    /**
     * [Activity] 中存储数据的容器
     */
    val ACTIVITY_CACHE: SSCacheType = object : SSCacheType {
      val MAX_SIZE = 80
      val MAX_SIZE_MULTIPLIER = 0.0008f
      override val cacheTypeId: Int
        get() = ACTIVITY_CACHE_TYPE_ID

      override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        return if (targetMemoryCacheSize >= MAX_SIZE) {
          MAX_SIZE
        } else targetMemoryCacheSize
      }
    }

    /**
     * [Fragment] 中存储数据的容器
     */
    val FRAGMENT_CACHE: SSCacheType = object : SSCacheType {
      val MAX_SIZE = 80
      val MAX_SIZE_MULTIPLIER = 0.0008f
      override val cacheTypeId: Int
        get() = FRAGMENT_CACHE_TYPE_ID

      override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        return if (targetMemoryCacheSize >= MAX_SIZE) {
          MAX_SIZE
        } else targetMemoryCacheSize
      }
    }
  }
}