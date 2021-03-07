package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.TestPageContract
import com.nate.moduleone.mvp.model.TestPageModel

@Module
//构建 TestPageModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class TestPageModule(private val view: TestPageContract.View) {
  @ActivityScope
  @Provides
  fun provideTestPageView(): TestPageContract.View{
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideTestPageModel(model:TestPageModel): TestPageContract.Model{
    return model
  }
}