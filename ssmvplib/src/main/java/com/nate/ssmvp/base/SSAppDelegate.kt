package com.nate.ssmvp.base

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.Context
import android.content.res.Configuration
import com.jess.arms.base.delegate.AppLifecycles
import com.nate.ssmvp.config.SSMVPConfig
import com.nate.ssmvp.dagger.component.DaggerSSAppComponent
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.data.cache.SmartCache
import com.nate.ssmvp.utils.SSManifestParser
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named

/**
 * super simple mvp App 的代理类，为什么用代理，这样可以不必强制开发者必须继承 [SSBaseApp], 出于单继承的限制的考虑
 * Created by Nate on 2020/5/3
 */
class SSAppDelegate : SSIApp, SSAppLifecycle {

  private lateinit var mApplication: Application
  private lateinit var mSSAppComponent: SSAppComponent

  @Inject
  @Named("ActivityLifecycle")
  lateinit var mActivityLifecycle: ActivityLifecycleCallbacks

  @Inject
  @Named("ActivityLifecycleForRxLifecycle")
  lateinit var mActivityLifecycleForRxLifecycle: ActivityLifecycleCallbacks

  private var mModules: List<SSMVPConfig>
  private var mAppLifecycles: List<SSAppLifecycle> = ArrayList()
  private var mActivityLifecycles: List<ActivityLifecycleCallbacks> = ArrayList()
  private var mComponentCallback: ComponentCallbacks2? = null

  constructor(context: Context) {

    //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
    mModules = SSManifestParser(context).parse()

    //遍历之前获得的集合, 执行每一个 SSMVPConfig 实现类的某些方法
    for (module in mModules) {
      //注入开发者自定义的 AppLifeCycles
      module.injectAppLifecycle(context, mAppLifecycles)
      //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
      module.injectActivityLifecycle(context, mActivityLifecycles)
    }
  }

  override fun attachBaseContext(base: Context) {
    for (lifecycle in mAppLifecycles) {
      lifecycle.attachBaseContext(base)
    }
  }

