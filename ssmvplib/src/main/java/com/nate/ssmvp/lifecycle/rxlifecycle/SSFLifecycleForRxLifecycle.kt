package com.nate.ssmvp.lifecycle.rxlifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.FragmentEvent.ATTACH
import com.trello.rxlifecycle3.android.FragmentEvent.CREATE
import com.trello.rxlifecycle3.android.FragmentEvent.CREATE_VIEW
import com.trello.rxlifecycle3.android.FragmentEvent.DESTROY
import com.trello.rxlifecycle3.android.FragmentEvent.DESTROY_VIEW
import com.trello.rxlifecycle3.android.FragmentEvent.DETACH
import com.trello.rxlifecycle3.android.FragmentEvent.PAUSE
import com.trello.rxlifecycle3.android.FragmentEvent.RESUME
import com.trello.rxlifecycle3.android.FragmentEvent.START
import com.trello.rxlifecycle3.android.FragmentEvent.STOP
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FragmentLifecycleCallbacks 用来使实现了 [SSLifecycleAble] 接口的 Fragment 拥有使用 [RxLifecycle] 的能力
 * Created by Nate on 2020/5/4
 */
@Singleton
class SSFLifecycleForRxLifecycle @Inject constructor() : FragmentLifecycleCallbacks() {
  override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(ATTACH)
    }
  }

  override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(CREATE)
    }
  }

  override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(CREATE_VIEW)
    }
  }

  override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(START)
    }
  }

  override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(RESUME)
    }
  }

  override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(PAUSE)
    }
  }

  override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(STOP)
    }
  }

  override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(DESTROY_VIEW)
    }
  }

  override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(DESTROY)
    }
  }

  override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
    if (f is SSFragmentLifecycleAble) {
      obtainSubject(f).onNext(DETACH)
    }
  }

  private fun obtainSubject(fragment: Fragment): Subject<FragmentEvent> {
    return (fragment as SSFragmentLifecycleAble).provideLifecycleSubject()
  }
}