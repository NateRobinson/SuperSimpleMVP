package com.nate.ssmvp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jess.arms.integration.lifecycle.FragmentLifecycleable
import com.jess.arms.utils.ArmsUtils
import com.nate.ssmvp.mvp.SSIPresenter
import com.trello.rxlifecycle3.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * super simple mvp Fragment 基类
 * Created by Nate on 2020/5/3
 */
open abstract class SSBaseFragment<P : SSIPresenter> : Fragment(), SSIFragment, FragmentLifecycleable {
  private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
  private lateinit var mContext: Context

  @Inject
  lateinit var mPresenter: P

  override fun provideLifecycleSubject(): Subject<FragmentEvent> {
    return mLifecycleSubject
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    mContext = context
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupFragmentComponent(ArmsUtils.obtainAppComponentFromContext(mContext))
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return initView(inflater, container, savedInstanceState)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initData(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.onDestroy()
  }
}