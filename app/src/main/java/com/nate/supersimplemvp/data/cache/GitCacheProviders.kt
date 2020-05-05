package com.nate.supersimplemvp.data.cache

import com.nate.supersimplemvp.entity.User
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

/**
 * Created by Nate on 2020/5/5
 */
interface GitCacheProviders {
  @LifeCache(duration = 10, timeUnit = TimeUnit.SECONDS)
  fun getUser(oRepos: Observable<User>, userName: DynamicKey, evictDynamicKey: EvictDynamicKey): Observable<User>
}