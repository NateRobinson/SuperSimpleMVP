package com.nate.ssmvp.imageloader

import android.widget.ImageView

/**
 * 图片加载库需要的配置基类
 * Created by Nate on 2020/5/4
 */
open abstract class SSImageConfig {
  var url: String? = null
    protected set
  var imageView: ImageView? = null
    protected set
  var placeholder //占位符
      = 0
    protected set
  var errorPic //错误占位符
      = 0
    protected set
}