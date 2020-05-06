package com.nate.moduleone.config;

import android.app.Application
import android.content.Context
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.utils.SSMvpUtils
import com.nate.moduleone.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by Nate
 */
class ModuleLifecycle : SSAppLifecycle {
  override fun attachBaseContext(base: Context) {
  }

  override fun onCreate(application: Application) {
    if (LeakCanary.isInAnalyzerProcess(application)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    //LeakCanary 内存泄露检查
    if (BuildConfig.IS_BUILD_MODULE) {
      SSMvpUtils.obtainAppComponentFromContext(application).extras().put(
        SmartCache.getKeyOfKeep(RefWatcher::class.java.name), if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED
      )
    }
  }

  override fun onTerminate(application: Application) {
  }
}
