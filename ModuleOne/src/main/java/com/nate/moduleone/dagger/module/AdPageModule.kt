package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.AdPageContract
import com.nate.moduleone.mvp.model.AdPageModel

@Module
//构建 AdPageModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class AdPageModule(private val view: AdPageContract.View) {
  @FragmentScope
  @Provides
  fun provideAdPageView(): AdPageContract.View{
    return this.view
  }

  @FragmentScope
  @Provides
  fun provideAdPageModel(model:AdPageModel): AdPageContract.Model{
    return model
  }
}