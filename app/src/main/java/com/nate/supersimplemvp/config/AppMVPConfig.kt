package com.nate.supersimplemvp.config

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.nate.ssmvp.base.SSAppLifecycle
import com.nate.ssmvp.config.SSMVPConfig
import com.nate.ssmvp.dagger.module.SSConfigModule
import com.nate.ssmvp.dagger.module.SSThirdLibModule
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS

/**
 * 每个 module 都可以实现 SSMVPConfig 接口做一些自定义的配置
 * Created by Nate on 2020/5/5
 */
class AppMVPConfig : SSMVPConfig {
  override fun applyOptions(context: Context, builder: SSConfigModule.Builder) {
    builder.okHttpHandler(AppOkHttpHandler()).responseErrorListener(AppResponseErrorHandler())
      .gsonConfiguration(object : SSThirdLibModule.GsonConfiguration {
        override fun configGson(context: Context, builder: GsonBuilder) {
          builder.serializeNulls() //支持序列化值为 null 的参数
            .enableComplexMapKeySerialization() //支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
            .disableHtmlEscaping()
        }
      }).okHttpConfiguration(object : SSThirdLibModule.OkHttpConfiguration {
        override fun configOkHttp(context: Context, builder: Builder) {
          val loggingInterceptor = HttpLoggingInterceptor(object : Logger {
            override fun log(message: String) {
              Timber.tag("APP-OkHttp").d(message);
            }
          })
          loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
          builder.addInterceptor(loggingInterceptor)
          //自定义配置 Okhttp 的参数
          builder.writeTimeout(10, SECONDS)
          //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
          ProgressManager.getInstance().with(builder)
          //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
          RetrofitUrlManager.getInstance().with(builder)
        }
      })

  }

  override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<SSAppLifecycle>) {
    lifecycles.add(AppLifecycle())
  }

  override fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>) {
    lifecycles.add(AppActivityLifecycleCallbacks())
  }

  override fun injectFragmentLifecycle(context: Context, lifecycles: ArrayList<FragmentManager.FragmentLifecycleCallbacks>) {
    lifecycles.add(AppFragmentLifecycleCallbacks())
  }
}
