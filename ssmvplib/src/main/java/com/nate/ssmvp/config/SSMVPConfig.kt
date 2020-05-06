package com.nate.ssmvp.config

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.dagger.module.SSConfigModule.Builder

/**
 * super simple mvp 配置接口
 * Created by Nate on 2020/5/3
 */
interface SSMVPConfig {
  /**
   * 使用 [SSConfigModule.Builder] 给框架配置一些配置参数
   *
   * @param context [Context]
   * @param builder [SSConfigModule.Builder]
   */
  fun applyOptions(context: Context, builder: Builder)

  /**
   * 使用 [SSAppLifecycle] 在 [Application] 的生命周期中注入一些操作
   *
   * @param context    [Context]
   * @param lifecycles [Application] 的生命周期容器, 可向框架中添加多个 [Application] 的生命周期类
   */
  fun injectAppLifecycle(context: Context, lifecycles: ArrayList<SSAppLifecycle>)

  /**
   * 使用 [Application.ActivityLifecycleCallbacks] 在 [Activity] 的生命周期中注入一些操作
   *
   * @param context    [Context]
   * @param lifecycles [Activity] 的生命周期容器, 可向框架中添加多个 [Activity] 的生命周期类
   */
  fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<ActivityLifecycleCallbacks>)

  /**
   * 使用 [FragmentManager.FragmentLifecycleCallbacks] 在 [Fragment] 的生命周期中注入一些操作
   *
   * @param context    [Context]
   * @param lifecycles [Fragment] 的生命周期容器, 可向框架中添加多个 [Fragment] 的生命周期类
   */
  fun injectFragmentLifecycle(context: Context, lifecycles: ArrayList<FragmentLifecycleCallbacks>)
}