package com.nate.ssmvp.base

import com.jess.arms.di.component.AppComponent

/**
 * 需要所有的 Application 都实现此接口
 * Created by Nate on 2020/5/3
 */
interface SSIApp {
  fun getAppComponent(): AppComponent
}