package com.nate.ssmvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.di.component.AppComponent

/**
 * 此接口用来约定 Fragment 必须实现的一些接口
 * Created by Nate on 2020/5/3
 */
interface SSIFragment {
  /**
   * 配置 Fragment 的依赖
   *
   * @param appComponent
   */
  fun setupFragmentComponent(appComponent: AppComponent)

  /**
   * View 初始化
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

  /**
   * 数据初始化
   *
   * @param savedInstanceState
   */
  fun initData(savedInstanceState: Bundle?)
}