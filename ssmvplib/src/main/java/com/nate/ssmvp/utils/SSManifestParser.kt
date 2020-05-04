package com.nate.ssmvp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import com.nate.ssmvp.base.SSAppDelegate
import com.nate.ssmvp.config.SSMVPConfig
import java.util.ArrayList

/**
 * 用来对 AndroidManifest 里面的 meta 做解析，获得 SSMVPConfig 供 [SSAppDelegate] 中使用
 * Created by Nate on 2020/5/3
 */
private const val MODULE_VALUE = "SSMVPConfig"

class SSManifestParser {
  private var context: Context? = null

  constructor(context: Context?) {
    this.context = context
  }

  fun parse(): List<SSMVPConfig> {
    val modules: MutableList<SSMVPConfig> = ArrayList()
    try {
      val appInfo = context!!.packageManager.getApplicationInfo(
          context?.packageName ?: "", PackageManager.GET_META_DATA
      )
      if (appInfo.metaData != null) {
        for (key in appInfo.metaData.keySet()) {
          if (MODULE_VALUE == appInfo.metaData[key]) {
            modules.add(parseModule(key))
          }
        }
      }
    } catch (e: NameNotFoundException) {
      throw RuntimeException("Unable to find metadata to parse SSMVPConfig", e)
    }
    return modules
  }

  private fun parseModule(className: String): SSMVPConfig {
    val clazz: Class<*>
    clazz = try {
      Class.forName(className)
    } catch (e: ClassNotFoundException) {
      throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
    }
    val module: Any
    module = try {
      clazz.newInstance()
    } catch (e: InstantiationException) {
      throw RuntimeException(
          "Unable to instantiate ConfigModule implementation for $clazz", e
      )
    } catch (e: IllegalAccessException) {
      throw RuntimeException(
          "Unable to instantiate ConfigModule implementation for $clazz", e
      )
    }
    if (module !is SSMVPConfig) {
      throw RuntimeException("Expected instanceof ConfigModule, but found: $module")
    }
    return module
  }
}