package com.nate.supersimplemvp.config

import com.nate.ssmvp.http.SSOkHttpHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

/**
 * 自定义的全局 OkHttp 拦截处理器
 * Created by Nate on 2020/5/5
 */
class AppOkHttpHandler : SSOkHttpHandler {

  /**
   * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 token 或者 header 以及参数加密等操作
   *
   * @param chain [Interceptor.Chain]
   * @param request [Request]
   * @return [Request]
   */
  override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
    Timber.d("****************onHttpRequestBefore****************")
    return request
  }

  /**
   * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
   * 重新请求 token, 并重新执行请求
   *
   * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
   * @param chain [Interceptor.Chain]
   * @param response [Response]
   * @return [Response]
   */
  override fun onHttpResultResponse(chain: Interceptor.Chain): Response {
    Timber.d("****************onHttpResultResponse****************")
    return chain.proceed(chain.request())
  }
}
