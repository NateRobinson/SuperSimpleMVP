package com.nate.ssmvp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nate.ssmvp.lifecycle.rxlifecycle.SSFragmentLifecycleAble
import com.nate.ssmvp.mvp.SSIPresenter
import com.nate.ssmvp.utils.SSMvpUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * super simple mvp BottomSheetDialogFragment 基类
 * Created by Nate on 2021/5/20
 */
abstract class SSBaseBottomSheetDialogFragment<P : SSIPresenter> : BottomSheetDialogFragment(), SSIFragment, SSFragmentLifecycleAble {
  private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
  protected lateinit var mContext: Context

  @JvmField
  @Inject
  protected var mPresenter: P? = null

  override fun provideLifecycleSubject(): Subject<FragmentEvent> {
    return mLifecycleSubject
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    mContext = context
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupFragmentComponent(SSMvpUtils.obtainAppComponentFromContext(mContext))
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
    mPresenter?.onDestroy()
    mPresenter = null
  }
}