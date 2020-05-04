package com.nate.ssmvp.data.cache

import com.jess.arms.utils.Preconditions
import java.util.HashMap

/**
 * 提供一些配置类的全局依赖实例
 * Created by Nate on 2020/5/3
 */
class SmartCache<V>(size: Int) : SSCache<String, V> {
  private val mMap //可将数据永久存储至内存中的存储容器
      : MutableMap<String, V>
  private val mCache //当达到最大容量时可根据 LRU 算法抛弃不合规数据的存储容器
      : SSCache<String, V>

  /**
   * 将 [.mMap] 和 [.mCache] 的 `size` 相加后返回
   *
   * @return 相加后的 `size`
   */
  @Synchronized
  override fun size(): Int {
    return mMap.size + mCache.size()
  }

  /**
   * 将 [.mMap] 和 [.mCache] 的 `maxSize` 相加后返回
   *
   * @return 相加后的 `maxSize`
   */
  @Synchronized
  override fun getMaxSize(): Int {
    return mMap.size + mCache.maxSize
  }

  /**
   * 如果在 `key` 中使用 [.KEY_KEEP] 作为其前缀, 则操作 [.mMap], 否则操作 [.mCache]
   *
   * @param key `key`
   * @return `value`
   */
  @Synchronized
  override operator fun get(key: String): V? {
    return if (key.startsWith(KEY_KEEP)) {
      mMap[key]
    } else mCache[key]
  }

  /**
   * 如果在 `key` 中使用 [.KEY_KEEP] 作为其前缀, 则操作 [.mMap], 否则操作 [.mCache]
   *
   * @param key `key`
   * @param value `value`
   * @return 如果这个 `key` 在容器中已经储存有 `value`, 则返回之前的 `value` 否则返回 `null`
   */
  @Synchronized
  override fun put(key: String, value: V): V? {
    return if (key.startsWith(KEY_KEEP)) {
      mMap.put(key, value)
    } else mCache.put(key, value)
  }

  /**
   * 如果在 `key` 中使用 [.KEY_KEEP] 作为其前缀, 则操作 [.mMap], 否则操作 [.mCache]
   *
   * @param key `key`
   * @return 如果这个 `key` 在容器中已经储存有 `value` 并且删除成功则返回删除的 `value`, 否则返回 `null`
   */
  @Synchronized
  override fun remove(key: String): V? {
    return if (key.startsWith(KEY_KEEP)) {
      mMap.remove(key)
    } else mCache.remove(key)
  }

  /**
   * 如果在 `key` 中使用 [.KEY_KEEP] 作为其前缀, 则操作 [.mMap], 否则操作 [.mCache]
   *
   * @param key `key`
   * @return `true` 为在容器中含有这个 `key`, 否则为 `false`
   */
  @Synchronized
  override fun containsKey(key: String): Boolean {
    return if (key.startsWith(KEY_KEEP)) {
      mMap.containsKey(key)
    } else mCache.containsKey(key)
  }

  /**
   * 将 [.mMap] 和 [.mCache] 的 `keySet` 合并返回
   *
   * @return 合并后的 `keySet`
   */
  @Synchronized
  override fun keySet(): Set<String> {
    val set = mCache.keySet()
    set.addAll(mMap.keys)
    return set
  }

  /**
   * 清空 [.mMap] 和 [.mCache] 容器
   */
  override fun clear() {
    mCache.clear()
    mMap.clear()
  }

  companion object {
    const val KEY_KEEP = "Keep="

    /**
     * 使用此方法返回的值作为 key, 可以将数据永久存储至内存中
     *
     * @param key `key`
     * @return Keep= + `key`
     */
    fun getKeyOfKeep(key: String): String {
      Preconditions.checkNotNull(key, "key == null")
      return KEY_KEEP + key
    }
  }

  init {
    mMap = HashMap()
    mCache = SSLruCache(size)
  }
}
