package com.nate.ssmvp.base

import android.app.Application
import android.content.Context
import com.nate.ssmvp.utils.Preconditions
import com.nate.ssmvp.dagger.component.SSAppComponent

/**
 * 框架默认提供的 Application 基类
 * Created by Nate on 2020/5/3
 */
class SSBaseApp : Application(), SSIApp {

  private lateinit var mAppDelegate: SSAppLifecycle

  /**
   * 这里会在 [BaseApplication.onCreate] 之前被调用,可以做一些较早的初始化
   * 常用于 MultiDex 以及插件化框架的初始化
   * @param base
   */
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    mAppDelegate = SSAppDelegate(base)
    mAppDelegate.attachBaseContext(base)
  }

  override fun onCreate() {
    super.onCreate()
    mAppDelegate.onCreate(this)
  }

  /**
   * 此方法只会在模拟环境中被调用
   */
  override fun onTerminate() {
    super.onTerminate()
    mAppDelegate.onTerminate(this)
  }

  override fun getSSAppComponent(): SSAppComponent {
    Preconditions.checkState(mAppDelegate is SSIApp, "%s must be implements %s", mAppDelegate.javaClass.name, SSIApp::class.java.name)
    return (mAppDelegate as SSIApp).getSSAppComponent()
  }
}