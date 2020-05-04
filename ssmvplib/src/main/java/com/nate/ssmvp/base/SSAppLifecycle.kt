package com.nate.ssmvp.base

import android.app.Application
import android.content.Context

/**
 * 定义一些 APP 声明周期的代理
 * Created by Nate on 2020/5/3
 */
interface SSAppLifecycle {

  fun attachBaseContext(base: Context)

  fun onCreate(application: Application)

  fun onTerminate(application: Application)

}