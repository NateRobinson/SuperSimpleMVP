package com.nate.ssmvp.http

import okhttp3.Interceptor.Chain
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * OKHttp 请求和响应结果的处理类
 * Created by Nate on 2020/5/4
 */
interface SSOkHttpHandler {
  /**
   * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
   * 重新请求 token, 并重新执行请求
   *
   * @param chain [Interceptor]
   * @param response [Response]
   * @return [Response]
   */
  fun onHttpResultResponse(chain: Chain): Response

  /**
   * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 token 或者 header 以及参数加密等操作
   *
   * @param chain [Interceptor.Chain]
   * @param request [Request]
   * @return [Request]
   */
  fun onHttpRequestBefore(chain: Chain, request: Request): Request

  companion object {
    /**
     * 空实现
     */
    val EMPTY: SSOkHttpHandler = object : SSOkHttpHandler {
      override fun onHttpResultResponse(chain: Chain): Response {
        //不管是否处理, 都必须将 response 返回出去
        return chain.proceed(chain.request())
      }

      override fun onHttpRequestBefore(chain: Chain, request: Request): Request {
        //不管是否处理, 都必须将 request 返回出去
        return request
      }
    }
  }
}