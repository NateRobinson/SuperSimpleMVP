package com.nate.supersimplemvp.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.supersimplemvp.mvp.contract.SecondPageContract
import com.nate.supersimplemvp.mvp.model.SecondPageModel

@Module
//构建SecondPageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SecondPageModule(private val view: SecondPageContract.View) {
  @ActivityScope
  @Provides
  fun provideSecondPageView(): SecondPageContract.View {
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideSecondPageModel(model: SecondPageModel): SecondPageContract.Model {
    return model
  }
}
