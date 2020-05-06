package com.nate.supersimplemvp.mvp.contract

import com.nate.ssmvp.mvp.SSIModel
import com.nate.ssmvp.mvp.SSIView

/**
 * Created by Nate on 2020/5/5
 */
interface UserContract {
  interface View : SSIView
  interface Model : SSIModel
}