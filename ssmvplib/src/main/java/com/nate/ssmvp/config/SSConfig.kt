package com.nate.ssmvp.config

import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * 一些框架的常量配置
 * Created by Nate on 2020/5/4
 */
object SSConfig {

  val DEFAULT_BASE_URL = "https://api.github.com/".toHttpUrl()

}