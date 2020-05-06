package com.nate.supersimplemvp.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.supersimplemvp.mvp.contract.MainContract
import com.nate.supersimplemvp.mvp.model.MainModel
import dagger.Module
import dagger.Provides

/**
 * Created by Nate on 2020/5/5
 */
@Module
class MainModule(private val view: MainContract.View) {

  @ActivityScope
  @Provides
  fun provideMainView(): MainContract.View {
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideMainModel(model: MainModel): MainContract.Model {
    return model
  }

}