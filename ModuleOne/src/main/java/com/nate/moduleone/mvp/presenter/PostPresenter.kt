package com.nate.moduleone.mvp.presenter

import android.app.Application

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.nate.moduleone.mvp.contract.PostContract

@ActivityScope
class PostPresenter
@Inject
constructor(model: PostContract.Model, rootView: PostContract.View) :
SSBasePresenter<PostContract.Model, PostContract.View>(model,rootView) {

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