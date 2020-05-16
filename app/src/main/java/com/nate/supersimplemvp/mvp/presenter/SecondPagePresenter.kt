package com.nate.supersimplemvp.mvp.presenter

import android.app.Application

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.nate.supersimplemvp.mvp.contract.SecondPageContract

@ActivityScope
class SecondPagePresenter
@Inject constructor(model: SecondPageContract.Model, rootView: SecondPageContract.View) :
  SSBasePresenter<SecondPageContract.Model, SecondPageContract.View>(model, rootView) {

  @Inject
  lateinit var mApplication: Application

  @Inject
  lateinit var mErrorHandler: RxErrorHandler

  @Inject
  lateinit var mExecutorService: ExecutorService

  override fun onDestroy() {
    super.onDestroy()
  }
}
