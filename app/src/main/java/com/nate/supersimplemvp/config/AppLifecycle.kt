package com.nate.supersimplemvp.config

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.utils.SSMvpUtils
import com.nate.supersimplemvp.BuildConfig
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
    if (!ProcessUtils.isMainProcess()) {
      // just do init job in main process
      return
    }
    if (BuildConfig.LOG_DEBUG) { //Timber初始化
      Timber.plant(DebugTree())
      ARouter.openLog()
      ARouter.openDebug()
      RetrofitUrlManager.getInstance().setDebug(true)
    }
    Utils.init(application)
    ARouter.init(application)
  }

  override fun onTerminate(application: Application) {
  }
}
