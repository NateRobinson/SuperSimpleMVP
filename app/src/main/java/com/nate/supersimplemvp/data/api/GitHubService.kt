package com.nate.supersimplemvp.data.api

import com.nate.supersimplemvp.entity.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Nate on 2020/5/5
 */
interface GitHubService {
  @GET("users/{user}")
  fun getRxUser(@Path("user") user: String): Observable<User>
}