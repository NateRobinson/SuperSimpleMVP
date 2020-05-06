package com.nate.supersimplemvp.config

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * Created by Nate on 2020/5/5
 */
class AppActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
  override fun onActivityPaused(activity: Activity) {
    Timber.i("$activity - onActivityPaused")
  }

  override fun onActivityStarted(activity: Activity) {
    Timber.i("$activity - onActivityStarted")
  }

  override fun onActivityDestroyed(activity: Activity) {
    Timber.i("$activity - onActivityDestroyed")
    EventBus
      .getDefault()
      .unregister(activity)
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    Timber.i("$activity - onActivitySaveInstanceState")
  }

  override fun onActivityStopped(activity: Activity) {
    Timber.i("$activity - onActivityStopped")
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    Timber.i("$activity - onActivityCreated")
    EventBus
      .getDefault()
      .register(activity)
  }

  override fun onActivityResumed(activity: Activity) {
    Timber.i("$activity - onActivityResumed")
  }
}
