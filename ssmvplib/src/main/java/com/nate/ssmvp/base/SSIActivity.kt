package com.nate.ssmvp.base

import android.os.Bundle
import com.jess.arms.di.component.AppComponent
import com.nate.ssmvp.dagger.component.SSAppComponent

/**
 * 此接口用来约定 Activity 必须实现的一些接口
 * Created by Nate on 2020/5/3
 */
interface SSIActivity {

  /**
   * 配置 Activity 的依赖
   *
   * @param ssAppComponent
   */
  fun setupActivityComponent(ssAppComponent: SSAppComponent)

  /**
   * View 初始化
   * @param savedInstanceState
   * @return 0 -- 适用于使用 DataBinding；other -- 绑定布局文件
   */
  fun initView(savedInstanceState: Bundle?): Int

  /**
   * 数据初始化
   *
   * @param savedInstanceState
   */
  fun initData(savedInstanceState: Bundle?)
}