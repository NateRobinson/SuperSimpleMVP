package com.nate.moduleone.mvp.presenter

import android.app.Application

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.nate.moduleone.mvp.contract.ContactContract

@ActivityScope
class ContactPresenter
@Inject constructor(model: ContactContract.Model, rootView: ContactContract.View) :
  SSBasePresenter<ContactContract.Model, ContactContract.View>(model, rootView) {

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
