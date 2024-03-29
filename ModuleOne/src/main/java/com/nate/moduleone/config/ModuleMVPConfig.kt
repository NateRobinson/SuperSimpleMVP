package com.nate.moduleone.config;

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.config.SSMVPConfig
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.utils.SSMvpUtils

import com.nate.moduleone.BuildConfig

/**
 * 每个 module 都可以实现 SSMVPConfig 接口做一些自定义的配置
 * Created by Nate
 */
class ModuleMVPConfig : SSMVPConfig {
  override fun applyOptions(context: Context, builder: SSConfigModule.Builder) {

  }

  override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<SSAppLifecycle>) {
    lifecycles.add(ModuleLifecycle())
  }

  override fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>) {

  }

  override fun injectFragmentLifecycle(context: Context, lifecycles: ArrayList<FragmentManager.FragmentLifecycleCallbacks>) {
  }
}
