package com.nate.ssmvp.base

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks2
import android.content.Context
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.component.AppComponent
import com.nate.ssmvp.config.SSMVPConfig
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.utils.SSManifestParser
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Nate on 2020/5/3
 */
class SSAppDelegate : SSIApp, SSAppLifecycle {

  private lateinit var mApplication: Application
  private lateinit var mAppComponent: AppComponent

  @Inject
  @Named("ActivityLifecycle")
  lateinit var mActivityLifecycle: ActivityLifecycleCallbacks

  @Inject
  @Named("ActivityLifecycleForRxLifecycle")
  lateinit var mActivityLifecycleForRxLifecycle: ActivityLifecycleCallbacks

  private var mModules: List<SSMVPConfig>
  private var mAppLifecycles: List<AppLifecycles> = ArrayList()
  private var mActivityLifecycles: List<ActivityLifecycleCallbacks> = ArrayList()
  private var mComponentCallback: ComponentCallbacks2? = null

  constructor(context: Context) {

    //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
    mModules = SSManifestParser(context).parse()

    //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法

    //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
    for (module in mModules) {

      //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
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
    //    mAppComponent = DaggerSSAppComponent.builder()
    //        .application(mApplication) //提供application
    //        .globalConfigModule(getGlobalConfigModule(mApplication, mModules)) //全局配置
    //        .build()
    //    mAppComponent.inject(this)

    //将 ConfigModule 的实现类的集合存放到缓存 Cache, 可以随时获取
    //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
    //否则存储在 LRU 算法的存储空间中 (大于或等于缓存所能允许的最大 size, 则会根据 LRU 算法清除之前的条目)
    //前提是 extras 使用的是 IntelligentCache (框架默认使用)

    //将 ConfigModule 的实现类的集合存放到缓存 Cache, 可以随时获取
    //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
    //否则存储在 LRU 算法的存储空间中 (大于或等于缓存所能允许的最大 size, 则会根据 LRU 算法清除之前的条目)
    //前提是 extras 使用的是 IntelligentCache (框架默认使用)
    //    mAppComponent.extras()
    //        .put(IntelligentCache.getKeyOfKeep(ConfigModule::class.java.name), mModules)

    //注册框架内部已实现的 Activity 生命周期逻辑

    //注册框架内部已实现的 Activity 生命周期逻辑
    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle)

    //注册框架内部已实现的 RxLifecycle 逻辑

    //注册框架内部已实现的 RxLifecycle 逻辑
    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle)

    //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
    //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
    //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)

    //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
    //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
    //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)
    for (lifecycle in mActivityLifecycles) {
      mApplication.registerActivityLifecycleCallbacks(lifecycle)
    }

    //mComponentCallback = AppComponentCallbacks(mApplication, mAppComponent)

    //注册回掉: 内存紧张时释放部分内存

    //注册回掉: 内存紧张时释放部分内存
    //mApplication.registerComponentCallbacks(mComponentCallback)

    //执行框架外部, 开发者扩展的 App onCreate 逻辑

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

  override fun getAppComponent(): AppComponent {
    return mAppComponent!!
  }

  override fun onTerminate(application: Application) {
  }

}