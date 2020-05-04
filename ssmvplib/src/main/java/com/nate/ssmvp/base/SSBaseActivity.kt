package com.nate.ssmvp.base

import android.os.Bundle
import android.view.InflateException
import androidx.appcompat.app.AppCompatActivity
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.utils.ArmsUtils
import com.nate.ssmvp.mvp.SSIPresenter
import com.trello.rxlifecycle3.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * super simple mvp Activity 基类
 * Created by Nate on 2020/5/3
 */
open abstract class SSBaseActivity<P : SSIPresenter> : AppCompatActivity(), SSIActivity,
  ActivityLifecycleable {
  private val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()

  @Inject
  lateinit var mPresenter: P

  override fun provideLifecycleSubject(): Subject<ActivityEvent> {
    return mLifecycleSubject
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    try {
      val layoutResID: Int = initView(savedInstanceState)
      if (layoutResID != 0) {
        setContentView(layoutResID)
      }
    } catch (e: Exception) {
      if (e is InflateException) throw e
      e.printStackTrace()
    }
    setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(this))
    initData(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    // 释放资源
    mPresenter.onDestroy()
  }
}