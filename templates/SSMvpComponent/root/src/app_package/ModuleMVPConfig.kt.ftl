package ${packageName}.config;

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

import ${packageName}.BuildConfig
import com.squareup.leakcanary.RefWatcher

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
    //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码, 所以不需要再执行
    if (BuildConfig.IS_BUILD_MODULE) {
      lifecycles.add(object : FragmentLifecycleCallbacks() {
        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
          (SSMvpUtils.obtainAppComponentFromContext(f.activity as Context)
            .extras()[SmartCache.getKeyOfKeep(RefWatcher::class.java.name)] as RefWatcher).watch(f)
        }
      })
    }
  }
}
