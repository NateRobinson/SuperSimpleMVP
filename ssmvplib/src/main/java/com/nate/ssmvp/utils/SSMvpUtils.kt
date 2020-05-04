package com.nate.ssmvp.utils

import android.content.Context
import com.jess.arms.utils.Preconditions
import com.nate.ssmvp.base.SSIApp
import com.nate.ssmvp.dagger.component.SSAppComponent

/**
 * 提供一些框架类
 * Created by Nate on 2020/5/4
 */
object SSMvpUtils {
  fun obtainAppComponentFromContext(context: Context): SSAppComponent {
    Preconditions.checkState(
        context.applicationContext is SSIApp, "%s must be implements %s", context.applicationContext.javaClass.name, SSIApp::class.java.name
    )
    return (context.applicationContext as SSIApp).getSSAppComponent()
  }
}