package com.nate.supersimplemvp.config

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.utils.SSMvpUtils
import com.nate.supersimplemvp.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by Nate on 2020/5/5
 */
class AppLifecycle : SSAppLifecycle {
  override fun attachBaseContext(base: Context) {
  }

  override fun onCreate(application: Application) {
    if (LeakCanary.isInAnalyzerProcess(application)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    if (BuildConfig.LOG_DEBUG) { //Timber初始化
      Timber.plant(DebugTree())
      ARouter.openLog()
      ARouter.openDebug()
      RetrofitUrlManager.getInstance().setDebug(true)
    }
    //LeakCanary 内存泄露检查
    //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
    //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
    SSMvpUtils.obtainAppComponentFromContext(application).extras().put(
      SmartCache.getKeyOfKeep(RefWatcher::class.java.name), if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED
    )
    ARouter.init(application)
  }

  override fun onTerminate(application: Application) {
  }
}
