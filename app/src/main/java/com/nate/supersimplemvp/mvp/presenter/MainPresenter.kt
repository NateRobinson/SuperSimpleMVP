package com.nate.supersimplemvp.mvp.presenter

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.mvp.SSBasePresenter
import com.nate.ssmvp.utils.SSRxLifecycleUtils
import com.nate.supersimplemvp.entity.User
import com.nate.supersimplemvp.mvp.contract.MainContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.concurrent.ExecutorService
import javax.inject.Inject

/**
 * Created by Nate on 2020/5/5
 */
@ActivityScope
class MainPresenter @Inject constructor(model: MainContract.Model, view: MainContract.View) :
  SSBasePresenter<MainContract.Model, MainContract.View>(model, view) {
  @Inject
  lateinit var mErrorHandler: RxErrorHandler

  @Inject
  lateinit var mExecutorService: ExecutorService

  fun getGitUser(userName: String) {
    mModel
      .getGitUser(userName)
      .subscribeOn(Schedulers.from(mExecutorService))
      .compose(SSRxLifecycleUtils.bindToLifecycle(mRootView))
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : ErrorHandleSubscriber<User>(mErrorHandler) {
        override fun onNext(user: User) {
          mRootView?.getUserSuccess(user)
        }
      })
  }

}