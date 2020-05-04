package com.nate.ssmvp.imageloader

/**
 * 定义一层通用的图片加载策略类型，方便切换图片加载框架时最小化的修改代码
 * Created by Nate on 2020/5/3
 */
enum class SSImageCacheStrategy {
  ALL,
  NONE,
  RESOURCE,
  DATA,
  AUTOMATIC
}