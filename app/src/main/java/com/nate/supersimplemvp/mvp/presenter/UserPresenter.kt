package com.nate.supersimplemvp.mvp.presenter

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.ssmvp.mvp.SSBasePresenter
import com.nate.supersimplemvp.mvp.contract.UserContract
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import java.util.concurrent.ExecutorService
import javax.inject.Inject

/**
 * Created by Nate on 2020/5/5
 */
@FragmentScope
class UserPresenter @Inject constructor(model: UserContract.Model, view: UserContract.View) :
  SSBasePresenter<UserContract.Model, UserContract.View>(model, view) {
  @Inject
  lateinit var mErrorHandler: RxErrorHandler

  @Inject
  lateinit var mExecutorService: ExecutorService
}