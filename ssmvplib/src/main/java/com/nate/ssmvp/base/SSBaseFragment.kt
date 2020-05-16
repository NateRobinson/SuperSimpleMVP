package com.nate.ssmvp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nate.ssmvp.lifecycle.rxlifecycle.SSFragmentLifecycleAble
import com.nate.ssmvp.mvp.SSIPresenter
import com.nate.ssmvp.utils.SSMvpUtils
import com.trello.rxlifecycle3.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject

/**
 * super simple mvp Fragment 基类
 * Created by Nate on 2020/5/3
 */
abstract class SSBaseFragment<P : SSIPresenter> : Fragment(), SSIFragment, SSFragmentLifecycleAble {
  private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
  protected lateinit var mContext: Context
  private var mUnbinder: Unbinder? = null

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
    val view = initView(inflater, container, savedInstanceState)
    if (view != null) {
      mUnbinder = ButterKnife.bind(this, view)
    }
    return view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initData(savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
      try {
        mUnbinder?.unbind()
      } catch (e: IllegalStateException) {
        e.printStackTrace()
        //fix Bindings already cleared
        Timber.w("onDestroyView: ${e.message}")
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mUnbinder = null
    mPresenter?.onDestroy()
    mPresenter = null
  }
}