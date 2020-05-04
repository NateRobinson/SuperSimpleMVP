package com.nate.ssmvp.mvp

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.trello.rxlifecycle3.RxLifecycle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Presenter 基类
 * Created by Nate on 2020/5/1
 */
class SSBasePresenter<M : SSIModel, V : SSIView> : SSIPresenter, LifecycleObserver {

  private val TAG = this.javaClass.simpleName
  private var mCompositeDisposable: CompositeDisposable? = null
  private var mModel: M? = null
  private var mRootView: V? = null

  constructor() {
    onStart()
  }

  /**
   * 页面同时需要 Model 和 View 层，调用此构造方法
   *
   * @param model
   * @param rootView
   */
  constructor(
    model: M,
    rootView: V
  ) : this() {
    mModel = model
    mRootView = rootView
  }

  /**
   * 页面只需要 View 层，调用此构造方法
   *
   * @param rootView
   */
  constructor(rootView: V) : this() {
    mRootView = rootView
  }

  override fun onStart() {
    //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
    if (mRootView != null && mRootView is LifecycleOwner) {
      (mRootView as LifecycleOwner).lifecycle.addObserver(this)
      if (mModel != null && mModel is LifecycleObserver) {
        (mRootView as LifecycleOwner).lifecycle.addObserver((mModel as LifecycleObserver))
      }
    }
  }

  override fun onDestroy() {
    //解除订阅
    unDispose()
    mModel?.onDestroy()
    mModel = null
    mRootView = null
    mCompositeDisposable = null
  }

  /**
   * 只有当 `mRootView` 不为 null, 并且 `mRootView` 实现了 [LifecycleOwner] 时, 此方法才会被调用
   */
  @OnLifecycleEvent(ON_DESTROY)
  fun onDestroy(owner: LifecycleOwner) {
    owner.lifecycle.removeObserver(this)
  }

  /**
   * 将 [Disposable] 添加到 [CompositeDisposable] 中统一管理
   * 可在  中使用 [.unDispose] 停止正在执行的 RxJava 任务,避免内存泄漏
   * 目前框架已使用 [RxLifecycle] 避免内存泄漏, 此方法作为备用方案
   * @param disposable
   */
  fun addDispose(disposable: Disposable) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = CompositeDisposable()
    }
    mCompositeDisposable?.add(disposable)
  }

  /**
   * 停止集合中正在执行的 RxJava 任务
   */
  private fun unDispose() {
    mCompositeDisposable?.clear()
  }
}