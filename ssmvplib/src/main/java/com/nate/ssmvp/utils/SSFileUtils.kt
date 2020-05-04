package com.nate.ssmvp.utils

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * 文件管理工具类
 * Created by Nate on 2020/5/4
 */
object SSFileUtils {
  /**
   * 返回缓存文件夹
   */
  fun getCacheFile(context: Context): File {
    return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
      var file = context.externalCacheDir //获取系统管理的sd卡缓存文件
      if (file == null) { //如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
        file = File(getCacheFilePath(context))
        makeDirs(file)
      }
      file
    } else {
      context.cacheDir
    }
  }

  /**
   * 获取自定义缓存文件地址
   *
   * @param context
   * @return
   */
  private fun getCacheFilePath(context: Context): String {
    val packageName = context.packageName
    return "/mnt/sdcard/$packageName"
  }

  /**
   * 创建未存在的文件夹
   *
   * @param file
   * @return
   */
  fun makeDirs(file: File): File {
    if (!file.exists()) {
      file.mkdirs()
    }
    return file
  }
}