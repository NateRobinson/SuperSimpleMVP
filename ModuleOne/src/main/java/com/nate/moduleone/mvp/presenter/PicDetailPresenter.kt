package com.nate.moduleone.mvp.presenter

import android.app.Application

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.nate.moduleone.mvp.contract.PicDetailContract

@FragmentScope
class PicDetailPresenter
@Inject
constructor(model: PicDetailContract.Model, rootView: PicDetailContract.View) :
SSBasePresenter<PicDetailContract.Model, PicDetailContract.View>(model,rootView) {

  @Inject
  lateinit var mApplication:Application
  @Inject
  lateinit var mErrorHandler:RxErrorHandler
  @Inject
  lateinit var mExecutorService: ExecutorService

  override fun onDestroy() {
    super.onDestroy()
  }
}