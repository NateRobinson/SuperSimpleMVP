package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.PicContract
import com.nate.moduleone.mvp.model.PicModel

@Module
//构建 PicModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class PicModule(private val view: PicContract.View) {
  @FragmentScope
  @Provides
  fun providePicView(): PicContract.View{
    return this.view
  }

  @FragmentScope
  @Provides
  fun providePicModel(model:PicModel): PicContract.Model{
    return model
  }
}