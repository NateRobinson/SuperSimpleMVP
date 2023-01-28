package com.nate.moduleone.config;

import android.app.Application
import android.content.Context
import com.nate.ssmvp.base.SSAppLifecycle

/**
 * Created by Nate
 */
class ModuleLifecycle : SSAppLifecycle {
  override fun attachBaseContext(base: Context) {
  }

  override fun onCreate(application: Application) {
  }

  override fun onTerminate(application: Application) {
  }
}
