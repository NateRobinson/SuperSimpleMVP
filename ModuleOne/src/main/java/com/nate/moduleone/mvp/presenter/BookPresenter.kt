package com.nate.moduleone.mvp.presenter

import android.app.Application

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.nate.moduleone.mvp.contract.BookContract

@FragmentScope
class BookPresenter
@Inject constructor(model: BookContract.Model, rootView: BookContract.View) :
  SSBasePresenter<BookContract.Model, BookContract.View>(model, rootView) {

  @Inject
  lateinit var mApplication: Application

  @Inject
  lateinit var mErrorHandler: RxErrorHandler

  @Inject
  lateinit var mExecutorService: ExecutorService

  override fun onDestroy() {
    super.onDestroy();
  }
}
