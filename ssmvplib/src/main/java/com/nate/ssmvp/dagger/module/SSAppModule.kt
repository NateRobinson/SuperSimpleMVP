package com.nate.ssmvp.dagger.module

import android.app.Application.ActivityLifecycleCallbacks
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.ActivityLifecycleForRxLifecycle
import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.data.SSRepositoryManager
import com.nate.ssmvp.data.cache.SSCache
import com.nate.ssmvp.data.cache.SSCache.SSCacheFactory
import com.nate.ssmvp.lifecycle.SSActivityLifecycle
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.ArrayList
import javax.inject.Named
import javax.inject.Singleton

/**
 * 提供框架必须的一些依赖实例
 * Created by Nate on 2020/5/2
 */
@Module
abstract class SSAppModule {

  @Module
  companion object {

    @JvmStatic
    @Singleton
    @Provides
    fun provideExtras(cacheFactory: SSCacheFactory<String, in Any>): SSCache<String, in Any> {
      return cacheFactory.build(CacheType.EXTRAS)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFragmentLifecycle(): List<FragmentLifecycleCallbacks> {
      return ArrayList<FragmentLifecycleCallbacks>()
    }

  }

  @Binds
  abstract fun bindRepositoryManager(repositoryManager: SSRepositoryManager): SSIRepositoryManager

  @Binds
  @Named("ActivityLifecycle")
  abstract fun bindActivityLifecycle(activityLifecycle: SSActivityLifecycle): ActivityLifecycleCallbacks

  @Binds
  @Named("ActivityLifecycleForRxLifecycle")
  abstract fun bindActivityLifecycleForRxLifecycle(activityLifecycleForRxLifecycle: ActivityLifecycleForRxLifecycle): ActivityLifecycleCallbacks

}
