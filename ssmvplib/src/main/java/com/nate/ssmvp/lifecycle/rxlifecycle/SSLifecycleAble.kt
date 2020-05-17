package com.nate.ssmvp.lifecycle.rxlifecycle

import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.subjects.Subject

/**
 * Activity, Fragment 实现此接口，配合 super simple mvp 框架即可使用 [RxLifecycle]
 * Created by Nate on 2020/5/4
 */
interface SSLifecycleAble<T> {
  fun provideLifecycleSubject(): Subject<T>
}