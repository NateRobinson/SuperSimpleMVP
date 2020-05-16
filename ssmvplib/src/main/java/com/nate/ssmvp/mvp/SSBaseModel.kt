package com.nate.ssmvp.mvp

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.nate.ssmvp.data.SSIRepositoryManager

/**
 * Model 基类
 * Created by Nate on 2020/5/1
 */
open class SSBaseModel : SSIModel, LifecycleObserver {

  //用于管理网络请求层, 以及数据缓存层
  protected var mRepositoryManager: SSIRepositoryManager

  constructor(repositoryManager: SSIRepositoryManager) {
    mRepositoryManager = repositoryManager
  }

  override fun onDestroy() {
  }

  @OnLifecycleEvent(ON_DESTROY)
  fun onDestroy(owner: LifecycleOwner) {
    owner.lifecycle.removeObserver(this)
  }
}