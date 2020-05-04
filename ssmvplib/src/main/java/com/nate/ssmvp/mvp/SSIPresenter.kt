package com.nate.ssmvp.mvp

/**
 * Presenter interface
 * 定义一些通用的 Presenter 声明周期接口，Presenter 实现类会实现这些生命周期接口
 * Created by Nate on 2020/5/1
 */
interface SSIPresenter {
  /**
   * 约定的方法，Presenter 会在这里面做一些初始化工作
   */
  fun onStart()

  /**
   * 在 BaseActivity 中会调用 Presenter 的这个方法做资源释放
   */
  fun onDestroy()
}