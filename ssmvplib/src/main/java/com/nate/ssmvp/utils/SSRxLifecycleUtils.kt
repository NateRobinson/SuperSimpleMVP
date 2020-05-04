package com.nate.ssmvp.utils

import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.lifecycle.rxlifecycle.SSActivityLifecycleAble
import com.nate.ssmvp.lifecycle.rxlifecycle.SSFragmentLifecycleAble
import com.nate.ssmvp.lifecycle.rxlifecycle.SSLifecycleAble
import com.nate.ssmvp.mvp.SSIView
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid

/**
 * 让继承 [SSBaseActivity] 或者 [SSBaseFragment] 的 Activity 或 Fragment 都可以使用 [RxLifecycle]
 * Created by Nate on 2020/5/3
 */
object SSRxLifecycleUtils {

  /**
   * 绑定 Activity 的指定生命周期
   */
  fun <T> bindUntilEvent(view: SSIView, event: ActivityEvent): LifecycleTransformer<T> {
    return if (view is SSActivityLifecycleAble) {
      bindUntilEvent(view as SSActivityLifecycleAble, event)
    } else {
      throw IllegalArgumentException("view isn't ActivityLifecycleable")
    }
  }

  /**
   * 绑定 Fragment 的指定生命周期
   */
  fun <T> bindUntilEvent(view: SSIView, event: FragmentEvent): LifecycleTransformer<T> {
    return if (view is SSFragmentLifecycleAble) {
      bindUntilEvent(view as SSFragmentLifecycleAble, event)
    } else {
      throw IllegalArgumentException("view isn't FragmentLifecycleable")
    }
  }

  private fun <T, R> bindUntilEvent(lifecycleAble: SSLifecycleAble<R>, event: R): LifecycleTransformer<T> {
    return RxLifecycle.bindUntilEvent(lifecycleAble.provideLifecycleSubject(), event)
  }

  /**
   * 绑定 Activity/Fragment 的生命周期
   */
  fun <T> bindToLifecycle(view: SSIView): LifecycleTransformer<T> {
    return if (view is SSLifecycleAble<*>) {
      bindToLifecycle(view as SSLifecycleAble<*>)
    } else {
      throw IllegalArgumentException("view isn't Lifecycleable")
    }
  }

  private fun <T> bindToLifecycle(lifecycleAble: SSLifecycleAble<*>): LifecycleTransformer<T> {
    return when (lifecycleAble) {
      is SSActivityLifecycleAble -> {
        RxLifecycleAndroid.bindActivity(lifecycleAble.provideLifecycleSubject())
      }
      is SSFragmentLifecycleAble -> {
        RxLifecycleAndroid.bindFragment(lifecycleAble.provideLifecycleSubject())
      }
      else -> {
        throw IllegalArgumentException("Lifecycleable not match")
      }
    }
  }
}