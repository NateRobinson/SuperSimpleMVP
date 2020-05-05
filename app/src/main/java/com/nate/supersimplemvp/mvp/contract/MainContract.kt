package com.nate.supersimplemvp.mvp.contract

import com.nate.ssmvp.mvp.SSIModel
import com.nate.ssmvp.mvp.SSIView
import com.nate.supersimplemvp.entity.User
import io.reactivex.Observable

/**
 * Created by Nate on 2020/5/5
 */
interface MainContract {
  interface View : SSIView{
    fun getUserSuccess(user: User)
  }
  interface Model : SSIModel {
    fun getGitUser(userName: String): Observable<User>
  }
}