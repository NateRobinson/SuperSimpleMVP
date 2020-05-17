package com.nate.ssmvp.base

import android.os.Bundle
import android.view.InflateException
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nate.ssmvp.lifecycle.rxlifecycle.SSActivityLifecycleAble
import com.nate.ssmvp.mvp.SSIPresenter
import com.nate.ssmvp.utils.SSMvpUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * super simple mvp Activity 基类
 * Created by Nate on 2020/5/3
 */
abstract class SSBaseActivity<P : SSIPresenter> : AppCompatActivity(), SSIActivity, SSActivityLifecycleAble {
  private val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()

  @JvmField
  @Inject
  protected var mPresenter: P? = null
  private var mUnbinder: Unbinder? = null

  override fun provideLifecycleSubject(): Subject<ActivityEvent> {
    return mLifecycleSubject
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    try {
      val layoutResID: Int = initView(savedInstanceState)
      if (layoutResID != 0) {
        setContentView(layoutResID)
        mUnbinder = ButterKnife.bind(this)
      }
    } catch (e: Exception) {
      if (e is InflateException) throw e
      e.printStackTrace()
    }
    setupActivityComponent(SSMvpUtils.obtainAppComponentFromContext(this))
    initData(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
      mUnbinder?.unbind()
    }
    mUnbinder = null
    mPresenter?.onDestroy()
    mPresenter = null
  }
}