package com.nate.supersimplemvp.config

import android.content.Context
import android.net.ParseException
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 自定义网络错误处理
 * Created by Nate on 2020/5/5
 */
class AppResponseErrorHandler : ResponseErrorListener {
  override fun handleResponseError(context: Context, t: Throwable) {
    Timber.tag("AppResponseErrorHandler").w(t.message)
    var msg: String? = "未知错误"
    if (t is UnknownHostException) {
      msg = "网络不可用"
    } else if (t is SocketTimeoutException) {
      msg = "请求网络超时"
    } else if (t is HttpException) {
      msg = convertStatusCode(t)
    } else if (t is JsonParseException || t is ParseException || t is JSONException || t is JsonIOException) {
      msg = "数据解析错误"
    }
    ToastUtils.showShort(msg)
  }

  private fun convertStatusCode(httpException: HttpException): String? {
    return when {
      httpException.code() == 500 -> {
        "服务器发生错误"
      }
      httpException.code() == 404 -> {
        "请求地址不存在"
      }
      httpException.code() == 403 -> {
        "请求被服务器拒绝"
      }
      httpException.code() == 307 -> {
        "请求被重定向到其他页面"
      }
      else -> {
        httpException.message()
      }
    }
  }

}