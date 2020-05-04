package com.nate.ssmvp.base

import android.app.Application
import com.jess.arms.di.component.AppComponent

/**
 * Created by Nate on 2020/5/3
 */
class SSBaseApp : Application(), SSIApp {

  private lateinit var mAppDelegate: SSAppLifecycle



  override fun getAppComponent(): AppComponent {
    return (mAppDelegate as SSIApp).getAppComponent()
  }
}