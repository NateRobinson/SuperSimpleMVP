package com.nate.ssmvp.mvp

/**
 * Model interface
 * 定义一些通用的 Model 生命周期接口，Model 实现类会实现这些生命周期接口
 * Created by Nate on 2020/5/1
 */
interface SSIModel {
  /**
   * [SSBasePresenter.onDestroy] 时会默认调用此方法
   */
  fun onDestroy()
}