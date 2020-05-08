package com.nate.moduleone.mvp.contract

import com.nate.ssmvp.mvp.SSIView
import com.nate.ssmvp.mvp.SSIModel

interface ContactContract {
  // 定义 View 接口，需要 Activity 或者 Fragment 实现，进行视图数据的绑定
  interface View : SSIView

  // 定义数据 Model，通过 Mode 暴露的方法完成数据的获取
  interface Model : SSIModel
}
