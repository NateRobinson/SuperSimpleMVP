package com.nate.supersimplemvp.mvp.model

import com.google.gson.Gson
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel
import com.nate.supersimplemvp.data.api.GitHubService
import com.nate.supersimplemvp.data.cache.GitCacheProviders
import com.nate.supersimplemvp.entity.User
import com.nate.supersimplemvp.mvp.contract.MainContract
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject

/**
 * Created by Nate on 2020/5/5
 */
@ActivityScope
class MainModel @Inject constructor(repositoryManager: SSIRepositoryManager) : SSBaseModel(repositoryManager), MainContract.Model {
  @Inject
  lateinit var mGson: Gson

  override fun getGitUser(userName: String): Observable<User> {
    val gitHubService = mRepositoryManager.obtainRetrofitService(GitHubService::class.java)
    //val gitCacheProvides = mRepositoryManager.obtainCacheService(GitCacheProviders::class.java)
    //return gitCacheProvides.getUser(gitHubService.getRxUser(userName), DynamicKey(userName), EvictDynamicKey(false))
    return  gitHubService.getRxUser(userName)
  }
}