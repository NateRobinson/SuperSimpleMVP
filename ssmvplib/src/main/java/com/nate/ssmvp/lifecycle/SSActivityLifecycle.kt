package com.nate.ssmvp.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.jess.arms.base.BaseFragment
import com.jess.arms.base.delegate.FragmentDelegate
import com.jess.arms.base.delegate.IActivity
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * super simple mvp 框架提供的默认 ActivityLifecycleCallbacks 实现
 * Created by Nate on 2020/5/3
 */
@Singleton
class SSActivityLifecycle @Inject constructor() : ActivityLifecycleCallbacks {

  @Inject
  lateinit var mFragmentLifecycles: Lazy<List<FragmentLifecycleCallbacks>>

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    registerFragmentCallbacks(activity)
  }

  override fun onActivityStarted(activity: Activity) {
  }

  override fun onActivityResumed(activity: Activity) {
  }

  override fun onActivityPaused(activity: Activity) {
  }

  override fun onActivityStopped(activity: Activity) {
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
  }

  override fun onActivityDestroyed(activity: Activity) {
  }

  /**
   * 给每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 [IActivity.useFragment]
   * 设置是否使用监听,如果这个 Activity 返回 false 的话,这个 Activity 下面的所有 Fragment 将不能使用 [FragmentDelegate]
   * 意味着 [BaseFragment] 也不能使用
   *
   * @param activity
   */
  private fun registerFragmentCallbacks(activity: Activity) {
    if (activity is FragmentActivity) {
      // 注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
      for (fragmentLifecycle in mFragmentLifecycles.get()) {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycle, true)
      }
    }
  }
}