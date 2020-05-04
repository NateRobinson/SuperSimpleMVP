package com.nate.ssmvp.lifecycle.rxlifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.ActivityEvent.CREATE
import com.trello.rxlifecycle3.android.ActivityEvent.DESTROY
import com.trello.rxlifecycle3.android.ActivityEvent.PAUSE
import com.trello.rxlifecycle3.android.ActivityEvent.RESUME
import com.trello.rxlifecycle3.android.ActivityEvent.START
import com.trello.rxlifecycle3.android.ActivityEvent.STOP
import dagger.Lazy
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ActivityLifecycleCallbacks 用来使实现了 [SSLifecycleAble] 接口的 Activity 拥有使用 [RxLifecycle] 的能力
 * Created by Nate on 2020/5/4
 */
@Singleton
class SSALifecycleForRxLifecycle @Inject constructor() : ActivityLifecycleCallbacks {
  @Inject
  lateinit var mFragmentLifecycle: Lazy<SSFLifecycleForRxLifecycle>

  /**
   * 通过桥梁对象 `BehaviorSubject<ActivityEvent> mLifecycleSubject`
   * 在每个 Activity 的生命周期中发出对应的生命周期事件
   */
  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(CREATE)
      if (activity is FragmentActivity) {
        (activity as FragmentActivity).supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true)
      }
    }
  }

  override fun onActivityStarted(activity: Activity) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(START)
    }
  }

  override fun onActivityResumed(activity: Activity) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(RESUME)
    }
  }

  override fun onActivityPaused(activity: Activity) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(PAUSE)
    }
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(STOP)
    }
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
  override fun onActivityDestroyed(activity: Activity) {
    if (activity is SSActivityLifecycleAble) {
      obtainSubject(activity).onNext(DESTROY)
    }
  }

  private fun obtainSubject(activity: Activity): Subject<ActivityEvent> {
    return (activity as SSActivityLifecycleAble).provideLifecycleSubject()
  }
}