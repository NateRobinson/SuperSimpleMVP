package com.nate.ssmvp.mvp

/**
 * View interface
 * 定义一些通用的 View 接口，Activity or Fragment 会实现这个接口，再 Presenter 中可以调用这些接口
 * Created by Nate on 2020/5/1
 */
interface SSIView {
  /**
   * 显示加载框
   */
  fun showLoading()

  /**
   * 隐藏加载框
   */
  fun hideLoading()

  /**
   * 显示信息
   * @param message 消息内容
   */
  fun showMessage(message: String)
}
