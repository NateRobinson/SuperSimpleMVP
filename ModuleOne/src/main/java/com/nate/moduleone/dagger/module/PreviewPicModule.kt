package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.PreviewPicContract
import com.nate.moduleone.mvp.model.PreviewPicModel

@Module
//构建 PreviewPicModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class PreviewPicModule(private val view: PreviewPicContract.View) {
  @ActivityScope
  @Provides
  fun providePreviewPicView(): PreviewPicContract.View{
    return this.view
  }

  @ActivityScope
  @Provides
  fun providePreviewPicModel(model:PreviewPicModel): PreviewPicContract.Model{
    return model
  }
}