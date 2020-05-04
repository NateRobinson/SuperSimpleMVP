package com.nate.ssmvp.base

import com.nate.ssmvp.mvp.SSIPresenter
import javax.inject.Inject

/**
 * super simple mvp 懒加载 Fragment 基类
 * Created by Nate on 2020/5/4
 */
abstract class SSBaseLazyLoadFragment<P : SSIPresenter> : SSBaseFragment<P>() {

  @Inject
  lateinit var mUnused: Unused
  private var isViewCreated = false // 界面是否已创建完成 = false
  private var isVisibleToUser = false // 是否对用户可见 = false
  private var isDataLoaded = false // 数据是否已请求 = false

  /**
   * 第一次可见时触发调用,此处实现具体的数据请求逻辑
   */
  protected abstract fun lazyLoadData()

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    this.isVisibleToUser = isVisibleToUser
    tryLoadData()
  }

  /**
   * 保证在initData后触发
   */
  override fun onResume() {
    super.onResume()
    isViewCreated = true
    tryLoadData()
  }

  /**
   * ViewPager场景下，判断父fragment是否可见
   */
  private val isParentVisible: Boolean
    get() {
      val fragment = parentFragment
      return fragment == null || fragment is SSBaseLazyLoadFragment<*> && fragment.isVisibleToUser
    }

  fun tryLoadData() {
    if (isViewCreated && isVisibleToUser && isParentVisible && !isDataLoaded) {
      lazyLoadData()
      isDataLoaded = true
      //通知子Fragment请求数据
      dispatchParentVisibleState()
    }
  }

  /**
   * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
   */
  private fun dispatchParentVisibleState() {
    val fragmentManager = childFragmentManager
    val fragments = fragmentManager.fragments
    if (fragments.isEmpty()) {
      return
    }
    for (child in fragments) {
      if (child is SSBaseLazyLoadFragment<*> && child.isVisibleToUser) {
        child.tryLoadData()
      }
    }
  }
}