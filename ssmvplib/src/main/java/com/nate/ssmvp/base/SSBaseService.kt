package com.nate.ssmvp.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * super simple mvp Service 基类
 * Created by Nate on 2020/5/4
 */
abstract class SSBaseService : Service() {
  var mCompositeDisposable: CompositeDisposable? = null
  override fun onBind(intent: Intent): IBinder? {
    return null
  }

  override fun onCreate() {
    super.onCreate()
    init()
  }

  override fun onDestroy() {
    super.onDestroy()
    mCompositeDisposable?.clear()
    mCompositeDisposable = null
  }

  protected fun addDispose(disposable: Disposable) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = CompositeDisposable()
    }
    mCompositeDisposable?.add(disposable)
  }

  /**
   * 初始化
   */
  abstract fun init()
}