  override fun onCreate(application: Application) {
    mApplication = application
    mSSAppComponent = DaggerSSAppComponent.builder()
        .application(mApplication) //提供application
        .ssConfigModule(getSSConfigModule(mApplication, mModules)) //全局配置
        .build()
    mSSAppComponent.inject(this)

    //将 ConfigModule 的实现类的集合存放到缓存 Cache, 可以随时获取
    //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
    //否则存储在 LRU 算法的存储空间中 (大于或等于缓存所能允许的最大 size, 则会根据 LRU 算法清除之前的条目)
    //前提是 extras 使用的是 IntelligentCache (框架默认使用)
    mSSAppComponent.extras()
        .put(SmartCache.getKeyOfKeep(SSMVPConfig::class.java.name), mModules)

    //注册框架内部已实现的 Activity 生命周期逻辑
    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle)
    //注册框架内部已实现的 RxLifecycle 逻辑
    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle)

    //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
    for (lifecycle in mActivityLifecycles) {
      mApplication.registerActivityLifecycleCallbacks(lifecycle)
    }

    mComponentCallback = SSAppComponentCallbacks(mApplication, mSSAppComponent)
    //注册回掉: 内存紧张时释放部分内存
    mApplication.registerComponentCallbacks(mComponentCallback)

    //执行框架外部, 开发者扩展的 App onCreate 逻辑
    for (lifecycle in mAppLifecycles) {
      lifecycle.onCreate(mApplication)
    }
  }

  /**
   * 将app的全局配置信息封装进 [SSConfigModule] (使用Dagger注入到需要配置信息的地方)
   * 需要在AndroidManifest中声明 [SSMVPConfig] 的实现类, 和Glide的配置方式相似
   * @return [SSConfigModule]
   */
  private fun getSSConfigModule(context: Context, modules: List<SSMVPConfig>): SSConfigModule {
    val builder = SSConfigModule.builder()
    //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
    for (module in modules) {
      module.applyOptions(context, builder)
    }
    return builder.build()
  }

  override fun getSSAppComponent(): SSAppComponent {
    return mSSAppComponent
  }

  override fun onTerminate(application: Application) {
    if (mActivityLifecycle != null) {
      mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
    }
    if (mActivityLifecycleForRxLifecycle != null) {
      mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle)
    }
    if (mComponentCallback != null) {
      mApplication.unregisterComponentCallbacks(mComponentCallback)
    }
    if (mActivityLifecycles.isNotEmpty()) {
      for (lifecycle in mActivityLifecycles) {
        mApplication.unregisterActivityLifecycleCallbacks(lifecycle)
      }
    }
    if (mAppLifecycles.isNotEmpty()) {
      for (lifecycle in mAppLifecycles) {
        lifecycle.onTerminate(mApplication)
      }
    }
    mComponentCallback = null
  }

  /**
   * [ComponentCallbacks2] 是一个细粒度的内存回收管理回调
   * 需要响应 onTrimMemory 做内存回收处理，使得 App 在进程中活的更久
   */
  private class SSAppComponentCallbacks internal constructor(private val mApplication: Application, private val mSSAppComponent: SSAppComponent) :
    ComponentCallbacks2 {

    /**
     * 只要走到此回调，说明设备的内存资源已经开始紧张
     * @param level 内存级别
     */
    override fun onTrimMemory(level: Int) {

      when (level) {
        TRIM_MEMORY_RUNNING_MODERATE -> {
          //状态1. 当开发者的 App 正在运行
          //设备开始运行缓慢, 不会被 kill, 也不会被列为可杀死的, 但是设备此时正运行于低内存状态下, 系统开始触发杀死 LRU 列表中的进程的机制
        }
        TRIM_MEMORY_RUNNING_LOW -> {
          //状态1. 当开发者的 App 正在运行
          //设备运行更缓慢了, 不会被 kill, 但请你回收 unused 资源, 以便提升系统的性能, 你应该释放不用的资源用来提升系统性能 (但是这也会直接影响到你的 App 的性能)
        }
        TRIM_MEMORY_RUNNING_CRITICAL -> {
          //状态1. 当开发者的 App 正在运行
          //设备运行特别慢, 当前 App 还不会被杀死, 但是系统已经把 LRU 列表中的大多数进程都已经杀死, 因此你应该立即释放所有非必须的资源
          //如果系统不能回收到足够的 RAM 数量, 系统将会清除所有的 LRU 列表中的进程, 并且开始杀死那些之前被认为不应该杀死的进程, 例如那个包含了一个运行态 Service 的进程
        }
        TRIM_MEMORY_UI_HIDDEN -> {
          //状态2. 当前 App UI 不再可见, 这是一个回收大个资源的好时机

        }
        TRIM_MEMORY_BACKGROUND -> {
          //状态3. 当前的 App 进程被置于 Background LRU 列表中
          //进程位于 LRU 列表的上端, 尽管你的 App 进程并不是处于被杀掉的高危险状态, 但系统可能已经开始杀掉 LRU 列表中的其他进程了
          //你应该释放那些容易恢复的资源, 以便于你的进程可以保留下来, 这样当用户回退到你的 App 的时候才能够迅速恢复
        }
        TRIM_MEMORY_MODERATE -> {
          //状态3. 当前的 App 进程被置于 Background LRU 列表中
          //系统正运行于低内存状态并且你的进程已经已经接近 LRU 列表的中部位置, 如果系统的内存开始变得更加紧张, 你的进程是有可能被杀死的
        }
        TRIM_MEMORY_COMPLETE -> {
          //状态3. 当前的 App 进程被置于 Background LRU 列表中
          //系统正运行于低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
          //低于 API 14 的 App 可以使用 onLowMemory 回调
        }
        else -> {
          // do nothing
        }
      }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}

    /**
     * 当系统开始清除 LRU 列表中的进程时, 尽管它会首先按照 LRU 的顺序来清除, 但是它同样会考虑进程的内存使用量, 因此消耗越少的进程则越容易被留下来
     * [ComponentCallbacks2.onTrimMemory] 的回调是在 API 14 才被加进来的, 对于老的版本, 你可以使用 [ComponentCallbacks2.onLowMemory] 方法来进行兼容
     * [ComponentCallbacks2.onLowMemory] 相当于 `onTrimMemory(TRIM_MEMORY_COMPLETE)`
     */
    override fun onLowMemory() {
      //系统正运行于低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
    }
  }
}